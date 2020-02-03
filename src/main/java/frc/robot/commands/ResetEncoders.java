/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class ResetEncoders extends CommandBase {
  /**
   * Resets the encoders and finishes.
   * @author Joe Sedutto
   */
  public ResetEncoders(Drivetrain _drivetrain) {
    addRequirements(_drivetrain);
    //addRequirements(_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Drivetrain.resetEncoders(0); // Reset the encoders to 0
    System.out.println("I ran!");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    if(interrupted){
      System.out.println("Reset encoders was inturrupted!");
    }else{
      System.out.println("Reset encoders normally.");
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
