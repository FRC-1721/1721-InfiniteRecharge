package frc.robot.commands;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.HandlingMode;
import frc.robot.subsystems.Drivetrain;

public class DriveCommand extends CommandBase {
    
  private final Drivetrain subsystem;
  private final DoubleSupplier leftStick;
  private final DoubleSupplier rightStick;
  private final Supplier<HandlingMode> handlingMode;

  public DriveCommand(DoubleSupplier _leftStick, DoubleSupplier _rightStick, Supplier<HandlingMode> _drivingMode, Drivetrain _drivetrain) {
    addRequirements(_drivetrain);
    subsystem = _drivetrain;
    leftStick = _leftStick;
    rightStick = _rightStick;
    handlingMode = _drivingMode;
  }

  @Override
  public void execute() {
    subsystem.FlyByWireA(leftStick.getAsDouble(), rightStick.getAsDouble());
  }
}