/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class ManualClimb extends CommandBase {
  private final Climber climber;
  private final DoubleSupplier gantry_speed;
  private final DoubleSupplier lift_speed;
  private final BooleanSupplier releaseOverride;

  /**
   * Creates a new TestShooter.
   */
  public ManualClimb(Climber _climber, DoubleSupplier _gantry_speed, DoubleSupplier _lift_speed, BooleanSupplier _releaseOverride) {
    addRequirements(_climber);

    climber = _climber;
    gantry_speed = _gantry_speed;
    lift_speed = _lift_speed;
    releaseOverride = _releaseOverride;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    climber.GantryManualControl(gantry_speed.getAsDouble());
    climber.ManualControl(lift_speed.getAsDouble(), releaseOverride.getAsBoolean(), releaseOverride.getAsBoolean());

    SmartDashboard.putNumber("Lift Manual Control", lift_speed.getAsDouble());
    SmartDashboard.putNumber("Gantry Manual Control", gantry_speed.getAsDouble());
  }
}
