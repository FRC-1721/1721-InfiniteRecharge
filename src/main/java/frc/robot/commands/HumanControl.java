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
    switch(handlingMode.get()){ // Ask the handlingMode supplier what its value is
      case kStandard: // In the case of standard mode..
        //System.out.println("Running in mode Standard");
        drivetrain.FlyByWireA(steerage.getAsDouble(), thro.getAsDouble());
        break;
      case kDiffLock: // In the case of diff mode..
        //System.out.println("Running in mode Diff Lock"); 
        break;
      case kLaneAssist: // In the case of lane asisst mode..
        //System.out.println("Running in mode LaneAssist");
        break;
      case kFlyByWire: // In the case of flybywire mode..
        //System.out.println("Running in mode FlyByWire");
        drivetrain.FlyByWireA(steerage.getAsDouble(), thro.getAsDouble());
        break;
      default:
      System.out.println("RUNNING IN DEFAULT! NO DRIVING MODE SELECTED!");
    }
  }
}
