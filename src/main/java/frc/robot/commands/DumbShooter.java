/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;

public class DumbShooter extends CommandBase {
  private final Shooter shooter;
  private final Turret turret;

  /**
   * Creates a new TestShooter.
   */
  public DumbShooter(Shooter _shooter, Turret _turret) {
    addRequirements(_shooter);

    shooter = _shooter;
    turret = _turret;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double limelight_heading = shooter.getLimelightAzimuth();
    double turret_heading = Turret.getTurretHeading();

    shooter.switchPipelines(1);
    turret.targetHeading(turret_heading + limelight_heading, true);
  }

  @Override
  public void end(boolean interrupted) {
    shooter.switchPipelines(0);
  }
}
