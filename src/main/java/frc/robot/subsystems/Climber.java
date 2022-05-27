/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {
  // Can Spark Max motor controllers
  private CANSparkMax gantryMotor;
  private CANSparkMax liftMotor;

  // Climber Lock solenoid
  private Solenoid liftLockSolenoid;

  /**
   * Creates a new Climber.
   */
  public Climber() {
    // For initalization code
    // Creates a new motor
    gantryMotor = new CANSparkMax(
        Constants.CANAddresses.MiniNeo_Gantry_Motor_Address,
        MotorType.kBrushless);
    gantryMotor.restoreFactoryDefaults();

    liftMotor = new CANSparkMax(
        Constants.CANAddresses.Neo_Lift_Motor_Address,
        MotorType.kBrushless);
    liftMotor.restoreFactoryDefaults();

    // Initalize the lift lock solenoid
    liftLockSolenoid = new Solenoid(
        PneumaticsModuleType.CTREPCM,
        Constants.Pneumatics.Lift_Release_Solenoid);
  }

  /**
   * sets the speed and does nothing else.
   * 
   * @author Joe Sedutto
   * @param speed A speed value
   */
  public void gantryManualControl(double speed) {
    if (Math.abs(speed) <= 0.1) {
      // do nothing.
      gantryMotor.set(0);
    } else {
      gantryMotor.set(speed);
    }
  }

  /**
   * Manually controls the climber and automaticly control up and down rates.
   * TODO: These rates need to be field asjustable.
   * 
   * @author Joe Sedutto
   * @param speed A value between -1 and 1
   */
  public void manualControl(double speed) {
    speed = speed * -1;

    if (Math.abs(speed) <= 0.07) {
      // do nothing.
      liftMotor.set(0);
    } else if (speed >= 0.15) { // Continue upwards if you push harder
      liftLockSolenoid.set(true);
      liftMotor.set(speed / 2); // Up
    } else if (speed >= 0.11) { // For very small up values, invert the direction, unlock the lift
      liftLockSolenoid.set(true); // unlock the lift.
      liftMotor.set((speed / 4) * -1); // Up (inverted)
    } else if (speed <= -0.4) { // Down, fast!
      liftLockSolenoid.set(false); // Keep the lift locked at high downward speed
      liftMotor.set(speed / 1.6); // Down
    } else {
      liftLockSolenoid.set(false); // Lock the lift, and pull down
      liftMotor.set(speed / 1.6); // Down
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
