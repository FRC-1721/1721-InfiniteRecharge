/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.AutoClimb;
import com.revrobotics.CANSparkMax;

/**
 * Climber as a subsystem will be calling the
 * AutoClimb command as to collect input from
 * sensors on toaster to move rollers on
 * Charles
 *  @author Travis Bettens
 */
public class Climber extends Subsystem {
  private static final CANSparkMax LiftMotor = new CANSparkMax(Constants.CANIds.Lift_Motor_ID); // Init the port motor at 1

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new AutoClimb()); // Use to set the defaut command of a subsystem
  }
}
