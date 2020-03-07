/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.LiveSettings;

public class Turret extends SubsystemBase {
  // TalonSRX objects
  private static final TalonSRX turretMotor = new TalonSRX(Constants.CANIds.TalonSRX_Turret_Address);

  /**
   * Creates a new Turret.
   */
  public Turret() {
    // Turret PID and init
    // Set motors to default to prevent weirdness
    turretMotor.configFactoryDefault();

    // Set feedback sensors here
    turretMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 
                                             Constants.DrivetrainPID.kPIDLoopIdx,
                                             Constants.DrivetrainPID.kTimeoutMs);
    
    // Sets the position to clear when the F limit switch is pressed
    turretMotor.configClearPositionOnLimitF(true, Constants.DrivetrainPID.kTimeoutMs);

    // Enable brake mode
    turretMotor.setNeutralMode(Constants.ShooterPID.turretBrakeMode);

    // Fix sensor phase here
    turretMotor.setSensorPhase(Constants.TurretPID.turretSensorPhase);

    // Configure nominal outputs
    turretMotor.configNominalOutputForward(0, Constants.TurretPID.kTimeoutMs);
		turretMotor.configNominalOutputReverse(0, Constants.TurretPID.kTimeoutMs);
		turretMotor.configPeakOutputForward(0.5, Constants.TurretPID.kTimeoutMs);
    turretMotor.configPeakOutputReverse(-0.5, Constants.TurretPID.kTimeoutMs);
    
    // Configure allowable closed loop error (dead zone)
    turretMotor.configAllowableClosedloopError(0, Constants.TurretPID.kPIDLoopIdx, Constants.TurretPID.kTimeoutMs);

    // Config slot0 gains
    turretMotor.config_kF(Constants.TurretPID.kPIDLoopIdx, Constants.TurretPID.kGains.kF, Constants.TurretPID.kTimeoutMs);
		turretMotor.config_kP(Constants.TurretPID.kPIDLoopIdx, Constants.TurretPID.kGains.kP, Constants.TurretPID.kTimeoutMs);
		turretMotor.config_kI(Constants.TurretPID.kPIDLoopIdx, Constants.TurretPID.kGains.kI, Constants.TurretPID.kTimeoutMs);
    turretMotor.config_kD(Constants.TurretPID.kPIDLoopIdx, Constants.TurretPID.kGains.kD, Constants.TurretPID.kTimeoutMs);    

    // Set Inverted
    turretMotor.setInverted(Constants.TurretPID.turretMotorInvert); // Sets the output of the motor backwards
  }

  /**
   * For testing only
   * @author Joe Sedutto
   * @deprecated use targetHeading() instead
   * @param power
   */
  public void testTurret(double power) {
    turretMotor.set(ControlMode.PercentOutput, power);
  }

  /**
   * Drives the turret at some velocity.
   * Useful for manual control
   * @author Joe Sedutto
   * @param velocity (ticks/10ms)
   */
  public void manualTurret(double velocity) {
    switch (LiveSettings.turretMode.getValue()){
      case auxiliary:
      case normal:
      turretMotor.set(ControlMode.Velocity, velocity); // Sets the motor to some velocity 
      break;
      case disengaged:
      break;
    }
  }

  /**
   * Drives the turret to some specificed location.
   * Useful for automatic control
   * @author Joe Sedutto
   * @param heading (Radians) < 8, (Ticks) > 8
   */
  public void targetHeading(double heading, boolean radians){
    if (radians){ // If commanding using radians
      turretMotor.set(ControlMode.Position, heading * Constants.TurretPID.ticksPerRadian);
    }
    else{
      turretMotor.set(ControlMode.Position, heading);
    }
  }

  public static double getTurretHeadingRaw(){return turretMotor.getSelectedSensorPosition();}
  public static double getTurretHeading(){return (turretMotor.getSelectedSensorPosition() / Constants.TurretPID.ticksPerRadian);}

  public boolean isAtTopLimit(){if (turretMotor.isFwdLimitSwitchClosed() > 0){ return true; }else{ return false;}}
  public boolean isAtBottomLimit(){if (turretMotor.isRevLimitSwitchClosed() > 0){ return true; }else{ return false;}}
  public boolean isAtLimit(){if (turretMotor.isFwdLimitSwitchClosed() + turretMotor.isFwdLimitSwitchClosed() > 0){ return true; }else{ return false;}}

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("Turret Forward Limit Switch", isAtTopLimit());
    SmartDashboard.putBoolean("Turret Reverse Limit Switch", isAtBottomLimit());

    SmartDashboard.putNumber("Turret Azimuth", getTurretHeading());
  }
}
