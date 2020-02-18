/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ManualShooter extends CommandBase {
  private final Shooter shooter;
  private final DoubleSupplier shooter_velocity;
  private final DoubleSupplier turret_velocity;
  private final Joystick operatorJoystick;

  /**
   * Creates a new TestShooter.
   */
  public ManualShooter(Shooter _shooter, Joystick _operatorJoystick, DoubleSupplier _test_speed, DoubleSupplier _turret_velocity) {
    addRequirements(_shooter);

    shooter = _shooter;
    shooter_velocity = _test_speed;
    turret_velocity = _turret_velocity;
    operatorJoystick = _operatorJoystick;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    shooter.testShooter(shooter_velocity.getAsDouble());
    shooter.testTurret(turret_velocity.getAsDouble() / 2); // 100 is just a multiplier to scale the drum speed
    
    if (shooter.isAtTopLimit()){ // If at the top limit
      operatorJoystick.setRumble(RumbleType.kLeftRumble, 1); // Rumble the left side
    }
    else if(shooter.isAtBottomLimit()){ // If at the bottom
      operatorJoystick.setRumble(RumbleType.kRightRumble, 1); // Rumble the right side
    }
    else{ // If neither
      operatorJoystick.setRumble(RumbleType.kLeftRumble, 0); // Dont rumble
      operatorJoystick.setRumble(RumbleType.kRightRumble, 0);
    }
  }
}
