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

  /**
   * Creates a new Climber.
   */
  public Climber() { 
    // For initalization code
    gantryMotor = new CANSparkMax(Constants.CANIds.Gantry_Motor_Address, MotorType.kBrushless); // Creates a new motor
    climbMotor.restoreFactoryDefaults();
  }

  /**
   * sets the speed and does nothing else
   * @author Joe Sedutto
   * @param speed
   */
  public void GantryManualControl(double speed){
    gantryMotor.set(speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
