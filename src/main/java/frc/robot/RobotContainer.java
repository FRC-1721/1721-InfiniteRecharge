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
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.Drivetrain;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // Joysticks and Operator Input
  public static final Joystick DriverStick = new Joystick(Constants.DriverInputSettings.Driver_Stick_Port);

  // Subsystems
  private final Drivetrain drivetrain = new Drivetrain();

  // Selectors
  Command robot_autonomous; // Autonomous object, will be populated later by the contents of the sendable chooser
  SendableChooser<Command> autoChooser = new SendableChooser<>(); // Create a new chooser for holding what autonomous we want to use

  Command handling_mode_selector; // Autonomous object, will be populated later by the contents of the sendable chooser
  SendableChooser<HandlingMode> handlingChooser = new SendableChooser<>(); // Create a new chooser for holding what autonomous we want to use

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    // Commands
    drivetrain.setDefaultCommand(new DriveCommand(() -> DriverStick.getRawAxis(0), () -> DriverStick.getRawAxis(0), () -> handlingChooser.getSelected(), drivetrain)); // Set the default command of drivetrain to driveCommand

    // Define SmartDashboard modes
    handlingChooser.addOption("Standard", HandlingMode.kStandard);
    handlingChooser.addOption("Standard", HandlingMode.kFlyByWire);
    handlingChooser.addOption("Standard", HandlingMode.kLaneAssist);
    handlingChooser.addOption("Standard", HandlingMode.kDiffLock);

    //autoChooser.setDefaultOption("ROS Full Auto", new ROS_FullAuto());
    autoChooser.addOption("Do nothing", null); // Send null
    SmartDashboard.putData("Auto mode", autoChooser);
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return autoChooser.getSelected();
  }
}
