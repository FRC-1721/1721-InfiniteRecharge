package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * Contains methods to drive the robot base.
 */
public class Drivetrain extends SubsystemBase {
  // TalonSRX objects
  private static final TalonSRX portMotor = new TalonSRX(Constants.CANIds.TalonSRX_Port_ID); // Create a new TalonSRX Object
  private static final TalonSRX starboardMotor = new TalonSRX(Constants.CANIds.TalonSRX_Starboard_ID); // Create a new TalonSRX Object

  // VictorSPX objects
  private static final VictorSPX portMotorSlave0 = new VictorSPX(Constants.CANIds.VictorSPX_Port_Slave_Id0);
  private static final VictorSPX portMotorSlave1 = new VictorSPX(Constants.CANIds.VictorSPX_Port_Slave_Id1);
  private static final VictorSPX starboardMotorSlave0 = new VictorSPX(Constants.CANIds.VictorSPX_Starboard_Slave_Id0);
  private static final VictorSPX starboardMotorSlave1 = new VictorSPX(Constants.CANIds.VictorSPX_Starboard_Slave_Id1);
  
  /**
   * The drivetrain subsystem controls the movement of the robot.
   */
  public Drivetrain() {
    // Set Inverted
    portMotor.setInverted(true); // Sets the output of the motor backwards
    portMotorSlave0.setInverted(true);
    portMotorSlave1.setInverted(true);

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
   */
  public void FlyByWireA(double steerage, double thro){
    starboardMotor.set(ControlMode.PercentOutput, (thro + (steerage)) * -1); // Set the starboard motor to the sum of thro - steerage
    portMotor.set(ControlMode.PercentOutput, (thro - (steerage)) * -1); // Set the port motor to the sum of thro + steerage
  }

  public void FlyWithWiresA(double starboard, double port){
    starboardMotor.set(ControlMode.PercentOutput, starboard);
    portMotor.set(ControlMode.PercentOutput, port);
  }

  public static double getDriveEncoderPort(){return portMotor.getSelectedSensorPosition();} // Returns the encoder value of the port motor
  public static double getDriveEncoderStarboard(){return starboardMotor.getSelectedSensorPosition();} // Returns the encoder value of the starboard motor
  public static double getOverallSpeed(){return (((starboardMotor.getSelectedSensorVelocity() + portMotor.getSelectedSensorVelocity()) / 2) * 10) / Constants.RobotOperatingSystem.ticksPerMeter;} // Returns the average speed of the robot in knots


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
