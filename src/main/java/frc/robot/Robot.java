/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.ROS;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer(); // Define our Robot Container
    ROS.setMode(Constants.RobotOperatingSystem.Modes.NoMode); // Tell ROS what mode we are in
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run(); // Runs the entire Scheduler, not to be messed with.
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
    ROS.setMode(Constants.RobotOperatingSystem.Modes.Disabled); // Tell ROS what mode we are in
  }

  /** This function is called all the time during Disabled mode */
  @Override
  public void disabledPeriodic() {
  }

  /**
   * This function runs once each time the robot enters autonomous mode
   */
  @Override
  public void autonomousInit() {
    ROS.setMode(Constants.RobotOperatingSystem.Modes.Autonomous); // Tell ROS what mode we are in

    m_autonomousCommand = m_robotContainer.getAutonomousCommand(); // Get the selected autonomous command

    if (m_autonomousCommand != null) { // If the command is not null
      m_autonomousCommand.schedule(); // Run the command
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    ROS.setMode(Constants.RobotOperatingSystem.Modes.Teleop); // Tell ROS what mode we are in
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    if (m_autonomousCommand != null && m_robotContainer.isAutonomousOverride()) { // If the command that was run at autonomous init was not null and isAutonomousOverride is true
      m_autonomousCommand.cancel(); // Cancel the autonomous command
    }
  }

  @Override
  public void testInit() {
    ROS.setMode(Constants.RobotOperatingSystem.Modes.Test); // Tell ROS what mode we are in
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
