// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.functions;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Intake;

public class ToggleDeployIntake extends InstantCommand {
  private static Intake intake;

  /**
   * Creates a new ShiftUp.
   */
  @SuppressWarnings("checkstyle:ParameterName")
  public ToggleDeployIntake(Intake _intake) {
    // Do not require intake as they are not mutually exclusive.
    //addRequirements(_intake);
    intake = _intake;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    DoubleSolenoid.Value currentState = intake.getDeployState();
    if (currentState != DoubleSolenoid.Value.kForward) {
      intake.setIntakePosition(DoubleSolenoid.Value.kForward);
    } else {
      intake.setIntakePosition(DoubleSolenoid.Value.kReverse);
    }
    end(false);
  }
}
