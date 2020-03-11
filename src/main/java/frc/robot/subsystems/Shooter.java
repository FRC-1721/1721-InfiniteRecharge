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
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  // FalconFX objects
  private static final TalonFX shooterMotor = new TalonFX(Constants.CANIds.TalonFX_Shooter_Address);

  // Dumb targeting
  private static final NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry tx = limelight.getEntry("tx");
  NetworkTableEntry ty = limelight.getEntry("ty");
  NetworkTableEntry ta = limelight.getEntry("ta");
  NetworkTableEntry camMode_entry = limelight.getEntry("camMode");
  NetworkTableEntry pipeline_entry = limelight.getEntry("pipeline");

  // Flag Information
  private static double targetVelocity;

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
   * Sets the shooter velocity.
   * @author Joe Sedutto
   * @param velocity (ticks/10ms)
   */
  public void setShooterVelocity(double velocity){
    targetVelocity = velocity; // We use targetVelocity to check weather ready to shoot 
    shooterMotor.set(ControlMode.Velocity, velocity);
  }

  /**
   * Takes a int and sets the pipeline accordingly
   * @param pipeline
   */
  public void switchPipelines(int pipeline) {
    pipeline_entry.setNumber(pipeline);
    //camMode_entry.setNumber(pipeline);
  }


  

  public double getLimelightAzimuth(){return Math.toRadians(tx.getDouble(0.0));}
  public double getLimelightElevation(){return Math.toRadians(ty.getDouble(0.0));}
  public double getRoughLimelightDistance(){return ta.getDouble(0.0);}
  public static double getShooterVelocity(){return shooterMotor.getSelectedSensorVelocity();}

  public static boolean isReadyToFire(){return ( (targetVelocity > getShooterVelocity() - Constants.ShooterPID.AcceptableVelocityError) && (targetVelocity < getShooterVelocity() + Constants.ShooterPID.AcceptableVelocityError) && (targetVelocity > 100));}

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Limelight Azimuth", getLimelightAzimuth());
    SmartDashboard.putBoolean("Is ready to fire", isReadyToFire());
    SmartDashboard.putNumber("Shooter Temperature", shooterMotor.getTemperature());

    if (isReadyToFire()){
      SmartDashboard.putString("Alert", "Ready to fire");
    }
  }
}
