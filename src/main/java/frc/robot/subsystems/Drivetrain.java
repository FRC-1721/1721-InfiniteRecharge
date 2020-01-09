package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
   * Contains methods to drive the robot
   */
public class Drivetrain extends SubsystemBase {
  public static final Joystick DriverStick = new Joystick(Constants.DriverInputSettings.Driver_Stick_Port);
  private static final TalonSRX portmotor = new TalonSRX(Constants.CANIds.TalonSRX_Port_ID); // Init the port motor at 1
  private static final TalonSRX starboardmotor = new TalonSRX(Constants.CANIds.TalonSRX_Starboard_ID); // Init the starboard at 2
  
  /**
   * The drivetrain subsystem controls the movement of the robot.
   */
  public Drivetrain() {
    starboardmotor.setInverted(true); // Sets the output of the starboard motor backwards
  }

  /**
   * Using a joystick as input this method drives
   * the robot base.
   * @param DriverJoystick
   * @author Joe Sedutto
   */
  public static void FlyByWireA(){
    double steerage = DriverStick.getRawAxis(0); // Set the variable steerage to the value of the 0 axis on the driverstick
    double thro = DriverStick.getRawAxis(1); // Set the variable thro to the value of the 1 axis on the driverstick

    starboardmotor.set(ControlMode.PercentOutput, (thro - steerage) / 2); // Set the starboard motor to the sum of thro - steerage
    portmotor.set(ControlMode.PercentOutput, (thro + steerage) / 2); // Set the port motor to the sum of thro + steerage
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
