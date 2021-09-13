/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.functions;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

import java.util.function.DoubleSupplier;

public class PurgeIntake extends CommandBase {
  private final Intake intake;
  private final DoubleSupplier purgeSpeed;
  
  /**
   * Creates a new PurgeIntake.
   * @author Joe Sedutto & Khan Simeoni
   */
  @SuppressWarnings("checkstyle:ParameterName")
  public PurgeIntake(Intake _intake, DoubleSupplier _purgeSpeed) {
    addRequirements(_intake);
    intake = _intake;
    purgeSpeed = _purgeSpeed;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    intake.purgeIntake(-purgeSpeed.getAsDouble()); // Drive the intake the specified speed
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.purgeIntake(0.00); // Stop when done
  }
}
