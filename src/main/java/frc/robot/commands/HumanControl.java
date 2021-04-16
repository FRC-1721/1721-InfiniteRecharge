package frc.robot.commands;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
   * @param _thro Throttle supplier
   * @param _steerage Steerage supplier
   * @param _drivingMode Driving mode supplier
   * @param _drivetrain Drivetrain object
   */
  @SuppressWarnings("checkstyle:ParameterName")
  public HumanControl(
      DoubleSupplier _thro, 
      DoubleSupplier _steerage, 
      Supplier<HandlingMode> _drivingMode, 
      Drivetrain _drivetrain) {
    addRequirements(_drivetrain);
    
    drivetrain = _drivetrain;
    thro = _thro;
    steerage = _steerage;
    handlingMode = _drivingMode;
  }

  @Override
  public void execute() {
    switch (handlingMode.get()) { // Ask the handlingMode supplier what its value is
      case kStandard: // In the case of standard mode..
        //System.out.println("Running in mode Standard");
        drivetrain.flyByWireB(steerage.getAsDouble(), thro.getAsDouble());
        break;
      case kDiffLock: // In the case of diff mode..
        //System.out.println("Running in mode Diff Lock"); 
        break;
      case kLaneAssist: // In the case of lane asisst mode..
        //System.out.println("Running in mode LaneAssist");
        break;
      case kFlyByWire: // In the case of flybywire mode..
        //System.out.println("Running in mode FlyByWire");
        drivetrain.flyByWireA(steerage.getAsDouble(), thro.getAsDouble());
        break;
      default:
        DriverStation.reportError("Running in default mode! No drive mode selected!", false);
        drivetrain.flyByWireA(steerage.getAsDouble(), thro.getAsDouble());
    }
    SmartDashboard.putNumber("Thro", thro.getAsDouble());
  }
}
