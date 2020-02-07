/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ROS;


public class ROSControl extends CommandBase {
  private final Drivetrain drivetrain;
  private final ROS ros;
  
  /**
   * Creates a new ROSControl.
   * 
   * @author Joe
   */
  public ROSControl(Drivetrain _drivetrain, ROS _ros) {
    addRequirements(_drivetrain);
    addRequirements(_ros);
    drivetrain = _drivetrain;
    ros = _ros;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drivetrain.FlyWithWiresB(ros.getStarboardSpeed(), ros.getPortSpeed());
  }
}
