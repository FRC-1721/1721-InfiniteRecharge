/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.functions;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ArmShooter extends CommandBase {
  private final Shooter shooter;
  
  /**
   * Creates a new ArmShooter.
   */
  @SuppressWarnings("checkstyle:ParameterName")
  public ArmShooter(Shooter _shooter) {
    addRequirements(_shooter);

    shooter = _shooter;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooter.switchPipelines(1);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    shooter.testShooter(1);
  }
}
