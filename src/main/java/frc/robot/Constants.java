/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

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
        // TalonSRX
        public static final int TalonSRX_Port_Address = 0;              // Configured 1/25/2020
        public static final int TalonSRX_Starboard_Address = 1;         // Configured 1/25/2020
        public static final int TalonSRX_Turret_Address = 2;            // Configured 2/8/2020
        public static final int TalonSRX_Solver_Address = 3;            // Configured never

        // VictorSPX
        public static final int VictorSPX_Port_Slave_Address0 = 0;      // Configured 1/25/2020
        public static final int VictorSPX_Port_Slave_Address1 = 1;      // Configured 1/25/2020
        public static final int VictorSPX_Starboard_Slave_Address0 = 2; // Configured 1/25/2020
        public static final int VictorSPX_Starboard_Slave_Address1 = 3; // Configured 1/25/2020
        
        // Three-phase/other
        public static final int Lift_Motor_Address = 1;                 // Congigured 2/9/2020
        public static final int Gantry_Motor_Address = 2;               // Configured never
        public static final int TalonFX_Shooter_Address = 3;            // Configured never
        public static final int IntakeMotor_Address = 3;                // Configured 3/1/2020
		public static final int MiniNeo_Shooter_Loader_Address = 4;

        // Solenoids
        public static final int Starboard_Solenoid_Address = 3;         // Configured never
        public static final int Port_Solenoid_Address = 0;              // Configured never
        public static final int Ball_Release_Solenoid_Address = 7;      // Configured never
        public static final int Lift_Release_Solenoid_Address = 4;
    }

    /**
     * Change button and joystick mappings in here for Driver Stick
     */
    public static final class DriverInputSettings{
        public static final int Driver_Stick_Port = 0;          // The USB order of the stick
        public static final int Drivebase_Thro_Axis = 1;        // Configured never
        public static final int Drivebase_Yaw_Axis = 2;         // Configured never
        public static final int Autonomous_Restart_Button = 1;  // Configured never
        public static final double Overide_Threshold = 0.2;     // The value the driver must overcome to manually disable the auto
    }

    /**
     * Change button and stick mappings for Operator Controller
     */
    public static final class OperatorInputSettings{
        public static final int Operator_Controller_Port = 1;   // The USB order of the controller

        public static final int Turret_Spin_cw_axis = 3;        // Configured never
        public static final int Turret_Spin_ccw_axis = 2;       // Configured never

        public static final int Intake_Button = 1;              // Configured never
        public static final int Purge_Button = 2;               // Configured never
        public static final int Arm_Shooter_Button = 3;         // Configured 2/14/2020
        public static final int Disarm_Shooter_Button = 4;      // Configured 2/14/2020
        public static final int Manual_Turret_Button = 5;       // Configured 2/28/2020
        public static final int Automatic_Turret_Button = 6;    // Congigured 2/28/2020
        public static final int Fire_When_Ready_Button = 7;

        public static final int Climb_Axis = 1;                 // Configured 2/15/2020
        public static final int Gantry_Axis = 0;                // Congigured 2/15/2020

        public static final double LiftDeadzone = 0.1;
    }

    /**
     * Change button mappings for the DS Toggle Switchboard
     */
    public static final class DSTogglePanelSettings{
        public static final int DS_Toggle_Panel_Port = 2;       // The USB order of the controller
        public static final int SolveStageTwo = 1;              // A button to solve stage 2
    }

    public static final class RobotOperatingSystem{
        public static double rosUpdateFrequency = 0.02;

        public static final class Names{
            public static String ManualTurret = "manual_turret";
			public static String rosTablename = "ROS";
            public static String starboardEncoderName = "Starboard";
            public static String portEncoderName = "Port";
            public static String rosIndexName = "rosIndex";
            public static String turretEncoderName = "Turret";
            public static String robotModeEntryName = "RobotMode";

            public static String ROSShooterTable = "enable_shooter";
            public static String DrivetrainTable = "ros_drivetrain";
            public static String ResetEncoders = "reset_encoders";
            public static String ShiftUp = "shift_up";
            public static String ShiftDown = "shift_down";
            public static String ZeroTurret = "zero_turret";
            public static String gameMessageEntryName = "game_message";
            public static String driverStationEntryName = "starting_station";
            public static String eventNameAndMatch = "event_name_and_match";
            public static String distanceAlongInitLine = "distance_along_init_line";
        }
        
        public static final class Modes{
            public static String NoMode = "NoMode";
            public static String Teleop = "Teleop";
            public static String Autonomous = "Autonomous";
            public static String Disabled = "Disabled";
            public static String Test = "Test";
        }
    }

    public static final class DrivetrainPID{

        public static int kPIDLoopIdx = 0; // The loop Index
        public static int kTimeoutMs = 30; // The timeout to wait when writing variables to the motors
        public static boolean portSensorPhase = true; // The phase of the sensor
        public static boolean portMotorInvert = true; // The inversion of the motor
        public static NeutralMode drivetrainBrakeMode = NeutralMode.Coast; // The brake mode of the motor
        public static boolean starboardSensorPhase = false;
        public static boolean starboardMotorInvert = false;
        public static final Gains kGains = new Gains(0.15,  // kP TODO
                                                     0.0,   // kI
                                                     1.0,   // kD
                                                     0.0,   // kF
                                                     0,     // kIzone
                                                     1.0);  // kPeakoutput
        // Measurements and other
        public static int ticksPerMeter = 10000;        // Calibrated 12/16/2019
    }

    public static final class ShooterPID{

        public static int kPIDLoopIdx = 0; // The loop Index
        public static int kTimeoutMs = 30; // The timeout to wait when writing variables to the motors
        public static boolean shooterSensorPhase = false; // The phase of the sensor
        public static boolean shooterMotorInvert = false; // The inversion of the motor
        public static boolean spinnerSensorPhase = false;
        public static boolean spinnerMotorInvert = false;
        public static NeutralMode shooterBrakeMode = NeutralMode.Brake; // Brake mode
        public static NeutralMode turretBrakeMode = NeutralMode.Brake;
        public static final Gains kGains = new Gains(0.5,  // kP TODO
                                                     0.0,   // kI
                                                     20.0,   // kD
                                                     0.0,   // kF
                                                     0,     // kIzone
                                                     1.0);  // kPeakoutput
        public static final double AcceptableVelocityError = 100;
        
        // Measurements and other
        public static double maximumSpeed = 20000.0; // Ticks/100ms
        public static double radiansToForward = 0.785398; // Radians to reset to zero heading
    }

    public static final class TurretPID{

        public static int kPIDLoopIdx = 0; // The loop Index
        public static int kTimeoutMs = 30; // The timeout to wait when writing variables to the motors
        public static boolean turretSensorPhase = true; // The phase of the sensor
        public static boolean turretMotorInvert = false; // The inversion of the motor
        public static NeutralMode turretBreakMode = NeutralMode.Brake; // Brake mode
        public static final Gains kGains = new Gains(0.35,  // kP Configured 2/18/2020 (No shooter or load)
                                                     0.0,   // kI
                                                     30.0,  // kD
                                                     0.0,   // kF
                                                     0,     // kIzone
                                                     1.0);  // kPeakoutput
        // Measurements and other
        public static double ticksPerRadian = 9550;        // Calibrated 2/19/2020 (test)
        public static double legalZeroOffset = 100;
        public static double turretDefaultLocation = -6500;    // Calibrated never
    }

    public static final class ClimberPID{

        public static int kPIDLoopIdx = 0; // The loop Index
        public static int kTimeoutMs = 30; // The timeout to wait when writing variables to the motors
        public static boolean rollerSensorPhase = false; // The phase of the sensor
        public static boolean rollerMotorInvert = false; // The inversion of the motor
        public static boolean climbMotorInvert = false;
        public static final Gains kGains = new Gains(0.15,  // kP TODO
                                                     0.0,   // kI
                                                     1.0,   // kD
                                                     0.0,   // kF
                                                     0,     // kIzone
                                                     1.0);  // kPeakoutput
        public static int arbAnalogPerRadian = 10;
    }

    public static final class SolverPID{

        public static int kPIDLoopIdx = 0; // The loop Index
        public static int kTimeoutMs = 30; // The timeout to wait when writing variables to the motors
        public static boolean sensorPhase = true; // The phase of the sensor
        public static boolean motorInvert = false;
        public static final Gains kGains = new Gains(0.15,  // kP TODO
                                                     0.0,   // kI
                                                     1.0,   // kD
                                                     0.0,   // kF
                                                     0,     // kIzone
                                                     1.0);  // kPeakoutput
        // Measurements and other
        public static int TicksPerRotation = 10000;        // Calibrated 12/16/2019
    }

    public static final class Colors{
        public static final class Blue{
            public static double red = 0.0;
            public static double green = 0.0;
            public static double blue = 0.0;
        }

        public static final class Yellow{
            public static double red = 0.0;
            public static double green = 0.0;
            public static double blue = 0.0;
        }

        public static final class Red{
            public static double red = 0.0;
            public static double green = 0.0;
            public static double blue = 0.0;
        }

        public static final class Green{
            public static double red = 0.0;
            public static double green = 0.0;
            public static double blue = 0.0;
        }
    }

    public static final class Misc{

        public static final double Downshift_Max_Speed = 0; // The maximum speed that you can downshift at.
        public static final double Upshift_Min_Speed = 0;
    }
}
