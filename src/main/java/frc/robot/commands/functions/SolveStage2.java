/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.functions;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Solver;

public class SolveStage2 extends InstantCommand {
  private static Solver solver;

  /**
   * Creates a new SolveStage2.
   */
  public SolveStage2(Solver _solver) {
    addRequirements(_solver);

    solver = _solver;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    solver.setPositionNoColor(3);
  }
}
