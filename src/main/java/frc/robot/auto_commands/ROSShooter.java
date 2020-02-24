/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto_commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ROS;
import frc.robot.subsystems.Shooter;

public class ROSShooter extends CommandBase {
  private final Shooter shooter;
  private final ROS ros;

  /**
   * Constructs a command to control the shooter under ROS control
   */
  public ROSShooter(Shooter _shooter, ROS _ros) {
    addRequirements(_shooter); // Requires shooter to work
    // Does not require ROS (allows other commands to use ROS passivly)

    shooter = _shooter; // Initalize local shooter
    ros = _ros; // Initalzie local ROS
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double turret_heading = ros.getTurretHeading(); // Update the target turret heading
    shooter.targetHeading(turret_heading, false); // Command the turret to that angle
  }
}
