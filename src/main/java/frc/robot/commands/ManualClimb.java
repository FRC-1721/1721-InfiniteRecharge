/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class ManualClimb extends CommandBase {
  private final Climber climber;
  private final DoubleSupplier test_speed;

  /**
   * Creates a new TestShooter.
   */
  public ManualClimb(Climber _climber, DoubleSupplier _test_speed) {
    addRequirements(_climber);

    climber = _climber;
    test_speed = _test_speed;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    climber.GantryManualControl(test_speed.getAsDouble());

  }
}
