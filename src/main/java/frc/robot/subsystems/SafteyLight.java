/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SafteyLight extends SubsystemBase {
  private static final TalonSRX safetylight = new TalonSRX(0); // Init the saftey light at address 0
  
  /**
   * Creates a new SafteyLight.
   */
  public SafteyLight() {
  }

  /**
   * Set to true to spin the saftey light
   * @param state
   * @author Zak
   */
  public static void spin(boolean state){
    if (state == true) { // If the thro variable is under 0
      safetylight.set(ControlMode.PercentOutput, 1); // Spin the light!
    } else { // If the thro varaiable is over 0
      safetylight.set(ControlMode.PercentOutput, 0); // Stop that stuff!
    }
  }

  @Override
  public void periodic() {
  }
}
