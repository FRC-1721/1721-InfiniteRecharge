package frc.robot.commands;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.HandlingMode;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.SafteyLight;

public class DriveCommand extends CommandBase {
    
  private final Drivetrain subsystem;
  private final DoubleSupplier thro;
  private final DoubleSupplier steerage;
  private final Supplier<HandlingMode> handlingMode;

  public DriveCommand(DoubleSupplier _thro, DoubleSupplier _steerage, Supplier<HandlingMode> _drivingMode, Drivetrain _drivetrain) {
    addRequirements(_drivetrain);
    subsystem = _drivetrain;
    thro = _thro;
    steerage = _steerage;
    handlingMode = _drivingMode;
  }

  @Override
  public void execute() {
    switch(handlingMode){
      case kStandard:
        subsystem.FlyByWireA(thro.getAsDouble(), steerage.getAsDouble());
    }
  }
}