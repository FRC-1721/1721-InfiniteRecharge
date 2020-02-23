/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.Constants;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ControlPanel extends SubsystemBase {
  public static final TalonSRX solver = new TalonSRX(Constants.CANIds.TalonSRX_ControlPanel_Address);

  /**
   * Creates a new ControlPanel.
   */
  public ControlPanel() {
  
  }
  
  /**
   * @author Khan 
   * @param speed (from 0 to 1)
   */
  public void manuelSolver(double speed) {
    solver.set(ControlMode.PercentOutput, speed);
  }
  
  
  @Override
  public void periodic() {
    
  }
}
