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
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  // FalconFX objects
  private static TalonFX shooterMotor;

  // Neo 550
  private static CANSparkMax turretMotor;

  // Solenoids
  private static DoubleSolenoid shooterHood;

  // Dumb targeting
  private static final NetworkTable limelight 
      = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry tx = limelight.getEntry("tx");
  NetworkTableEntry ty = limelight.getEntry("ty");
  NetworkTableEntry ta = limelight.getEntry("ta");
  NetworkTableEntry camModeEntry = limelight.getEntry("camMode");
  NetworkTableEntry pipelineEntry = limelight.getEntry("pipeline");

  /**
   * Creates a new Turret.
   */
  public Shooter() {
    // Setup Motors
    shooterMotor = new TalonFX(
      Constants.CANAddresses.TalonFX_Shooter_Address);
    
    turretMotor = new CANSparkMax(
      Constants.CANAddresses.Turret_Motor_Address,
      MotorType.kBrushless);    

    // Setup other
    //shooterHood = new DoubleSolenoid(
    //  Constants.Pneumatics.Hood_Solenoid_Forward, 
    //  Constants.Pneumatics.Hood_Solenoid_Forward);

    // Shooter PID and init
    // Set motors to default to prevent weirdness
    shooterMotor.configFactoryDefault();

    // Set break mode
    shooterMotor.setNeutralMode(Constants.ShooterPID.shooterBreakMode);

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
    shooterMotor.configAllowableClosedloopError(
        0, 
        Constants.ShooterPID.kPIDLoopIdx, 
        Constants.ShooterPID.kTimeoutMs);

    // Config slot0 gains
    shooterMotor.config_kF(
        Constants.ShooterPID.kPIDLoopIdx, 
        Constants.ShooterPID.kGains.kF, 
        Constants.ShooterPID.kTimeoutMs);
    shooterMotor.config_kP(
        Constants.ShooterPID.kPIDLoopIdx, 
        Constants.ShooterPID.kGains.kP, 
        Constants.ShooterPID.kTimeoutMs);
    shooterMotor.config_kI(
        Constants.ShooterPID.kPIDLoopIdx, 
        Constants.ShooterPID.kGains.kI, 
        Constants.ShooterPID.kTimeoutMs);
    shooterMotor.config_kD(
        Constants.ShooterPID.kPIDLoopIdx, 
        Constants.ShooterPID.kGains.kD, 
        Constants.ShooterPID.kTimeoutMs);    

    // Set Inverted
    // Sets the output of the motor backwards
    shooterMotor.setInverted(Constants.ShooterPID.shooterMotorInvert);

    // Turret PID and init
    // Set motors to default to prevent weirdness
    turretMotor.restoreFactoryDefaults();

    // Set feedback sensors here
    //turretMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 
    //                                         Constants.DrivetrainPID.kPIDLoopIdx,
    //                                         Constants.DrivetrainPID.kTimeoutMs);
    //
    // Sets the position to clear when the F limit switch is pressed
    //turretMotor.configClearPositionOnLimitF(true, Constants.DrivetrainPID.kTimeoutMs);

    // Fix sensor phase here
    //turretMotor.setSensorPhase(Constants.TurretPID.turretSensorPhase);

    // Configure nominal outputs
    //turretMotor.configNominalOutputForward(0, Constants.TurretPID.kTimeoutMs);
    //turretMotor.configNominalOutputReverse(0, Constants.TurretPID.kTimeoutMs);
    //turretMotor.configPeakOutputForward(1, Constants.TurretPID.kTimeoutMs);
    //turretMotor.configPeakOutputReverse(-1, Constants.TurretPID.kTimeoutMs);
    
    // Configure allowable closed loop error (dead zone)
    //turretMotor.configAllowableClosedloopError(
    //    0, 
    //    Constants.TurretPID.kPIDLoopIdx, 
    //    Constants.TurretPID.kTimeoutMs);

    // Config slot0 gains
    //turretMotor.config_kF(
    //    Constants.TurretPID.kPIDLoopIdx, 
    //    Constants.TurretPID.kGains.kF, 
    //    Constants.TurretPID.kTimeoutMs);
    //turretMotor.config_kP(
    //    Constants.TurretPID.kPIDLoopIdx, 
    //    Constants.TurretPID.kGains.kP, 
    //    Constants.TurretPID.kTimeoutMs);
    //turretMotor.config_kI(
    //    Constants.TurretPID.kPIDLoopIdx, 
    //    Constants.TurretPID.kGains.kI, 
    //    Constants.TurretPID.kTimeoutMs);
    //turretMotor.config_kD(
    //    Constants.TurretPID.kPIDLoopIdx, 
    //    Constants.TurretPID.kGains.kD, 
    //    Constants.TurretPID.kTimeoutMs);    

    // Set Inverted
    // Sets the output of the motor backwards
    turretMotor.setInverted(Constants.TurretPID.turretMotorInvert);
  }
  
  /**
   * Sets the shooter motor to some speed
   * Does not use velocity control.
   * @author Joe Sedutto
   * @param speed A target speed for the shooter. FOR TESTING ONLY
   * @Deprecated DONT USE, ONLY FOR TESTING
   */
  public void testShooter(double speed) {
    shooterMotor.set(ControlMode.PercentOutput, speed);
  }

  /**
   * For testing only.
   * @author Joe Sedutto
   * @param speed A target speed for the turret.
   * @Deprecated DONT USE, ONLY FOR TESTING 
   */
  public void testTurret(double speed) {
    turretMotor.set(speed);
  }

  /**
   * Takes a variable state, that sets the state of the hood manually.
   * @param state Either off, forward or reverse.
   */
  void setHoodAngle(DoubleSolenoid.Value state) {
    shooterHood.set(state);
  }

  /**
   * Set the shooter solenoid to the state listed here.
   @author Joe Sedutto
   @param state The state the shooter should be in
   @deprecated Don't use this! use method in magazine.java instead.
   */
  public void releaseShooter(boolean state) {
    //ballReleaseSolenoid.set(state);
  }

  /**
   * Takes an angle in radians and converts
   * it to ticks to send to the turret control.
   * @author Joe Sedutto
   * @param heading A target heading
   */
  public void targetHeading(double heading) {
    //turretMotor.set(ControlMode.Position, heading * Constants.TurretPID.ticksPerRadian); TODO: Fix this
  }

  /**
   * Takes a int and sets the pipeline accordingly.
   * @param pipeline A pipeline to switch to.
   */
  public void switchPipelines(int pipeline) {
    pipelineEntry.setNumber(pipeline);
    //camMode_entry.setNumber(pipeline);
  }


  public double getTurretHeading() {
    //return (turretMotor.grtPosit() / Constants.TurretPID.ticksPerRadian);
    return -1; //TODO: Fix this
  }

  public double getLimelightHeading() {
    return Math.toRadians(tx.getDouble(0.0));
  }

  public double getLimelightElevation() {
    return Math.toRadians(ty.getDouble(0.0));
  }
  
  public double getLimelightDistance() {
    return ta.getDouble(0.0);
  }

  /**
   * Returns true if we're at the max turn of the turret.
   * @author Joe
   * @return if we're at the top limit of the turret
   */
  public boolean isAtTopLimit() {
    //if (turretMotor.isFwdLimitSwitchClosed() > 0) {
    //  return true; 
    //} else {
    //  return false;
    //}
    return false; //TODO: Fix this
  }

  /**
   * Returns true if we're at the min turn of the turret.
   * @author Joe
   * @return if we're at the bottom limit of the turret
   */
  public boolean isAtBottomLimit() {
    //if (turretMotor.isFwdLimitSwitchClosed() > 0) {
    //  return true; 
    //} else {
    //  return false;
    //}
    return false; //TODO: Fix this
  }

  /**
   * Returns true if we're at any limit of the turret.
   * @author Joe
   * @return if we're at any turret limit.
   */
  public boolean isAtLimit() {
    //if (turretMotor.isFwdLimitSwitchClosed() + turretMotor.isFwdLimitSwitchClosed() > 0) {
    //  return true; 
    //} else {
    //  return false;
    //}
    return false; //TODO: Fix this
  }

  @Override
  public void periodic() {
    //SmartDashboard.putNumber("Turret Pulse", turretMotor.getSelectedSensorPosition());
    SmartDashboard.putNumber("Turret Position", Shooter.turretMotor.getEncoder().getPosition());
  }
}
