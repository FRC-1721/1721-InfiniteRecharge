/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.            */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                 */
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
@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:LineLength"})
public final class Constants {

  /**
   * Use only for can IDs.
   */
  public static final class CANAddresses {
    // TalonSRX
    public static final int TalonSRX_Solver_Address = 3;            // Configured never

    // VictorSPX
    public static final int Magazine_Intake_Motor = 1;    // Configured never
    public static final int Magazine_Dischage_motor = 2;  // Configured never
    
    // Three-phase/other
    public static final int SparkMax_Port_Drive_Address = 2;             // Configured 4/16/2021
    public static final int SparkMax_Starboard_Drive_Address = 4;        // Configured 4/16/2021
    public static final int SparkMax_Port_Drive_Slave_Address = 1;       // Configured 4/16/2021
    public static final int SparkMax_Starboard_Drive_Slave_Address = 3;  // Configured 4/16/2021
    public static final int Neo_Lift_Motor_Address = 4;                  // Congigured never
    public static final int MiniNeo_Gantry_Motor_Address = 5;            // Configured never
    public static final int TalonFX_Shooter_Address = 1;                 // Configured never
    public static final int MiniNeo_Outrunner_IntakeMotor_Address = 8;   // Configured never
    public static final int Turret_Motor_Address = 6;                    // Configured 4/16/21
    public static final int Magazine_Agitate = 5;                        // Configured never
  }

  /**
   * Use for pneumatic constants.
   */
  public static final class Pneumatics {
    // Intake solenoids
    public static final int Intake_Solenoid_Forward = 5;          //(Deploy)  Configured never
    public static final int Intake_Solenoid_Reverse = 6;         //(Retract) Configured never

    // Shooter solenoids
    public static final int Hood_Solenoid_Forward = 4;          //(Up)  Configured never
    public static final int Hood_Solenoid_Reverse = 2;         //(Down) Configured never

    // Drivetrain solenoids
    public static final int Shift_Up_Solenoid = 1; // Configured never
    public static final int Shift_Down_Solenoid = 7; // Configured never
  }

  /**
   * Change button and joystick mappings in here for Driver Stick.
   */
  public static final class DriverInputSettings {
    public static final int Driver_Stick_Port = 0;      // The USB order of the stick
    public static final int Drivebase_Thro_Axis = 1;    // Configured never
    public static final int Drivebase_Yaw_Axis = 2;     // Configured never
    public static final int RestartAutonomous = 1;      // Configured never
    // The value the driver must overcome to manually disable the auto
    public static final double Overide_Threshold = 0.2; 
  }

  /**
   * Change button and stick mappings for Operator Controller.
   */
  public static final class OperatorInputSettings {
    public static final int Operator_Controller_Port = 1;   // The USB order of the controller

    public static final int Turret_Spin_axis = 4;    // Configured 4/16/2021
    public static final int Arm_Shooter_Button = 3;     // Configured 2/14/2020
    public static final int Disarm_Shooter_Button = 4;    // Configured 2/14/2020
    public static final int MagazineFeedAxis = 1;         // Configured 4/16/2021

    public static final int Climb_Axis = 1;         // Configured 2/15/2020
    public static final int Gantry_Axis = 0;        // Congigured 2/15/2020

    public static final int Intake_Button = 1;               // Configured never
    public static final int Purge_Button = 2;                // Configured never
    public static final int Intake_Deploy = 6; // Configured never
  }

  /**
   * Change button mappings for the DS Toggle Switchboard.
   */
  public static final class DSTogglePanelSettings {
    public static final int DS_Toggle_Panel_Port = 2;     // The USB order of the controller
    public static final int SolveStageTwo = 1;        // A button to solve stage 2
  }

  public static final class RobotOperatingSystem {
    public static double rosUpdateFrequency = 0.02;
    public static String rosTablename = "ROS";
    public static String starboardEncoderName = "Starboard";
    public static String portEncoderName = "Port";
    public static String rosIndexName = "rosIndex";
  }

  public static final class DrivetrainPID {

    public static int kPIDLoopIdx = 0; // The loop Index
    public static int kTimeoutMs = 30; // The timeout to wait when writing variables to the motors
    public static boolean portSensorPhase = false; // The phase of the sensor
    public static boolean portMotorInvert = false; // The inversion of the motor
    public static boolean starboardSensorPhase = false;
    public static boolean starboardMotorInvert = false;
    public static final Gains kGains = new Gains(0.15,  // kP TODO
                           0.0,   // kI
                           1.0,   // kD
                           0.0,   // kF
                           0,   // kIzone
                           1.0);  // kPeakoutput
    // Measurements and other
    public static int rotationsPerMeter = 10000;    // Calibrated Never
  }

  public static final class ShooterPID {

    public static int kPIDLoopIdx = 0; // The loop Index
    public static int kTimeoutMs = 30; // The timeout to wait when writing variables to the motors
    public static boolean shooterSensorPhase = false; // The phase of the sensor
    public static boolean shooterMotorInvert = false; // The inversion of the motor
    public static boolean spinnerSensorPhase = false;
    public static boolean spinnerMotorInvert = false;
    public static NeutralMode shooterBreakMode = NeutralMode.Brake; // Brake mode
    public static final Gains kGains = new Gains(0.15,  // kP TODO
                           0.0,   // kI
                           1.0,   // kD
                           0.0,   // kF
                           0,   // kIzone
                           1.0);  // kPeakoutput
  }

  public static final class TurretPID {

    public static int kPIDLoopIdx = 0; // The loop Index
    public static int kTimeoutMs = 30; // The timeout to wait when writing variables to the motors
    public static boolean turretSensorPhase = false; // The phase of the sensor
    public static boolean turretMotorInvert = false; // The inversion of the motor
    public static NeutralMode turretBreakMode = NeutralMode.Brake; // Brake mode
    public static final Gains kGains = new Gains(0.15,  // kP TODO
                           0.0,   // kI
                           1.0,   // kD
                           0.0,   // kF
                           0,   // kIzone
                           1.0);  // kPeakoutput
    // Measurements and other
    public static int ticksPerRadian = 10000;    // Calibrated Never
  }

  public static final class ClimberPID {

    public static int kPIDLoopIdx = 0; // The loop Index
    public static int kTimeoutMs = 30; // The timeout to wait when writing variables to the motors
    public static boolean rollerSensorPhase = false; // The phase of the sensor
    public static boolean rollerMotorInvert = false; // The inversion of the motor
    public static boolean climbMotorInvert = false;
    public static final Gains kGains = new Gains(0.15,  // kP TODO
                           0.0,   // kI
                           1.0,   // kD
                           0.0,   // kF
                           0,   // kIzone
                           1.0);  // kPeakoutput
    public static int arbAnalogPerRadian = 10;
  }

  public static final class SolverPID {

    public static int kPIDLoopIdx = 0; // The loop Index
    public static int kTimeoutMs = 30; // The timeout to wait when writing variables to the motors
    public static boolean sensorPhase = true; // The phase of the sensor
    public static boolean motorInvert = false;
    public static final Gains kGains = new Gains(0.15,  // kP TODO
                           0.0,   // kI
                           1.0,   // kD
                           0.0,   // kF
                           0,   // kIzone
                           1.0);  // kPeakoutput
    // Measurements and other
    public static int TicksPerRotation = 10000;    // Calibrated 12/16/2019
  }

  public static final class Colors {
    public static final class Blue {
      public static double red = 0.0;
      public static double green = 0.0;
      public static double blue = 0.0;
    }

    public static final class Yellow {
      public static double red = 0.0;
      public static double green = 0.0;
      public static double blue = 0.0;
    }

    public static final class Red {
      public static double red = 0.0;
      public static double green = 0.0;
      public static double blue = 0.0;
    }

    public static final class Green {
      public static double red = 0.0;
      public static double green = 0.0;
      public static double blue = 0.0;
    }
  }

  public static final class Misc {
    // The maximum speed that you can downshift at.
    public static final double Downshift_Max_Speed = 0; 
    public static final double Upshift_Min_Speed = 0;
  }
}
