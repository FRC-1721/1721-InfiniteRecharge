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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

    // Set brake mode
    shooterMotor.setNeutralMode(Constants.ShooterPID.shooterBrakeMode);

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


    // Other init
    ballReleaseSolenoid.set(false);
  }
  
  /**
   * Sets the shooter motor to some speed
   * Does not use velocity control
   * @author Joe Sedutto
   * @deprecated use setShooterVelocity instead
   * @param speed
   */
  public void testShooter(double power){
    shooterMotor.set(ControlMode.PercentOutput, power);
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
   * Sets the shooter velocity.
   * @author Joe Sedutto
   * @param velocity (ticks/10ms)
   */
  public void setShooterVelocity(double velocity){
    shooterMotor.set(ControlMode.Velocity, velocity);
  }

  /**
   * Set the shooter solenoid to the state listed here
   * @author Joe Sedutto
   * @param state
   */
  public void engageMagazine(boolean state){
    ballReleaseSolenoid.set(state);
  }

  /**
   * Drives the turret at some velocity.
   * Useful for manual control
   * @author Joe Sedutto
   * @param velocity (ticks/10ms)
   */
  public void manualTurret(double velocity) {
    turretMotor.set(ControlMode.Velocity, velocity); // Sets the motor to some velocity 
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

  /**
   * Takes a int and sets the pipeline accordingly
   * @param pipeline
   */
  public void switchPipelines(int pipeline) {
    pipeline_entry.setNumber(pipeline);
    //camMode_entry.setNumber(pipeline);
  }


  public static double getTurretHeadingRaw(){return turretMotor.getSelectedSensorPosition();}
  public static double getTurretHeading(){return (turretMotor.getSelectedSensorPosition() / Constants.TurretPID.ticksPerRadian);}

  public double getLimelightAzimuth(){return Math.toRadians(tx.getDouble(0.0));}
  public double getLimelightElevation(){return Math.toRadians(ty.getDouble(0.0));}
  public double getRoughLimelightDistance(){return ta.getDouble(0.0);}
  public boolean isAtTopLimit(){if (turretMotor.isFwdLimitSwitchClosed() > 0){ return true; }else{ return false;}}
  public boolean isAtBottomLimit(){if (turretMotor.isRevLimitSwitchClosed() > 0){ return true; }else{ return false;}}
  public boolean isAtLimit(){if (turretMotor.isFwdLimitSwitchClosed() + turretMotor.isFwdLimitSwitchClosed() > 0){ return true; }else{ return false;}}

  @Override
  public void periodic() {
    //SmartDashboard.putNumber("Turret Pulse", turretMotor.getSelectedSensorPosition());
    SmartDashboard.putBoolean("Turret Forward Limit Switch", isAtTopLimit());
    SmartDashboard.putBoolean("Turret Reverse Limit Switch", isAtBottomLimit());

    SmartDashboard.putNumber("Limelight Azimuth", getLimelightAzimuth());
    SmartDashboard.putNumber("Turret Azimuth", getTurretHeading());
  }
}
