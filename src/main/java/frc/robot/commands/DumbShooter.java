/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class DumbShooter extends CommandBase {
  private final Shooter shooter;

  /**
   * Creates a new TestShooter.
   */
  public DumbShooter(Shooter _shooter) {
    addRequirements(_shooter);

    shooter = _shooter;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double limelight_heading = shooter.getLimelightHeading();
    double turret_heading = Shooter.getTurretHeading();

    shooter.switchPipelines(1);
    shooter.targetHeading(turret_heading + limelight_heading, true);
  }

  @Override
  public void end(boolean interrupted) {
    shooter.switchPipelines(0);
  }
}
