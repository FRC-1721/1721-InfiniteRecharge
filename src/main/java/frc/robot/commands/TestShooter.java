/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class TestShooter extends CommandBase {
  private final Shooter shooter;
  private final DoubleSupplier test_speed;

  /**
   * Creates a new TestShooter.
   */
  public TestShooter(Shooter _shooter, DoubleSupplier _test_speed) {
    addRequirements(_shooter);

    shooter = _shooter;
    test_speed = _test_speed;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    shooter.testShooter(test_speed.getAsDouble());
  }
}
