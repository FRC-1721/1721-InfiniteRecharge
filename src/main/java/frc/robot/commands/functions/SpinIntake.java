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

public class SpinIntake extends CommandBase {
  private final Intake intake;
  private final DoubleSupplier intakeSpeed;
  private final DoubleSupplier purgeSpeed;
  
  /**
   * Creates a new PurgeIntake.
   * @author Joe Sedutto & Khan Simeoni
   * @param _intake An intake susbsystem
   * @param _intakeSpeed The speed at witch to spin the intake
   */
  @SuppressWarnings("checkstyle:ParameterName")
  public SpinIntake(Intake _intake, DoubleSupplier _intakeSpeed, DoubleSupplier _purgeSpeed) {
    addRequirements(_intake);
    intake = _intake;
    intakeSpeed = _intakeSpeed;
    purgeSpeed = _purgeSpeed;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (intakeSpeed.getAsDouble() != 0 && purgeSpeed.getAsDouble() == 0) {
      intake.driveIntake(intakeSpeed.getAsDouble()); // Drive the intake at the specified speed
    } else if (intakeSpeed.getAsDouble() == 0 && purgeSpeed.getAsDouble() != 0) {
      intake.purgeIntake(-purgeSpeed.getAsDouble()); // Purge the intake at the specified speed
    } else {
      return;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.driveIntake(0); // Stop when done
  }
}
