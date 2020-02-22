/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

public class GoToRandom extends CommandBase {
  public final Shooter shooter;

  /**
   * Creates a new GoToRandom.
   */
  public GoToRandom(Shooter _shooter) {
    addRequirements(_shooter);

    shooter = _shooter;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    shooter.targetHeading(-200);
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    shooter.targetHeading(-60000);
    try {
      Thread.sleep(900);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    shooter.targetHeading(-30000);
    try {
      Thread.sleep(1100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
