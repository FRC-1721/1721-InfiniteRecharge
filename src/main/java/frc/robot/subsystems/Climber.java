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
import frc.robot.LiveSettings;

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
    speed = (speed * 1);

    switch(LiveSettings.elevatorMode.getValue()){
      case normal: // If set to normal mode
      if (speed > Constants.OperatorInputSettings.LiftDeadzone){ // If going up
        liftLockSolenoid.set(true); // Pull the lock
        if (liftLockSolenoid.get() && speed > Constants.OperatorInputSettings.LiftDeadzone + 0.1){ // Lift lock must be disengaged
          liftMotor.set(speed / 1); // Drive up
        }
      }
      else if (speed < -Constants.OperatorInputSettings.LiftDeadzone){ // If going down
        liftLockSolenoid.set(true); // Pull the lock
        if (liftLockSolenoid.get() && speed < -Constants.OperatorInputSettings.LiftDeadzone - 0.1){ // Lift lock must be disengaged
          liftMotor.set(speed / 1); // Drive down
        }
      }
      else{
        liftLockSolenoid.set(false);
        liftMotor.set(0);
      }
      break;

      case disengaged: // If set to disabled
      break; // Do nothing if disengaged.

      case auxiliary: // If set to aux
      liftLockSolenoid.set(true); // Just lockup the solenoid on
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
