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

public class Magazine extends SubsystemBase {
  // CANSParkMax Objects
  private static final CANSparkMax shooterLoader = new CANSparkMax(Constants.CANIds.MiniNeo_Shooter_Loader_Address, MotorType.kBrushless);

  /**
   * Creates a new Magazine.
   * A subsystem to control the 
   * loader hardware for the shooter.
   */
  public Magazine() {

  }

  /**
   * Set the shooter motor to the state passed
   * @author Joe Sedutto
   * @param state (-1 to 1)
   */
  public void safeEngageMagazine(Double state){
    if (Shooter.isReadyToFire()){ // If the shooter is ready to fire
      shooterLoader.set(state);
    }
    else{
      shooterLoader.set(0);
    }
  }

  /**
   * Forces the shooter feeder to a specified state
   * @author Joe S
   * @param state (-1 to 1)
   */
  public void forceEngageMagazine(Double state){
    shooterLoader.set(state);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
