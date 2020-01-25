/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ROS extends SubsystemBase {

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
    networkTableInstance.startServer("ros.ini", "10.17.21.2", 5800);
    networkTableInstance.setUpdateRate(Constants.RobotOperatingSystem.rosUpdateFrequency); // Crank that solja boy
    rosTable = networkTableInstance.getTable(Constants.RobotOperatingSystem.rosTablename); // Get the table ros
    starboardEncoderEntry = rosTable.getEntry(Constants.RobotOperatingSystem.starboardEncoderName); // Get the writable entries
    portEncoderEntry = rosTable.getEntry(Constants.RobotOperatingSystem.portEncoderName);
    rosIndex = rosTable.getEntry(Constants.RobotOperatingSystem.rosIndexName);
    coprocessorPort = rosTable.getEntry("coprocessorPort"); // Coprossesor speed values
    coprocessorStarboard = rosTable.getEntry("coprocessorStarboard");

    // Notifier
    ros_notifier = new Notifier(ROS::updateTables); // Set the ros_notifer to update the command update, in the package ros
    ros_notifier.startPeriodic(Constants.RobotOperatingSystem.rosUpdateFrequency); // Start the ros notifer
  }

  /**
   * Is called by a notifier at the ros update frequency
   */
  public static void updateTables() {
    starboardEncoderEntry.setDouble(Drivetrain.getDriveEncoderStarboard());
    portEncoderEntry.setDouble(Drivetrain.getDriveEncoderPort());
    rosIndex.setNumber(rosIntex);

    // Increase the Index value
    rosIntex = rosIntex + 1;
    if (rosIntex > 255) {
      rosIntex = 1;
    }
  }

  public double getStarboardSpeed(){return coprocessorStarboard.getDouble(0);}
  public double getPortSpeed(){return coprocessorPort.getDouble(0);}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}