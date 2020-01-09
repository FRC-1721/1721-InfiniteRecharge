package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DriveCommand extends CommandBase {
    
  private final Drivetrain subsystem;
  private final DoubleSupplier leftStick;
  private final DoubleSupplier rightStick;
  //private final Supplier<HandlingMode> driveMode;

  public DriveCommand(DoubleSupplier _leftStick, DoubleSupplier _rightStick, Drivetrain drivetrain) {
    addRequirements(drivetrain);
    subsystem = drivetrain;
    leftStick = _leftStick;
    rightStick = _rightStick;
  }

  @Override
  public void execute() {
    subsystem.FlyByWireA(leftStick.getAsDouble(), rightStick.getAsDouble());
  }
}