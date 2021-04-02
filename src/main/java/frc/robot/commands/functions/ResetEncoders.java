/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.functions;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class ResetEncoders extends CommandBase {
  /**
   * Resets the encoders and finishes.
   * @author Joe Sedutto
   */
  public ResetEncoders(Drivetrain _drivetrain) {
    addRequirements(_drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Drivetrain.resetEncoders(0); // Reset the encoders to 0
    System.out.println("Reset Encoders");
    end(isFinished());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    if (interrupted) {
      System.out.println("The command to reset the encoders was intturupted!");
    } else {
      System.out.println("Encoders were reset normally.");
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (Drivetrain.getDriveEncoderPort() == 0 && Drivetrain.getDriveEncoderStarboard() == 0) {
      return true;
    } else {
      return false;
    }
  } //TODO: Clean up the code
}
