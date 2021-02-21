package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * Contains methods to drive the robot base.
 */
public class Drivetrain extends SubsystemBase {
  // Rev Robotics controllers For the win!
  private static final CANSparkMax portMotor = new CANSparkMax(Constants.CANIds.CANSparkMax_Port_Address, MotorType.kBrushless);
  private static final CANSparkMax starboardMotor = new CANSparkMax(Constants.CANIds.CANSparkMax_Starboard_Address, MotorType.kBrushless);
  private static final CANSparkMax portMotorSlave = new CANSparkMax(Constants.CANIds.CANSparkMax_Port_Slave_Address, MotorType.kBrushless);
  private static final CANSparkMax starboardMotorSlave = new CANSparkMax(Constants.CANIds.CANSparkMax_Starboard_Slave_Address, MotorType.kBrushless);

  // Shifting Gearboxes
  private static final Solenoid starboardSolenoid = new Solenoid(Constants.CANIds.Starboard_Solenoid_Address);
  private static final Solenoid portSolenoid = new Solenoid(Constants.CANIds.Port_Solenoid_Address);
  private static CANEncoder portMotorEncoder, starboardMotorEncoder;
  
  /**
   * The drivetrain subsystem controls the movement of the robot.
   */
  public Drivetrain() {
    // Set Inverted
    portMotor.setInverted(Constants.DrivetrainPID.portMotorInvert); // Sets the output of the motor backwards
    starboardMotor.setInverted(Constants.DrivetrainPID.starboardMotorInvert);

    // Set followers
    portMotorSlave.follow(portMotor); // Set to follow the master controller
    starboardMotorSlave.follow(starboardMotor); // Set to follow the master controller

    // Get encoder objects from the motor
    portMotorEncoder = portMotor.getEncoder();
    starboardMotorEncoder = starboardMotor.getEncoder();
  }

  /**
   * Using a joystick as input this method drives
   * the robot base.
   * @author Joe Sedutto
   * @param steerage
   * @param thro
   */
  public void FlyByWireA(double steerage, double thro){
    starboardMotor.set((thro + (steerage)) * -1); // Set the starboard motor to the sum of thro - steerage
    portMotor.set((thro - (steerage)) * -1); // Set the port motor to the sum of thro + steerage
  }

  /**
   * Drives the robot using two values for each wheel
   * in percentage output
   * @author Joe Sedutto
   * @param starboard
   * @param port
   */
  public void FlyWithWiresA(double starboard, double port){
    starboardMotor.set(starboard);
    portMotor.set(port);
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
    double starboard_miliseconds = ((starboard_meters / 100) * Constants.DrivetrainPID.rotationsPerMeter);
    double port_miliseconds = ((port_meters / 100) * Constants.DrivetrainPID.rotationsPerMeter);

    starboardMotor.set(starboard_miliseconds);
    portMotor.set(port_miliseconds);
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
   * @author Joe Sedutto
   * @author Aidan
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

  public static double getDriveEncoderPort(){return portMotorEncoder.getCountsPerRevolution();} // Returns the encoder value of the port motor
  public static double getDriveEncoderStarboard(){return starboardMotorEncoder.getCountsPerRevolution();} // Returns the encoder value of the starboard motor
  public static double getOverallSpeed(){return ((starboardMotorEncoder.getVelocity() + portMotorEncoder.getVelocity()) / 2) / Constants.DrivetrainPID.rotationsPerMeter;} // Returns the average speed of the robot in knots

  public static void resetEncoders(int position){portMotorEncoder.setPosition(0); starboardMotorEncoder.setPosition(0);} // Sets the encoder values to the number you pass

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
