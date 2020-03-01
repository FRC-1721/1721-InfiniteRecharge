/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.functions;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class PurgeIntake extends CommandBase {
  private final Intake intake;
  /**
   * Creates a new PurgeIntake.
   * @author Joe Sedutto
   */
  public PurgeIntake(Intake _intake) {
    addRequirements(_intake);
    intake = _intake;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    intake.purgeIntake(); // Drive the intake at full speed
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.purgeIntake(); // Stop when done
  }
}
