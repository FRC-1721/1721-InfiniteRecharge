/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.ros.ROS;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;

@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class ROSControl extends CommandBase {
  private final Drivetrain drivetrain;
  private final ROS ros;
  private final Shooter shooter;
  
  /**
   * Creates a new ROSControl.
   * @author Joe
   */
  @SuppressWarnings("checkstyle:ParameterName")
  public ROSControl(
      Drivetrain _drivetrain, 
      ROS _ros, 
      Shooter _shooter) {
    addRequirements(_drivetrain);
    addRequirements(_ros);
    addRequirements(_shooter);

    drivetrain = _drivetrain;
    ros = _ros;
    shooter = _shooter;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drivetrain.flyWithWiresB(ros.getStarboardSpeed(), ros.getPortSpeed());

    // TODO change this so it actually is 10/10 not 3/10
    shooter.targetHeading(shooter.getTurretHeading() + shooter.getLimelightHeading());
  }
}
