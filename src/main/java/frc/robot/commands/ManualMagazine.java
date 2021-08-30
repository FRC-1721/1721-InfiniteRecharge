// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Magazine;

import java.util.function.DoubleSupplier;

public class ManualMagazine extends CommandBase {
  private final Magazine magazine;
  private final DoubleSupplier feed;

  /** Creates a new ManualMagazine. */
  @SuppressWarnings("checkstyle:ParameterName")
  public ManualMagazine(
      Magazine _magazine,
      DoubleSupplier _feed) {
    addRequirements(_magazine);

    magazine = _magazine;
    feed = _feed;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    magazine.testagitator(/*0.25 + */(feed.getAsDouble() / 3));
    magazine.testDischarge(feed.getAsDouble());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
