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

  public ZeroTurret(Shooter _shooter) {
    addRequirements(_shooter); // Requires shooter

    shooter = _shooter;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    while(!shooter.isAtTopLimit()){
      shooter.testTurret(0.4);
    }
    shooter.targetHeading(Constants.TurretPID.turretDefaultLocation);
  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    //boolean idle = (shooter.getTurretHeadingRaw() > Constants.TurretPID.turretDefaultLocation - Constants.TurretPID.legalZeroOffset && shooter.getTurretHeadingRaw() < Constants.TurretPID.turretDefaultLocation + Constants.TurretPID.legalZeroOffset);
    return (false);
  }
}
