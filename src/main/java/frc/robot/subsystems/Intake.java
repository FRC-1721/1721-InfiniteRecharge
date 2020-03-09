/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.LiveSettings;

public class Intake extends SubsystemBase {
  // Motor objects
  private CANSparkMax intakeMotor;

  /**
   * Creates a new Intake.
   */
  public Intake() {
    intakeMotor = new CANSparkMax(Constants.CANIds.IntakeMotor_Address, MotorType.kBrushless);
    intakeMotor.restoreFactoryDefaults();
    intakeMotor.setInverted(true);
  }

  /**
   * Takes a variable speed and drives the
   * intake motor directly.
   * @author Joe Sedutto
   * @param speed (The speed at witch to spin the intake, postive numbers being "in")
   */
  public void driveIntake(double speed){
    switch (LiveSettings.intakeMode.getValue()){
      case disengaged:
      intakeMotor.set(0);
      default:
      intakeMotor.set(speed); // Set the motor to the required speed
			break;
    }
  }

  /**
   * Purges the intake.
   * @author Joe Sedutto
   */
  public void purgeIntake(){
    switch (LiveSettings.intakeMode.getValue()){
      case auxiliary:
      case normal:
      intakeMotor.set(-0.3);
      break;
      case disengaged:
      intakeMotor.set(0);
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
