package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
   * Contains methods to drive the robot
   */
public class Drivetrain extends SubsystemBase {
  private static final TalonSRX portmotor = new TalonSRX(Constants.CANIds.TalonSRX_Port_ID); // Init the port motor at 1
  private static final TalonSRX starboardmotor = new TalonSRX(Constants.CANIds.TalonSRX_Starboard_ID); // Init the starboard at 2
  
  /**
   * The drivetrain subsystem controls the movement of the robot.
   */
  public Drivetrain() {
    portmotor.setInverted(true); // Sets the output of the starboard motor backwards
  }

  /**
   * Using a joystick as input this method drives
   * the robot base.
   * @author Joe Sedutto
   */
  public void FlyByWireA(double steerage, double thro){
    starboardmotor.set(ControlMode.PercentOutput, -thro + (steerage)); // Set the starboard motor to the sum of thro - steerage
    portmotor.set(ControlMode.PercentOutput, -thro - (steerage)); // Set the port motor to the sum of thro + steerage
  }

  public void FlyWithWiresA(double starboard, double port){
    starboardmotor.set(ControlMode.PercentOutput, starboard);
    portmotor.set(ControlMode.PercentOutput, port);
  }

  public static double getDriveEncoderPort(){return portmotor.getSelectedSensorPosition();} // Returns the encoder value of the port motor
  public static double getDriveEncoderStarboard(){return starboardmotor.getSelectedSensorPosition();} // Returns the encoder value of the starboard motor
  public static double getOverallSpeed(){return (((starboardmotor.getSelectedSensorVelocity() + portmotor.getSelectedSensorVelocity()) / 2) * 10) / Constants.RobotOperatingSystem.ticksPerMeter;} // Returns the average speed of the robot in knots


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
