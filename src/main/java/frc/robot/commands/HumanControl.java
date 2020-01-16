package frc.robot.commands;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.HandlingMode;
import frc.robot.subsystems.Drivetrain;

public class HumanControl extends CommandBase {
    
  private final Drivetrain drivetrain;
  private final DoubleSupplier thro;
  private final DoubleSupplier steerage;
  private final Supplier<HandlingMode> handlingMode;

  /**
   * HumanControl takes these params to drive the robot in telop mode.
   * 
   * @author Joe
   * @param _thro
   * @param _steerage
   * @param _drivingMode
   * @param _drivetrain
   */
  public HumanControl(DoubleSupplier _thro, DoubleSupplier _steerage, Supplier<HandlingMode> _drivingMode, Drivetrain _drivetrain) {
    addRequirements(_drivetrain);
    drivetrain = _drivetrain;
    thro = _thro;
    steerage = _steerage;
    handlingMode = _drivingMode;
  }

  @Override
  public void execute() {
    switch(handlingMode.get()){
      case kStandard:
        drivetrain.FlyByWireA(thro.getAsDouble(), steerage.getAsDouble());
      case kDiffLock:
      case kLaneAssist:
      case kFlyByWire:
    }
  }
}