import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.HandlingMode;
import frc.robot.subsystems.Drivetrain;

public class DriveCommand extends CommandBase {
    
  private final Drivetrain subsystem;
  private final DoubleSupplier leftStick;
  private final DoubleSupplier rightStick;
  private final Supplier<HandlingMode> driveMode;

  public DriveCommand(DoubleSupplier _leftStick, DoubleSupplier _rightStick, Supplier<HandlingMode> _drivingMode, Drivetrain drivetrain) {
      addRequirements(drivetrain);
      subsystem = drivetrain;
      leftStick = _leftStick;
      rightStick = _rightStick;
      HandlingMode = _drivingMode;
  }

  @Override
  public void execute() {
      switch (HandlingMode) {
          case kStandard:
            // do the thing for standard here
            break;
          case kNextThingy:
            // do the thing for nextthingy here
            break;
        }
    }
}