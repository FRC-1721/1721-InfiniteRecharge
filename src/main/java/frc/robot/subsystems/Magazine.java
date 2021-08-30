// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Magazine extends SubsystemBase {
  // VictorSPX
  private static VictorSPX dischargeMotor;

  // Neo 550
  private static CANSparkMax magazineAgitator;

  /** Creates a new Magazine. */
  public Magazine() {
    // Setup motors
    dischargeMotor = new VictorSPX(
      Constants.CANAddresses.Magazine_Dischage_motor);
    
    magazineAgitator = new CANSparkMax(
      Constants.CANAddresses.Magazine_Agitate, MotorType.kBrushless);

    dischargeMotor.configFactoryDefault();
    dischargeMotor.setInverted(true);
    
    magazineAgitator.restoreFactoryDefaults();
    magazineAgitator.setInverted(false);
  }

  /**
   * Sets the agitator motor to some speed.
   @author Joe Sedutto
   @param speed A target speed for the agitator. FOR TESTING ONLY
   @Deprecated DONT USE, ONLY FOR TESTING
   */
  public void testagitator(double speed) {
    magazineAgitator.set(speed);
  }

  /**
   * Sets the discharge motor to some speed
   * Does not use velocity control.
   @author Joe Sedutto
   @param speed A target speed for the discharge. FOR TESTING ONLY
   @Deprecated DONT USE, ONLY FOR TESTING
   */
  public void testDischarge(double speed) {
    dischargeMotor.set(ControlMode.PercentOutput, speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
