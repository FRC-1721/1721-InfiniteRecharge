/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.functions;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

public class ZeroTurret extends CommandBase {
  private final Shooter shooter; // Include shooter
  private Boolean zeroed;

  public ZeroTurret(Shooter _shooter) {
    addRequirements(_shooter); // Requires shooter

    shooter = _shooter;
    zeroed = false;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    while (!zeroed) {                    // If we're not zeroed yet..
      shooter.testTurret(0.2);  // Drive the shooter backwards at -200
      if (shooter.isAtTopLimit()){ // When we are zeroed
        zeroed = true;                // Stop loop
        break;
      }
    }

    shooter.targetHeading(Constants.TurretPID.turretDefaultLocation);
  }

  @Override
  public boolean isFinished() {
    return zeroed;
  }
}
