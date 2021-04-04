/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {
  private CANSparkMax gantryMotor;
  private CANSparkMax liftMotor;

  /**
   * Creates a new Climber.
   */
  public Climber() { 
    // For initalization code
    // Creates a new motor
    gantryMotor 
      = new CANSparkMax(Constants.CANIds.Gantry_Motor_Address, MotorType.kBrushless); 
    gantryMotor.restoreFactoryDefaults();

    liftMotor = new CANSparkMax(Constants.CANIds.Lift_Motor_Address, MotorType.kBrushless);
    liftMotor.restoreFactoryDefaults();
  }

  /**
   * sets the speed and does nothing else.
   * @author Joe Sedutto
   * @param speed A speed value
   */
  public void gantryManualControl(double speed) {
    if (Math.abs(speed) <= 0.05) {
      
    } else {
      gantryMotor.set(speed);
    }
  }

  /**
   * Manually controls the climber and automaticly control up and down rates.
   * TODO: These rates need to be field asjustable.
   * @author Joe Sedutto
   * @param speed A value between -1 and 1
   */
  public void manualControl(double speed) {
    speed = speed * -1;

    if (speed < 0) {
      liftMotor.set(speed / 1.6); // Down
    } else {
      liftMotor.set(speed / 2); // Up
    }
    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
