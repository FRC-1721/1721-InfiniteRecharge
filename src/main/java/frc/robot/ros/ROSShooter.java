/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.ros;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;

public class ROSShooter extends CommandBase {
  private final Shooter shooter;
  private final Turret turret;
  private final ROS ros;

  // NT
  private static NetworkTable shooterCommandTable;
  private static NetworkTableEntry shooterCommandStatus; // an entry to tell ros if any instance of rosshooter is running

  /**
   * Constructs a command to control the shooter under ROS control
   */
  public ROSShooter(Shooter _shooter, Turret _turret, ROS _ros) {
    addRequirements(_shooter); // Requires shooter to work
    addRequirements(_turret);
    // Does not require ROS (allows other commands to use ROS passivly)

    shooter = _shooter; // Initalize local shooter
    turret = _turret;
    ros = _ros; // Initalzie local ROS

    // NT
    shooterCommandTable = ROS.rosTable.getSubTable("enable_shooter");
    shooterCommandStatus = shooterCommandTable.getEntry("running_somewhere");
  }

  // Called once when the command is initally schedueled
  @Override
  public void initialize() {
    shooter.switchPipelines(1); // Arms and readies the vision for tracking
    shooterCommandStatus.setBoolean(true);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double turret_heading = ros.getTurretHeading(); // Update the target turret heading
    turret.targetHeading(turret_heading, false); // Command the turret to that angle
  }

  @Override
  public void end(boolean interrupted){
    shooter.switchPipelines(0); // Disable the vision tracking
    shooterCommandStatus.setBoolean(true);
  }
}
