/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.ros;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableRegistry;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;

public class ROS extends SubsystemBase {
  //From SmartDashboard,class
  @SuppressWarnings("PMD.UseConcurrentHashMap")
  private static final Map<String, Sendable> tablesToData = new HashMap<>();

  // Initilize counters
  private static int rosIntex = 1;

  // Setup networkTables
  private static NetworkTableInstance networkTableInstance; // The networktable instance
  private static NetworkTable rosTable; // The table in that instance of networktables
  private static NetworkTableEntry starboardEncoderEntry; // An entry objecy
  private static NetworkTableEntry portEncoderEntry;
  private static NetworkTableEntry rosIndex;
  private static NetworkTableEntry coprocessorPort; // For tank drive
  private static NetworkTableEntry coprocessorStarboard;
  //private static NetworkTableEntry rosTime; // Is ros time (slow estimate)

  // Initialize noifiers
  private static Notifier ros_notifier;

  /**
   * Creates a new ROS.
   */
  public ROS() {
    // Network tables
    networkTableInstance = NetworkTableInstance.create(); // Get the default instance of network tables on the rio
    networkTableInstance.startServer("ros.ini", "10.17.21.2", 5800); // Start a new server on a different port\
    rosTable = networkTableInstance.getTable(Constants.RobotOperatingSystem.rosTablename); // Get the table ros out of that instance

    // Get the writable entries
    starboardEncoderEntry = rosTable.getEntry(Constants.RobotOperatingSystem.starboardEncoderName);
    portEncoderEntry = rosTable.getEntry(Constants.RobotOperatingSystem.portEncoderName);
    rosIndex = rosTable.getEntry(Constants.RobotOperatingSystem.rosIndexName);

    // Get the return entries
    coprocessorPort = rosTable.getEntry("coprocessorPort"); // Coprossesor speed values
    coprocessorStarboard = rosTable.getEntry("coprocessorStarboard");

    // Notifier (auto runs a method similar to a command but with NO PROTECTION )
    ros_notifier = new Notifier(ROS::updateTables); // Set the ros_notifer to update the command update, in the package ros
    ros_notifier.startPeriodic(Constants.RobotOperatingSystem.rosUpdateFrequency); // Start the ros notifer
  }

  /**
   * Is called by a notifier at the ros update frequency
   */
  public static void updateTables() {
    //starboardEncoderEntry.setDouble(Drivetrain.getDriveEncoderStarboard());
    //portEncoderEntry.setDouble(Drivetrain.getDriveEncoderPort());
    rosIndex.setNumber(rosIntex);

    networkTableInstance.flush(); // Force an update and flush all values out. (Recomended by https://www.chiefdelphi.com/t/integrating-ros-node-into-roborio-for-slam/358386/40)

    // Increase the Index value (Used for Syncing)
    rosIntex = rosIntex + 1;
    if (rosIntex > 255) {
      rosIntex = 1;
    }
  }

  /**
   * This method coppied from SmartDashboard.class
   *
   * @param key  a string key
   * @param data a command or datatype
   */
  @SuppressWarnings("PMD.CompareObjectsWithEquals")
  public void publishCommand(String key, Sendable data) {
    Sendable sddata = tablesToData.get(key);
    if (sddata == null || sddata != data) {
      tablesToData.put(key, data);
      NetworkTable dataTable = rosTable.getSubTable(key);
      SendableRegistry.publish(data, dataTable);
      dataTable.getEntry(".name").setString(key);
    }
  }

  public double getStarboardSpeed(){return coprocessorStarboard.getDouble(0);} // A number in m/s (translate to ticks/100ms in Drivetrain)
  public double getPortSpeed(){return coprocessorPort.getDouble(0);} // A number in m/s

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
