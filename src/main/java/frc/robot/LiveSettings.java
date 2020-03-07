package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class LiveSettings {
  public static final Joystick DSModePanel = new Joystick(Constants.DSTogglePanelSettings.DS_Toggle_Panel_Port);

    /**
     * Constructs LiveSettings.
     * @author Joe S
     */
    public LiveSettings() {

    }

    /**
     * An enum allowing you to switch 
     * turret modes during a match
     * @author Joe S
     */
    public static enum turretMode {
        normal,
        disengaged,
        auxiliary;

        turretMode() { }

        public static turretMode getValue() {
            if (DSModePanel.getRawButton(1)){ return disengaged; }
            else if (DSModePanel.getRawButton(2)){ return auxiliary; }
            else { return normal; }
        }
    }

    /**
     * An enum allowing you to switch
     * elevator modes during a match
     * @author Joe S
     */
    public static enum elevatorMode {
        normal,
        disengaged,
        auxiliary;

        elevatorMode() { }

        public static elevatorMode getValue() {
            if (DSModePanel.getRawButton(3)){ return disengaged; }
            else if (DSModePanel.getRawButton(4)){ return auxiliary; }
            else { return normal; }
        }
    }

    /**
     * An enum allowing you to switch
     * elevator modes during a match
     * @author Joe S
     */
    public static enum intakeMode {
        normal,
        disengaged,
        auxiliary;

        intakeMode() { }

        public static intakeMode getValue() {
            if (DSModePanel.getRawButton(5)){ return disengaged; }
            else if (DSModePanel.getRawButton(6)){ return auxiliary; }
            else { return normal; }
        }
    }
}