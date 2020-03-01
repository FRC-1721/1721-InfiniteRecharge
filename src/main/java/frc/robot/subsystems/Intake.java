/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  // Motor objects
  public static final VictorSPX intakeMotor = new VictorSPX(Constants.CANIds.VictorSPX_IntakeMotor_Address);

  /**
   * Creates a new Intake.
   */
  public Intake() {
    intakeMotor.setInverted(true);
  }

  /**
   * Takes a variable speed and drives the
   * intake motor directly.
   * @author Joe Sedutto
   * @param speed (The speed at witch to spin the intake, postive numbers being "in")
   */
  public void driveIntake(double speed){
    intakeMotor.set(ControlMode.PercentOutput, speed); // Set the motor to the required speed
  }

  /**
   * Purges the intake.
   * @author Joe Sedutto
   */
  public void purgeIntake(){
    intakeMotor.set(ControlMode.PercentOutput, -1.0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
