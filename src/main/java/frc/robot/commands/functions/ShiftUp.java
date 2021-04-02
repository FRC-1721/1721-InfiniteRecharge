/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.functions;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Drivetrain;

public class ShiftUp extends InstantCommand {
  private final Drivetrain drivetrain;

  /**
   * Creates a new ShiftUp.
   */
  public ShiftUp(Drivetrain _drivetrain) {
    // Do not require drivetrain as they are not mutually exclusive.
    drivetrain = _drivetrain;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.shiftGearboxesStandard(true);
    end(false);
  }
}
