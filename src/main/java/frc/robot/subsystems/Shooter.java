/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  // TalonSRX objects
  private static final TalonSRX turretMotor = new TalonSRX(Constants.CANIds.TalonSRX_Turret_Address);

  // FalconFX objects
  private static final TalonFX shooterMotor = new TalonFX(Constants.CANIds.TalonFX_Shooter_Address);

  // Solenoids
  private static final Solenoid ballReleaseSolenoid = new Solenoid(Constants.CANIds.Ball_Release_Solenoid_Address);

  /**
   * Creates a new Turret.
   */
  public Shooter() {
    // Shooter PID and init
    shooterMotor.configFactoryDefault(); // Part of the OLD API
    // New API
    TalonFXConfiguration shooterConfig = new TalonFXConfiguration(); // Create a new config object
    shooterConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.IntegratedSensor; // Add tags like this one to apply configs

    shooterMotor.configAllSettings(shooterConfig); // Write them all at once

    shooterMotor.setInverted(Constants.ShooterPID.shooterMotorInvert);
    shooterMotor.setNeutralMode(Constants.ShooterPID.shooterBreakMode);


    // Turret PID and init
    turretMotor.configFactoryDefault();

    // Other init
  }
  
  /**
   * Sets the shooter motor to some speed
   * Does not use velocity control
   * @author Joe Sedutto
   * @param speed
   */
  public void testShooter(double speed){
    shooterMotor.set(ControlMode.PercentOutput, speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
