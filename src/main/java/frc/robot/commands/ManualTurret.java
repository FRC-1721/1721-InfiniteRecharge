/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Turret;

public class ManualTurret extends CommandBase {
  private final Turret turret;

  private final DoubleSupplier turret_velocity;
  private final Joystick operatorJoystick;

  /**
   * Creates a new ManualTurret.
   */
  public ManualTurret(Turret _turret, Joystick _operatorJoystick, DoubleSupplier _turret_velocity) {
    addRequirements(_turret);

    turret_velocity = _turret_velocity;
    operatorJoystick = _operatorJoystick;
    turret = _turret;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    turret.manualTurret(turret_velocity.getAsDouble() * 4000); 
    
    if (turret.isAtTopLimit()){ // If at the top limit
      operatorJoystick.setRumble(RumbleType.kLeftRumble, 1); // Rumble the left side
    }
    else if(turret.isAtBottomLimit()){ // If at the bottom
      operatorJoystick.setRumble(RumbleType.kRightRumble, 1); // Rumble the right side
    }
    else{ // If neither
      operatorJoystick.setRumble(RumbleType.kLeftRumble, 0); // Dont rumble
      operatorJoystick.setRumble(RumbleType.kRightRumble, 0);
    }
  }
}
