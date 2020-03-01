package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Solenoid;
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

  // Shifting Gearboxes
  private static final Solenoid starboardSolenoid = new Solenoid(Constants.CANIds.Starboard_Solenoid_Address);
  private static final Solenoid portSolenoid = new Solenoid(Constants.CANIds.Port_Solenoid_Address);
  
  /**
   * The drivetrain subsystem controls the movement of the robot.
   */
  public Drivetrain() {
    // Configure PID
    // Set motors to default to prevent weirdness
    portMotor.configFactoryDefault();
    starboardMotor.configFactoryDefault();

    // Set feedback sensors here
    portMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 
                                           Constants.DrivetrainPID.kPIDLoopIdx,
                                           Constants.DrivetrainPID.kTimeoutMs);
    starboardMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 
                                                Constants.DrivetrainPID.kPIDLoopIdx,
                                                Constants.DrivetrainPID.kTimeoutMs);
    
    // Fix sensor phase here
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

  /**
   * Drives the robot using two values for each wheel
   * in velocity output (m/s)
   * @author Joe Sedutto
   * @param starboard_meters (A Value in M/s)
   * @param port_meters (A value in M/s)
   */
  public void FlyWithWiresB(double starboard_meters, double port_meters){
    // Convert the M/s into M/10ms, then, convert M/10ms into Ticks/10ms
    double starboard_miliseconds = ((starboard_meters / 100) * Constants.DrivetrainPID.ticksPerMeter);
    double port_miliseconds = ((port_meters / 100) * Constants.DrivetrainPID.ticksPerMeter);

    starboardMotor.set(ControlMode.Velocity, starboard_miliseconds);
    portMotor.set(ControlMode.Velocity, port_miliseconds);
  }

  /**
   * A method to set the shifting gearboxes to a manual gear.
   * @param gear
   * @author Joe Sedutto
   */
  public void ShiftGearboxesStandard(boolean gear){
    starboardSolenoid.set(gear);
    portSolenoid.set(gear);
  }

  /**
   * A method to set the shifting gearboxes to a manual gear
   * without dropping into a low speed too quickly.
   * @return
   */
  public boolean ShiftGearboxesAutomatic(boolean target_gear){
    if (target_gear == false && Math.abs(getOverallSpeed()) < Constants.Misc.Downshift_Max_Speed){ // If the user want to shift down, they must be going below the max speed
      ShiftGearboxesStandard(false);
      return true;
    }
    else if (target_gear == true && Math.abs(getOverallSpeed()) > Constants.Misc.Upshift_Min_Speed){ // If a user wants to shift up, they must be going above the min speed
      ShiftGearboxesStandard(true);
      return true;
    }
    else{ // All other conditions will result in a false
      return false;
    }
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
