/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.ros;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;


public class ROSControl extends CommandBase {
  private final Drivetrain drivetrain;
  private final ROS ros;

  // NT
  private static NetworkTable drivetrainCommandTable;
  private static NetworkTableEntry drivetrainCommandStatus; // an entry to tell ros if any instance of rosshooter is running
  
  /**
   * Creates a new ROSControl.
   * @author Joe
   */
  public ROSControl(Drivetrain _drivetrain, ROS _ros, Shooter _shooter) {
    addRequirements(_drivetrain);
    addRequirements(_ros);

    drivetrain = _drivetrain;
    ros = _ros;

    // NT
    drivetrainCommandTable = ROS.rosTable.getSubTable(Constants.RobotOperatingSystem.Names.DrivetrainTable);
    drivetrainCommandStatus = drivetrainCommandTable.getEntry("running_somewhere");
  }

  // Called once when the command is initally schedueled
  @Override
  public void initialize() {
    drivetrainCommandStatus.setBoolean(true); 
    SmartDashboard.putString("Alert", "ROS has the stick");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drivetrain.FlyWithWiresB(ros.getStarboardSpeed(), ros.getPortSpeed()); // Operate the drivetrain with commands from ROS
  }

  @Override
  public void end(boolean interrupted){
    drivetrainCommandStatus.setBoolean(false);
  }
}
