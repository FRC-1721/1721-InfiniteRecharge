/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.HumanControl;
import frc.robot.commands.ManualClimb;
import frc.robot.commands.ManualShooter;
import frc.robot.commands.ROSControl;
import frc.robot.commands.functions.ArmShooter;
import frc.robot.commands.functions.DisarmShooter;
import frc.robot.commands.functions.PurgeIntake;
import frc.robot.commands.functions.ResetEncoders;
import frc.robot.commands.functions.ShiftDown;
import frc.robot.commands.functions.ShiftUp;
import frc.robot.commands.functions.SpinIntake;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.ROS;
import frc.robot.subsystems.Shooter;

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
  public static final Joystick DSTogglePanel = new Joystick(Constants.DSTogglePanelSettings.DS_Toggle_Panel_Port);

  // Subsystems
  private final Drivetrain drivetrain = new Drivetrain();
  private final Intake intake = new Intake();
  private final ROS ros = new ROS();
  private final Shooter shooter = new Shooter();
  private final Climber climber = new Climber();

  // Commands


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

    // Configure the button bindings
    configureButtonBindings();

    // Default commands
    drivetrain.setDefaultCommand(new HumanControl(() -> DriverStick.getRawAxis(Constants.DriverInputSettings.Drivebase_Thro_Axis), () -> DriverStick.getRawAxis(Constants.DriverInputSettings.Drivebase_Yaw_Axis), () -> handlingChooser.getSelected(), drivetrain)); // Set the default command of drivetrain to HumanControl
    shooter.setDefaultCommand(new ManualShooter(shooter, OperatorStick, () -> OperatorStick.getRawAxis(3), () -> (OperatorStick.getRawAxis(Constants.OperatorInputSettings.Turret_Spin_cw_axis) - OperatorStick.getRawAxis(Constants.OperatorInputSettings.Turret_Spin_ccw_axis))));
    climber.setDefaultCommand(new ManualClimb(climber, () -> OperatorStick.getRawAxis(Constants.OperatorInputSettings.Climb_Axis)));
    
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(DriverStick, Constants.DriverInputSettings.Autonomous_Restart_Button).whenPressed(new ROSControl(drivetrain, ros, shooter)); // When you press the Autonomous Restart Button
    
    // Operator
    new JoystickButton(OperatorStick, Constants.OperatorInputSettings.Arm_Shooter_Button).whenPressed(new ArmShooter(shooter)); // Arms and disarms the shooter
    new JoystickButton(OperatorStick, Constants.OperatorInputSettings.Disarm_Shooter_Button).whenPressed(new DisarmShooter(shooter));
    new JoystickButton(OperatorStick, Constants.OperatorInputSettings.Intake_Button).whenHeld(new SpinIntake(intake));
    new JoystickButton(OperatorStick, Constants.OperatorInputSettings.Purge_Button).whenHeld(new PurgeIntake(intake));
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
