/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.functions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

public class ZeroTurret extends CommandBase {
  private final Shooter shooter; // Include shooter
  private boolean done;
  private boolean limit_hit;
  private boolean idle;

  public ZeroTurret(Shooter _shooter) {
    addRequirements(_shooter); // Requires shooter

    shooter = _shooter;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    done = false;
    limit_hit = false;
  }

  @Override
  public void execute() {
    if(!limit_hit){
      if(!shooter.isAtTopLimit()){
        shooter.manualTurret(4750);
      }
      else{
        shooter.manualTurret(0);
        limit_hit = true;
      }
    }
    else if(!done){
      shooter.targetHeading(Constants.TurretPID.turretDefaultLocation, false);
    }
    idle = (Constants.TurretPID.turretDefaultLocation < shooter.getTurretHeadingRaw() + Constants.TurretPID.legalZeroOffset && Constants.TurretPID.turretDefaultLocation > shooter.getTurretHeadingRaw() - Constants.TurretPID.legalZeroOffset );
    SmartDashboard.putBoolean("idle", idle);
  }

  @Override
  public boolean isFinished() {
    return (idle);
  }
}
