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
  
  /**
   * Creates a new PurgeIntake.
   * @author Joe Sedutto
   * @param _intake An intake susbsystem
   * @param _intakeSpeed The speed at witch to spin the intake
   */
  @SuppressWarnings("checkstyle:ParameterName")
  public SpinIntake(Intake _intake, DoubleSupplier _intakeSpeed) {
    addRequirements(_intake);
    intake = _intake;
    intakeSpeed = _intakeSpeed;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    intake.driveIntake(intakeSpeed.getAsDouble()); // Drive the intake at the specified speed
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.driveIntake(0); // Stop when done
  }
}
