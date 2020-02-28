/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.functions;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Turret;

public class ZeroTurret extends CommandBase {
  private final Turret turret; // Include shooter
  private boolean done;
  private boolean limit_hit;

  public ZeroTurret(Turret _turret) {
    addRequirements(_turret); // Requires shooter

    turret = _turret;
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
      if(!turret.isAtTopLimit()){
        turret.manualTurret(4000);
      }
      else{
        turret.manualTurret(0);
        limit_hit = true;
      }
    }
    else if(!done){
      turret.targetHeading(Constants.TurretPID.turretDefaultLocation, false);
    }
  }

  @Override
  public boolean isFinished() {
    return (false);
  }
}
