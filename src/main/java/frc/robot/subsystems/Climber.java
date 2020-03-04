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

  /**
   * Enables external control of the lift
   * @author Joe Sedutto
   * @param speed (Positive numbers are UP)
   */
  public void ClimberManualControl(double speed){
    speed = (speed * -1) - 0.025; // 0.025 helps prevent deadzone noise

    if (speed > 0){ // If the speed is greater than 0 (up)
      liftLockSolenoid.set(true); // Pull the lock
      if (liftLockSolenoid.get()){ // Lift lock must be disengaged (pulled) to drive up
        liftMotor.set(speed / 1); // Drive up
      }
      else{ // If user requests up but the lock did not report being engaged
        SmartDashboard.putString("Alert", "Liftlock failed to engage."); // Alert the user
      }
    }
    else{ // If the speed is less than 0 (down)
      liftLockSolenoid.set(false); // Drop the lock (not pulled)
      liftMotor.set(speed / 1); // Drive the motor down
    }
  }

  /**
   * Allows a user to force ovveride the commands
   * of ManualControl
   * @author Joe Sedutto
   * @param on
   */
  public void OverrideSolenoid(boolean on){
    liftLockSolenoid.set(on); // Set to that state.
  }

  public boolean isAtLowerLimit(){return (liftMotor.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyOpen).get());} // Gets the value of that limit switch

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("Lift Lower Limit", isAtLowerLimit());
  }
}
