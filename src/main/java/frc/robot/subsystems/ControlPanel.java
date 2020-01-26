/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ControlPanel extends SubsystemBase {
  private final I2C.Port i2cPort = I2C.Port.kOnboard; 
  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort); 
  private Color detectedColor;;


  
  /**
   * Creates a new ControlPanel.
   */
  public ControlPanel() {
  }

  /**
   * return true if green
   * @author Evelynn
   * @author Cohen
   */
  public boolean IsRed(){
    detectedColor = m_colorSensor.getColor(); 
    if(detectedColor.red > 0.5)
    {
      return true ;
    }else{
      return false;
    }
  }

  public boolean IsBlue(){
    detectedColor = m_colorSensor.getColor(); 
    if(detectedColor.blue > 0.5)
    {
      return true ;
    }else{
      return false;
    }
  }

  public boolean IsGreen(){
    detectedColor = m_colorSensor.getColor(); 
    if(detectedColor.green > 0.5)
    {
      return true ;
    }else{
      return false;
    }
  }

  

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
