/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {
  private CANSparkMax gantryMotor;
  private CANSparkMax liftMotor;

  private static final Solenoid liftLockSolenoid = new Solenoid(Constants.CANIds.Lift_Release_Solenoid_Address);

  /**
   * Creates a new Climber.
   */
  public Climber() { 
    // For initalization code
    gantryMotor = new CANSparkMax(Constants.CANIds.Gantry_Motor_Address, MotorType.kBrushless); // Creates a new motor
    gantryMotor.restoreFactoryDefaults();

    liftMotor = new CANSparkMax(Constants.CANIds.Lift_Motor_Address, MotorType.kBrushless);
    liftMotor.setInverted(true);
    liftMotor.restoreFactoryDefaults();
  }

  /**
   * sets the speed and does nothing else
   * @author Joe Sedutto
   * @param speed
   */
  public void GantryManualControl(double speed){
    if (Math.abs(speed) <= 0.05){
      
    }
    else{
      gantryMotor.set(speed);
    }
  }

  public void ManualControl(double speed, boolean isoverride, boolean override){
    speed = speed * -1;

    if (speed > 0){ // If the speed is greater than 0 (up)
      if (isoverride){
        liftLockSolenoid.set(override); // Engage the liftlock
      }
      else{
        liftLockSolenoid.set(true);
      }
      if (liftLockSolenoid.get()){ // Liftlock must be engaged to drive
        liftMotor.set(speed / 1); // up
      }
      else{ // If user requests up but the lock did not report being engaged
        SmartDashboard.putString("Alert", "Liftlock failed to engage."); // Alert the user
      }
    }
    else{ // If the speed is greater than 0 (down)
      if (isoverride){
        liftLockSolenoid.set(override); // Disengage the liftlock
      }
      else{
        liftLockSolenoid.set(false);
      }
      liftMotor.set(speed / 1); // Drive the motor down
    }
    SmartDashboard.putBoolean("Lift is override", isoverride);
  }

  public boolean isAtLowerLimit(){return (liftMotor.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyOpen).get());} // Gets the value of that limit switch

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("Lift Lower Limit", isAtLowerLimit());
  }
}
