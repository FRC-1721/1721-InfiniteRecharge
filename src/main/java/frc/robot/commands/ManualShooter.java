/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import java.util.function.DoubleSupplier;

public class ManualShooter extends CommandBase {
  private final Shooter shooter;
  private final DoubleSupplier testSpeed;
  private final DoubleSupplier turretSpeed;
  private final Joystick operatorJoystick;

  /**
   * Creates a new TestShooter.
   */
  public ManualShooter(
      Shooter _shooter, 
      Joystick _operatorJoystick, 
      DoubleSupplier _testSpeed, 
      DoubleSupplier _turretSpeed) {
    addRequirements(_shooter);

    shooter = _shooter;
    testSpeed = _testSpeed;
    turretSpeed = _turretSpeed;
    operatorJoystick = _operatorJoystick;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    shooter.testShooter(testSpeed.getAsDouble());
    shooter.testTurret(turretSpeed.getAsDouble());

    if (shooter.isAtTopLimit()) {
      operatorJoystick.setRumble(RumbleType.kLeftRumble, 1);
    } else if (shooter.isAtBottomLimit()) {
      operatorJoystick.setRumble(RumbleType.kRightRumble, 1);
    } else {
      operatorJoystick.setRumble(RumbleType.kLeftRumble, 0);
      operatorJoystick.setRumble(RumbleType.kRightRumble, 0);
    }
  }
}
