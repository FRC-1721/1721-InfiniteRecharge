/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class ShiftDown extends CommandBase {
  private final Drivetrain drivetrain;

  /**
   * Creates a new ShiftUp.
   */
  public ShiftDown(Drivetrain _drivetrain) {
    // Do not require drivetrain as they are not mutually exclusive.
    drivetrain = _drivetrain;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.ShiftGearboxesStandard(false);
  }
}
