/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.HumanControl;
import frc.robot.commands.ManualClimb;
import frc.robot.commands.ManualTurret;
import frc.robot.commands.SolveStage2;
import frc.robot.commands.functions.ArmShooter;
import frc.robot.commands.functions.DisarmShooter;
import frc.robot.commands.functions.PurgeIntake;
import frc.robot.commands.functions.ResetEncoders;
import frc.robot.commands.functions.ShiftDown;
import frc.robot.commands.functions.ShiftUp;
import frc.robot.commands.functions.SpinIntake;
import frc.robot.commands.functions.UnloadMagazineWhenReady;
import frc.robot.commands.functions.ZeroTurret;
import frc.robot.ros.ROS;
import frc.robot.ros.ROSControl;
import frc.robot.ros.ROSShooter;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Magazine;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Solver;
import frc.robot.subsystems.Turret;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // Joysticks and Operator Input
  public static final Joystick DriverStick = new Joystick(Constants.DriverInputSettings.Driver_Stick_Port);
  public static final Joystick OperatorStick = new Joystick(Constants.OperatorInputSettings.Operator_Controller_Port);

  // Subsystems
  private final Drivetrain drivetrain = new Drivetrain();
  private final Intake intake = new Intake();
  private final ROS ros = new ROS();
  private final Shooter shooter = new Shooter();
  private final Climber climber = new Climber();
  private final Turret turret = new Turret();
  private final Magazine magazine = new Magazine();
  private final Solver solver = new Solver();

  // Commands
  private final ROSShooter rosShooter = new ROSShooter(shooter, turret, magazine, ros);
  private final ManualTurret manualTurret = new ManualTurret(turret, OperatorStick, () -> (OperatorStick.getRawAxis(Constants.OperatorInputSettings.Turret_Spin_ccw_axis) - OperatorStick.getRawAxis(Constants.OperatorInputSettings.Turret_Spin_cw_axis)));
  private final UnloadMagazineWhenReady unloadMagazineWhenReady = new UnloadMagazineWhenReady(magazine);
  private final SolveStage2 solveStage2 = new SolveStage2(solver); // Create the Solve Stage 2 Command for driving the solver during stage 2

  // Selectors
  Command robot_autonomous; // Autonomous object, will be populated later by the contents of the sendable chooser
  SendableChooser<Command> autoChooser = new SendableChooser<>(); // Create a new chooser for holding what autonomous we want to use

  Command handling_mode_selector; // Autonomous object, will be populated later by the contents of the sendable chooser
  SendableChooser<HandlingMode> handlingChooser = new SendableChooser<>(); // Create a new chooser for holding what autonomous we want to use

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Define SmartDashboard modes
    // Handling Mode
    handlingChooser.setDefaultOption("Standard", HandlingMode.kStandard);
    handlingChooser.addOption("Fly By Wire", HandlingMode.kFlyByWire);
    handlingChooser.addOption("Lane Assist", HandlingMode.kLaneAssist);
    handlingChooser.addOption("Diff-Lock", HandlingMode.kDiffLock);
    SmartDashboard.putData("Handling Mode", handlingChooser);

    // Autonomous Mode
    autoChooser.setDefaultOption("ROS Full Auto", new ROSControl(drivetrain, ros, shooter));
    autoChooser.addOption("Do Nothing", null); // Send null
    SmartDashboard.putData("Auto Mode", autoChooser);

    // NT functions
    SmartDashboard.putData("Reset Encoders", new ResetEncoders(drivetrain));
    SmartDashboard.putData("Shift Up", new ShiftUp(drivetrain)); // For testing only!
    SmartDashboard.putData("Shift Down", new ShiftDown(drivetrain)); // For testing only!
    SmartDashboard.putData("Arm Shooter", new ArmShooter(shooter)); // For testing only!
    SmartDashboard.putData("Zero Turret", new ZeroTurret(turret));

    // Configure the button bindings
    configureButtonBindings();

    // Default Commands
    drivetrain.setDefaultCommand(new HumanControl(() -> DriverStick.getRawAxis(Constants.DriverInputSettings.Drivebase_Thro_Axis), () -> DriverStick.getRawAxis(Constants.DriverInputSettings.Drivebase_Yaw_Axis), () -> handlingChooser.getSelected(), drivetrain)); // Set the default command of drivetrain to HumanControl
    turret.setDefaultCommand(new ZeroTurret(turret));
    intake.setDefaultCommand(new PurgeIntake(intake));
    climber.setDefaultCommand(new ManualClimb(climber, () -> OperatorStick.getRawAxis(Constants.OperatorInputSettings.Gantry_Axis), () -> OperatorStick.getRawAxis(Constants.OperatorInputSettings.Climb_Axis), () -> OperatorStick.getRawButton(9)));
    //shooter.setDefaultCommand(new ManualShooter(shooter, OperatorStick, () -> OperatorStick.getRawAxis(3), () -> (OperatorStick.getRawAxis(Constants.OperatorInputSettings.Turret_Spin_cw_axis) - OperatorStick.getRawAxis(Constants.OperatorInputSettings.Turret_Spin_ccw_axis))));

    // ROS Commands
    ros.publishCommand(Constants.RobotOperatingSystem.Names.ROSShooterTable, rosShooter); // We may want these commands to be default commands, and be overriden by manual driver commands
    ros.publishCommand(Constants.RobotOperatingSystem.Names.ResetEncoders, new ResetEncoders(drivetrain));
    ros.publishCommand(Constants.RobotOperatingSystem.Names.ShiftUp, new ShiftUp(drivetrain));
    ros.publishCommand(Constants.RobotOperatingSystem.Names.ShiftDown, new ShiftDown(drivetrain));
    ros.publishCommand(Constants.RobotOperatingSystem.Names.ZeroTurret, new ZeroTurret(turret));
    ros.publishCommand(Constants.RobotOperatingSystem.Names.DrivetrainTable, new ROSControl(drivetrain, ros, shooter));
    ros.publishCommand(Constants.RobotOperatingSystem.Names.ManualTurret, manualTurret); // Not to be called by ROS but to check its status.
  }

  /**
   * This method contains instantioations for connecting
   * buttons and commands.
   */
  private void configureButtonBindings() {
    // Driver
    new JoystickButton(DriverStick, Constants.DriverInputSettings.Autonomous_Restart_Button).whenPressed(new ROSControl(drivetrain, ros, shooter)); // When you press the Autonomous Restart Button
    
    // Operator Commands
    // Arms and disarms the shooter (spinup)
    new JoystickButton(OperatorStick, Constants.OperatorInputSettings.Arm_Shooter_Button).whenPressed(new ArmShooter(shooter)); 
    // Runs a command to unload the magazine into the shooter only if the shooter reports that it is ready to fire
    new JoystickButton(OperatorStick, Constants.OperatorInputSettings.Fire_When_Ready_Button).whileHeld(unloadMagazineWhenReady); 
    // A command to disarm the shooter. (spindown)
    new JoystickButton(OperatorStick, Constants.OperatorInputSettings.Disarm_Shooter_Button).whenPressed(new DisarmShooter(shooter));
    // A command to inturupt the default purgeIntake command, and spin the intake in
    new JoystickButton(OperatorStick, Constants.OperatorInputSettings.Intake_Button).whenHeld(new SpinIntake(intake));

    // Un-fixed/testing/misc
    new JoystickButton(OperatorStick, Constants.OperatorInputSettings.Automatic_Turret_Button).whenPressed(rosShooter);
    new JoystickButton(OperatorStick, Constants.OperatorInputSettings.Manual_Turret_Button).whenPressed(manualTurret);
    new JoystickButton(OperatorStick, Constants.OperatorInputSettings.Purge_Button).whenHeld(new PurgeIntake(intake));
    //new JoystickButton(DSTogglePanel, Constants.DSTogglePanelSettings.SolveStageTwo).whenPressed(solveStage2);
  }

  /**
   * Checks if the operator is trying to manually drive the robot but not pressing the button
   * (provides another form of saftey)
   * 
   * @author Joe
   * @return true when the driver is trying to drive the robot
   */
  public boolean isAutonomousOverride() {
    if (DriverStick.getRawAxis(Constants.DriverInputSettings.Drivebase_Thro_Axis) > Constants.DriverInputSettings.Overide_Threshold || DriverStick.getRawAxis(Constants.DriverInputSettings.Drivebase_Yaw_Axis) > Constants.DriverInputSettings.Overide_Threshold){
      return true;
    }else{
      return false;
    }
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autoChooser.getSelected(); // Returns the currently Selected autonomous
  }
}
