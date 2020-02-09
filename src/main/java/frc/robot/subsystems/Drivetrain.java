package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * Contains methods to drive the robot base.
 */
public class Drivetrain extends SubsystemBase {
  // TalonSRX objects
  private static final TalonSRX portMotor = new TalonSRX(Constants.CANIds.TalonSRX_Port_Address); // Create a new TalonSRX Object
  private static final TalonSRX starboardMotor = new TalonSRX(Constants.CANIds.TalonSRX_Starboard_Address); // Create a new TalonSRX Object

  // VictorSPX objects
  private static final VictorSPX portMotorSlave0 = new VictorSPX(Constants.CANIds.VictorSPX_Port_Slave_Address0);
  private static final VictorSPX portMotorSlave1 = new VictorSPX(Constants.CANIds.VictorSPX_Port_Slave_Address1);
  private static final VictorSPX starboardMotorSlave0 = new VictorSPX(Constants.CANIds.VictorSPX_Starboard_Slave_Address0);
  private static final VictorSPX starboardMotorSlave1 = new VictorSPX(Constants.CANIds.VictorSPX_Starboard_Slave_Address1);
  
  /**
   * The drivetrain subsystem controls the movement of the robot.
   */
  public Drivetrain() {
    // Configure PID
    // Set motors to default to prevent weirdness
    portMotor.configFactoryDefault();
    starboardMotor.configFactoryDefault();

    portMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 
                                           Constants.DrivetrainPID.kPIDLoopIdx,
                                           Constants.DrivetrainPID.kTimeoutMs);
    starboardMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 
                                                Constants.DrivetrainPID.kPIDLoopIdx,
                                                Constants.DrivetrainPID.kTimeoutMs);
    portMotor.setSensorPhase(Constants.DrivetrainPID.portSensorPhase);
    starboardMotor.setSensorPhase(Constants.DrivetrainPID.starboardSensorPhase);

    // Configure nominal outputs
    portMotor.configNominalOutputForward(0, Constants.DrivetrainPID.kTimeoutMs);
		portMotor.configNominalOutputReverse(0, Constants.DrivetrainPID.kTimeoutMs);
		portMotor.configPeakOutputForward(1, Constants.DrivetrainPID.kTimeoutMs);
    portMotor.configPeakOutputReverse(-1, Constants.DrivetrainPID.kTimeoutMs);
    starboardMotor.configNominalOutputForward(0, Constants.DrivetrainPID.kTimeoutMs);
		starboardMotor.configNominalOutputReverse(0, Constants.DrivetrainPID.kTimeoutMs);
		starboardMotor.configPeakOutputForward(1, Constants.DrivetrainPID.kTimeoutMs);
    starboardMotor.configPeakOutputReverse(-1, Constants.DrivetrainPID.kTimeoutMs);
    
    // Configure allowable closed loop error (dead zone)
    portMotor.configAllowableClosedloopError(0, Constants.DrivetrainPID.kPIDLoopIdx, Constants.DrivetrainPID.kTimeoutMs);
    starboardMotor.configAllowableClosedloopError(0, Constants.DrivetrainPID.kPIDLoopIdx, Constants.DrivetrainPID.kTimeoutMs);

    // Config slot0 gains
    portMotor.config_kF(Constants.DrivetrainPID.kPIDLoopIdx, Constants.DrivetrainPID.kGains.kF, Constants.DrivetrainPID.kTimeoutMs);
		portMotor.config_kP(Constants.DrivetrainPID.kPIDLoopIdx, Constants.DrivetrainPID.kGains.kP, Constants.DrivetrainPID.kTimeoutMs);
		portMotor.config_kI(Constants.DrivetrainPID.kPIDLoopIdx, Constants.DrivetrainPID.kGains.kI, Constants.DrivetrainPID.kTimeoutMs);
    portMotor.config_kD(Constants.DrivetrainPID.kPIDLoopIdx, Constants.DrivetrainPID.kGains.kD, Constants.DrivetrainPID.kTimeoutMs);
    starboardMotor.config_kF(Constants.DrivetrainPID.kPIDLoopIdx, Constants.DrivetrainPID.kGains.kF, Constants.DrivetrainPID.kTimeoutMs);
		starboardMotor.config_kP(Constants.DrivetrainPID.kPIDLoopIdx, Constants.DrivetrainPID.kGains.kP, Constants.DrivetrainPID.kTimeoutMs);
		starboardMotor.config_kI(Constants.DrivetrainPID.kPIDLoopIdx, Constants.DrivetrainPID.kGains.kI, Constants.DrivetrainPID.kTimeoutMs);
    starboardMotor.config_kD(Constants.DrivetrainPID.kPIDLoopIdx, Constants.DrivetrainPID.kGains.kD, Constants.DrivetrainPID.kTimeoutMs);
    
    // Get the current abs position, and set the rel sensor to match
    int absolutePortPosition = portMotor.getSensorCollection().getPulseWidthPosition();
    int absoluteStarboardPosition = starboardMotor.getSensorCollection().getPulseWidthPosition();

		/* Mask out overflows, keep bottom 12 bits */ // From CTRE Examples
		absolutePortPosition &= 0xFFF;
		if (Constants.DrivetrainPID.portSensorPhase) { absolutePortPosition *= -1; } // Invert Once
    if (Constants.DrivetrainPID.portMotorInvert) { absolutePortPosition *= -1; } // Invert Twice
    
    absoluteStarboardPosition &= 0xFFF;
		if (Constants.DrivetrainPID.portSensorPhase) { absoluteStarboardPosition *= -1; } // Invert Once
		if (Constants.DrivetrainPID.starboardMotorInvert) { absolutePortPosition *= -1; } // Invert Twice
		
    /* Set the quadrature (relative) sensor to match absolute */ // From CTRE Examples
    portMotor.setSelectedSensorPosition(absolutePortPosition, Constants.DrivetrainPID.kPIDLoopIdx, Constants.DrivetrainPID.kTimeoutMs);
    starboardMotor.setSelectedSensorPosition(absoluteStarboardPosition, Constants.DrivetrainPID.kPIDLoopIdx, Constants.DrivetrainPID.kTimeoutMs);
    

    // Set Inverted
    portMotor.setInverted(Constants.DrivetrainPID.portMotorInvert); // Sets the output of the motor backwards
    portMotorSlave0.setInverted(Constants.DrivetrainPID.portMotorInvert);
    portMotorSlave1.setInverted(Constants.DrivetrainPID.portMotorInvert);

    starboardMotor.setInverted(Constants.DrivetrainPID.starboardMotorInvert);
    starboardMotorSlave0.setInverted(Constants.DrivetrainPID.starboardMotorInvert);
    starboardMotorSlave1.setInverted(Constants.DrivetrainPID.starboardMotorInvert);

    // Set followers
    portMotorSlave0.follow(portMotor); // Set to follow the master controller
    portMotorSlave1.follow(portMotor); // Set to follow the master controller
    starboardMotorSlave0.follow(starboardMotor); // Set to follow the master controller
    starboardMotorSlave1.follow(starboardMotor); // Set to follow the master controller
  }

  /**
   * Using a joystick as input this method drives
   * the robot base.
   * @author Joe Sedutto
   * @param steerage
   * @param thro
   */
  public void FlyByWireA(double steerage, double thro){
    starboardMotor.set(ControlMode.PercentOutput, (thro + (steerage)) * -1); // Set the starboard motor to the sum of thro - steerage
    portMotor.set(ControlMode.PercentOutput, (thro - (steerage)) * -1); // Set the port motor to the sum of thro + steerage
  }

  /**
   * Drives the robot using two values for each wheel
   * in percentage output
   * @author Joe Sedutto
   * @param starboard
   * @param port
   */
  public void FlyWithWiresA(double starboard, double port){
    starboardMotor.set(ControlMode.PercentOutput, starboard);
    portMotor.set(ControlMode.PercentOutput, port);
  }

  public static double getDriveEncoderPort(){return portMotor.getSelectedSensorPosition();} // Returns the encoder value of the port motor
  public static double getDriveEncoderStarboard(){return starboardMotor.getSelectedSensorPosition();} // Returns the encoder value of the starboard motor
  public static double getOverallSpeed(){return (((starboardMotor.getSelectedSensorVelocity() + portMotor.getSelectedSensorVelocity()) / 2) * 10) / Constants.DrivetrainPID.ticksPerMeter;} // Returns the average speed of the robot in knots

  public static void resetEncoders(int position){portMotor.setSelectedSensorPosition(position, Constants.DrivetrainPID.kPIDLoopIdx, Constants.DrivetrainPID.kTimeoutMs); starboardMotor.setSelectedSensorPosition(position, Constants.DrivetrainPID.kPIDLoopIdx, Constants.DrivetrainPID.kTimeoutMs);} // Sets the encoder values to the number you pass

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
