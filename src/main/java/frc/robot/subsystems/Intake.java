/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  // Motor objects
  private CANSparkMax outriggerIntakeMotor;
  private VictorSPX magazineIntakeMotor;
  
  // Pneumatics
  private DoubleSolenoid intakeDeploySolenoid;


  /**
   * Creates a new Intake.
   */
  public Intake() {
    // Setup the outrigger motor
    outriggerIntakeMotor 
      = new CANSparkMax(
        Constants.CANAddresses.MiniNeo_Outrunner_IntakeMotor_Address, 
        MotorType.kBrushless);
    outriggerIntakeMotor.restoreFactoryDefaults();
    //outriggerIntakeMotor.setInverted(true); // Inverts the motor

    // Setup the magazine intake motor
    magazineIntakeMotor 
      = new VictorSPX(Constants.CANAddresses.Magazine_Intake_Motor);
    //magazineIntakeMotor.setInverted(true); // Inverts the motor

    // Setup the pneumatic pistons
    intakeDeploySolenoid
      = new DoubleSolenoid(
        Constants.Pneumatics.Intake_Solenoid_Forward, 
        Constants.Pneumatics.Intake_Solenoid_Reverse);

  }

  /**
   * Takes a variable speed and drives the
   * intake motor directly.
   @author Joe Sedutto
   @param speed (The speed at witch to spin the intake, postive numbers being "in")
   */
  public void driveIntake(double speed) {
    outriggerIntakeMotor.set(speed); // Set the motor to the required speed
  }

  /**
   * This method lifts, or lowers the intake by triggering the solenoid.
   @author Joe Sedutto
   @param state (off, forward or reverse)
   */
  public void setIntakePosition(DoubleSolenoid.Value state) {
    intakeDeploySolenoid.set(state); // Set the solenoid to the requested state.
  }

  /**
   * Purges the intake.
   * @author Joe Sedutto
   */
  public void purgeIntake() {
    outriggerIntakeMotor.set(-0.5);
    magazineIntakeMotor.set(ControlMode.PercentOutput, 0.25);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
