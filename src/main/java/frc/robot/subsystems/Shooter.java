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
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
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

  // Dumb targeting
  private static final NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry tx = limelight.getEntry("tx");
  NetworkTableEntry ty = limelight.getEntry("ty");
  NetworkTableEntry ta = limelight.getEntry("ta");
  NetworkTableEntry camMode_entry = limelight.getEntry("camMode");
  NetworkTableEntry pipeline_entry = limelight.getEntry("pipeline");

  /**
   * Creates a new Turret.
   */
  public Shooter() {
    // Shooter PID and init
    // Set motors to default to prevent weirdness
    shooterMotor.configFactoryDefault();

    // Set feedback sensors here
    shooterMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 
                                           Constants.ShooterPID.kPIDLoopIdx,
                                           Constants.ShooterPID.kTimeoutMs);
    
    // Fix sensor phase here
    shooterMotor.setSensorPhase(Constants.ShooterPID.shooterSensorPhase);

    // Configure nominal outputs
    shooterMotor.configNominalOutputForward(0, Constants.ShooterPID.kTimeoutMs);
		shooterMotor.configNominalOutputReverse(0, Constants.ShooterPID.kTimeoutMs);
		shooterMotor.configPeakOutputForward(1, Constants.ShooterPID.kTimeoutMs);
    shooterMotor.configPeakOutputReverse(-1, Constants.ShooterPID.kTimeoutMs);
    
    // Configure allowable closed loop error (dead zone)
    shooterMotor.configAllowableClosedloopError(0, Constants.ShooterPID.kPIDLoopIdx, Constants.ShooterPID.kTimeoutMs);

    // Config slot0 gains
    shooterMotor.config_kF(Constants.ShooterPID.kPIDLoopIdx, Constants.ShooterPID.kGains.kF, Constants.ShooterPID.kTimeoutMs);
		shooterMotor.config_kP(Constants.ShooterPID.kPIDLoopIdx, Constants.ShooterPID.kGains.kP, Constants.ShooterPID.kTimeoutMs);
		shooterMotor.config_kI(Constants.ShooterPID.kPIDLoopIdx, Constants.ShooterPID.kGains.kI, Constants.ShooterPID.kTimeoutMs);
    shooterMotor.config_kD(Constants.ShooterPID.kPIDLoopIdx, Constants.ShooterPID.kGains.kD, Constants.ShooterPID.kTimeoutMs);    

    // Set Inverted
    shooterMotor.setInverted(Constants.ShooterPID.shooterMotorInvert); // Sets the output of the motor backwards


    // Turret PID and init
    // Set motors to default to prevent weirdness
    turretMotor.configFactoryDefault();

    // Set feedback sensors here
    turretMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 
                                           Constants.TurretPID.kPIDLoopIdx,
                                           Constants.TurretPID.kTimeoutMs);
    
    // Fix sensor phase here
    turretMotor.setSensorPhase(Constants.TurretPID.turretSensorPhase);

    // Configure nominal outputs
    turretMotor.configNominalOutputForward(0, Constants.TurretPID.kTimeoutMs);
		turretMotor.configNominalOutputReverse(0, Constants.TurretPID.kTimeoutMs);
		turretMotor.configPeakOutputForward(1, Constants.TurretPID.kTimeoutMs);
    turretMotor.configPeakOutputReverse(-1, Constants.TurretPID.kTimeoutMs);
    
    // Configure allowable closed loop error (dead zone)
    turretMotor.configAllowableClosedloopError(0, Constants.TurretPID.kPIDLoopIdx, Constants.TurretPID.kTimeoutMs);

    // Config slot0 gains
    turretMotor.config_kF(Constants.TurretPID.kPIDLoopIdx, Constants.TurretPID.kGains.kF, Constants.TurretPID.kTimeoutMs);
		turretMotor.config_kP(Constants.TurretPID.kPIDLoopIdx, Constants.TurretPID.kGains.kP, Constants.TurretPID.kTimeoutMs);
		turretMotor.config_kI(Constants.TurretPID.kPIDLoopIdx, Constants.TurretPID.kGains.kI, Constants.TurretPID.kTimeoutMs);
    turretMotor.config_kD(Constants.TurretPID.kPIDLoopIdx, Constants.TurretPID.kGains.kD, Constants.TurretPID.kTimeoutMs);    

    // Set Inverted
    turretMotor.setInverted(Constants.TurretPID.turretMotorInvert); // Sets the output of the motor backwards

    // Other init
    ballReleaseSolenoid.set(false);
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

  /**
   * For testing only
   * @author Joe Sedutto
   * @param asDouble
   */
  public void testTurret(double speed) {
    turretMotor.set(ControlMode.PercentOutput, speed);
  }

  /**
   * Set the shooter solenoid to the state listed here
   * @author Joe Sedutto
   * @param state
   */
  public void releaseShooter(boolean state){
    ballReleaseSolenoid.set(state);
  }

  /**
   * Takes an angle in radians and converts
   * it to ticks to send to the turret control
   * @author Joe Sedutto
   * @param heading
   */
  public void targetHeading(double heading){
    turretMotor.set(ControlMode.Position, heading * Constants.TurretPID.ticksPerRadian);
  }

  /**
   * Takes a int and sets the pipeline accordingly
   * @param pipeline
   */
  public void switchPipelines(int pipeline) {
    pipeline_entry.setNumber(pipeline);
    //camMode_entry.setNumber(pipeline);
  }


  public double getTurretHeading(){return (turretMotor.getSelectedSensorPosition() / Constants.TurretPID.ticksPerRadian);}

  public double getLimelightHeading(){return Math.toRadians(tx.getDouble(0.0));}
  public double getLimelightElevation(){return Math.toRadians(ty.getDouble(0.0));}
  public double getLimelightDistance(){return ta.getDouble(0.0);}
  public boolean isAtTopLimit(){if (turretMotor.isFwdLimitSwitchClosed() > 0){ return true; }else{ return false;}}
  public boolean isAtBottomLimit(){if (turretMotor.isFwdLimitSwitchClosed() > 0){ return true; }else{ return false;}}
  public boolean isAtLimit(){if (turretMotor.isFwdLimitSwitchClosed() + turretMotor.isFwdLimitSwitchClosed() > 0){ return true; }else{ return false;}}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
