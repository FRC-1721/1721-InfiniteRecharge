/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    /**
     * Use only for can IDs
     */
    public static final class CANIds{
        public static final int TalonSRX_Port_ID = 1; // Configured never
        public static final int TalonSRX_Starboard_ID = 2; // Configured never
    }

    /**
     * Change button and joystick mappings in here for Driver Stick
     */
    public static final class DriverInputSettings{
        public static final int Driver_Stick_Port = 0; // The USB order of the stick
        public static final int Drivebase_Thro_Axis = 1; // Configured never
        public static final int Drivebase_Yaw_Axis = 2; // Configured never
        public static final int Autonomous_Restart_Button = 1; // Configured never
        public static final double Overide_Threshold = 0.2; // The value the driver must overcome to manually disable the auto
    }

    /**
     * Change button and stick mappings for Operator Controller
     */
    public static final class OperatorInputSettings{
        public static final int Operator_Controller_Port = 0; // The USB order of the controller
        public static final int SomeNumber = 0; // Configured never
    }

    public static final class RobotOperatingSystem{
        public static double rosUpdateFrequency = 0.02;
        public static String rosTablename = "ROS";
        public static String starboardEncoderName = "Starboard";
        public static String portEncoderName = "Port";
        public static String rosIndexName = "rosIndex";
		public static int ticksPerMeter = 10000; // Calibrated 12/16/2019
    }
}
