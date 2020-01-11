package frc.robot.commands;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.HandlingMode;
import frc.robot.subsystems.Drivetrain;

public class HumanControl extends CommandBase {
    
  private final Drivetrain subsystem;
  private final DoubleSupplier thro;
  private final DoubleSupplier steerage;
  private final Supplier<HandlingMode> handlingMode;

  public HumanControl(DoubleSupplier _thro, DoubleSupplier _steerage, Supplier<HandlingMode> _drivingMode, Drivetrain _drivetrain) {
    addRequirements(_drivetrain);
    subsystem = _drivetrain;
    thro = _thro;
    steerage = _steerage;
    handlingMode = _drivingMode;
  }

  @Override
  public void execute() {
    switch(handlingMode.get()){
      case kStandard:
        subsystem.FlyByWireA(thro.getAsDouble(), steerage.getAsDouble());
      case kDiffLock:
      case kLaneAssist:
      case kFlyByWire:
    }
  }
}