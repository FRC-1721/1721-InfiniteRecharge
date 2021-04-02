/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

import java.util.function.DoubleSupplier;

public class ManualClimb extends CommandBase {
  private final Climber climber;
  private final DoubleSupplier gantryspeed;
  private final DoubleSupplier liftspeed;

  /**
   * Creates a new TestShooter.
   */
  public ManualClimb(
      Climber _climber, 
      DoubleSupplier _gantry_speed, 
      DoubleSupplier _lift_speed) {
    addRequirements(_climber);

    climber = _climber;
    gantryspeed = _gantry_speed;
    liftspeed = _lift_speed;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    climber.gantryManualControl(gantryspeed.getAsDouble());
    climber.manualControl(liftspeed.getAsDouble());

    SmartDashboard.putNumber("Lift Manual Control", liftspeed.getAsDouble());
    SmartDashboard.putNumber("Gantry Manual Control", gantryspeed.getAsDouble());
  }
}
