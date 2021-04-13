package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.EncoderType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * Contains methods to drive the robot base.
 */
public class Drivetrain extends SubsystemBase {
  // Rev Robotics controllers For the win!
  private static final CANSparkMax portMotor = new CANSparkMax(
      Constants.CANAddresses.SparkMax_Port_Drive_Address, 
      MotorType.kBrushless);
  private static final CANSparkMax starboardMotor = new CANSparkMax(
      Constants.CANAddresses.SparkMax_Starboard_Drive_Address, 
      MotorType.kBrushless);
  private static final CANSparkMax portMotorSlave = new CANSparkMax(
      Constants.CANAddresses.SparkMax_Port_Drive_Slave_Address, 
      MotorType.kBrushless);
  private static final CANSparkMax starboardMotorSlave = new CANSparkMax(
      Constants.CANAddresses.SparkMax_Starboard_Drive_Slave_Address, 
      MotorType.kBrushless);

  // Shifting Gearboxes
  private static final DoubleSolenoid shiftingGearbox = new DoubleSolenoid(
        Constants.Pneumatics.Shift_Up_Solenoid,
        Constants.Pneumatics.Shift_Down_Solenoid);
  private static CANEncoder portMotorEncoder;
  private static CANEncoder starboardMotorEncoder;
  
  /**
   * The drivetrain subsystem controls the movement of the robot.
   */
  public Drivetrain() {
    // Set motors to default on boot
    portMotor.restoreFactoryDefaults();
    portMotorSlave.restoreFactoryDefaults();
    starboardMotor.restoreFactoryDefaults();
    starboardMotorSlave.restoreFactoryDefaults();

    // Set Inverted
    portMotor.setInverted(false);
    starboardMotor.setInverted(true);
    // Sets the output of the motor backwards
    //portMotor.setInverted(Constants.DrivetrainPID.portMotorInvert); 
    //starboardMotor.setInverted(Constants.DrivetrainPID.starboardMotorInvert);

    // Set followers
    portMotorSlave.follow(portMotor); // Set to follow the master controller
    //starboardMotorSlave.follow(starboardMotor); // Set to follow the master controller

    // Get encoder objects from the motor
    portMotorEncoder = portMotor.getEncoder(EncoderType.kHallSensor, 4096);
    starboardMotorEncoder = starboardMotor.getEncoder(EncoderType.kHallSensor, 4096);
  }

  /**
   * Using a joystick as input this method drives
   * the robot base.
   * @author Joe Sedutto
   @param steerage Steering value
   @param thro Throttle value
   */
  public void flyByWireA(double steerage, double thro) {
    starboardMotor.set(thro + (steerage)); // Set the starboard motor to the sum of thro - steerage
    portMotor.set(thro - (steerage)); // Set the port motor to the sum of thro + steerage
  }

  /**
   * Using a joystick as input with logarithmically dampened values.
   * @author Aidan B
   @param steerage Steering value
   @param thro Throttle Value
   */
  public void flyByWireB(double steerage, double thro) {
    double dampenedThro = 1 * Math.pow(thro, 3);
    double dampenedSteerage = 1 * Math.pow(steerage, 3);
    // Set the starboard motor to the sum of thro - steerage
    starboardMotor.set(dampenedThro + (dampenedSteerage));
    // Set the port motor to the sum of thro + steerage
    portMotor.set(dampenedThro - (dampenedSteerage)); 
  }

  /**
   * Drives the robot using two values for each wheel
   * in percentage output.
   * @author Joe Sedutto
   @param starboard Speed value for the starboard side
   @param port Speed value for the port side
   */
  public void flyWithWiresA(double starboard, double port) {
    starboardMotor.set(starboard);
    portMotor.set(port);
  }

  /**
   * Drives the robot using two values for each wheel
   * in velocity output (m/s).
   * @author Joe Sedutto
   * @param starboardMeters (A Value in M/s)
   * @param portMeters (A value in M/s)
   */
  public void flyWithWiresB(double starboardMeters, double portMeters) {
    // Convert the M/s into M/10ms, then, convert M/10ms into Ticks/10ms
    double starboardMiliseconds 
        = ((starboardMeters / 100) * Constants.DrivetrainPID.rotationsPerMeter);
    double portMiliseconds 
        = ((portMeters / 100) * Constants.DrivetrainPID.rotationsPerMeter);

    starboardMotor.set(starboardMiliseconds);
    portMotor.set(portMiliseconds);
  }

  /**
   * A method to set the shifting gearboxes to a manual gear.
   @param gear The target gear
   @author Joe Sedutto
   @param gear A value for the solenoids to fire with
   */
  public void shiftGearboxesStandard(DoubleSolenoid.Value gear) {
    shiftingGearbox.set(gear);
  }

  /**
   * A method to set the shifting gearboxes to a manual gear
   * without dropping into a low speed too quickly.
   * @author Joe Sedutto
   * @author Aidan
   * @return
   */
  public boolean shiftGearboxesAutomatic(boolean targetGear) {
    if (targetGear == false && Math.abs(getOverallSpeed()) < Constants.Misc.Downshift_Max_Speed) { 
      // If the user want to shift down, they must be going below the max speed
      shiftGearboxesStandard(DoubleSolenoid.Value.kReverse);
      return true;
    } else if (
        targetGear == true && Math.abs(getOverallSpeed()) > Constants.Misc.Upshift_Min_Speed) { 
      // If a user wants to shift up, they must be going above the min speed
      shiftGearboxesStandard(DoubleSolenoid.Value.kForward);
      return true;
    } else { // All other conditions will result in a false
      return false;
    }
  }

  public static double getDriveEncoderPort() {
    // Returns the encoder value of the port motor-
    return portMotorEncoder.getPosition(); 
  } 

  public static double getDriveEncoderStarboard() {
    // Returns the encoder value of the starboard motor
    return starboardMotorEncoder.getPosition();
  }

  public static double getOverallSpeed() {
    // Returns the average speed of the robot in knots
    return ((starboardMotorEncoder.getVelocity() + portMotorEncoder.getVelocity()) / 2) / Constants.DrivetrainPID.rotationsPerMeter;
  }

  /**
   * Sets the encoders back to 0.
   * @param position NotImplemented
   */
  public static void resetEncoders(int position) {
    // Sets the encoder values to the number you pass
    portMotorEncoder.setPosition(0); 
    starboardMotorEncoder.setPosition(0);
  } 

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Port Side Encoder", getDriveEncoderPort());
    SmartDashboard.putNumber("Starboard Side Encoder", getDriveEncoderStarboard());
  }
}
