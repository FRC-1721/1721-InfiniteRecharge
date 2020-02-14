/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Solver extends SubsystemBase {
  // Motors
  private static final TalonSRX solverMotor = new TalonSRX(Constants.CANIds.TalonSRX_Solver_Address); // Creates a new motor

  // Sensors and IO
  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort); 

  /**
   * Creates a new Solver.
   */
  public Solver() {
    solverMotor.configFactoryDefault(); // Clear the programming to prevent weirdness

    solverMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, // Configure the feedback sensor
                                             Constants.SolverPID.kPIDLoopIdx,
                                             Constants.SolverPID.kTimeoutMs);

    solverMotor.setSensorPhase(Constants.SolverPID.sensorPhase); // Configure the phase

    solverMotor.configNominalOutputForward(0, Constants.SolverPID.kTimeoutMs); // Configure nominal outputs
    solverMotor.configNominalOutputReverse(0, Constants.SolverPID.kTimeoutMs);
    solverMotor.configPeakOutputForward(1, Constants.SolverPID.kTimeoutMs);
    solverMotor.configPeakOutputReverse(-1, Constants.SolverPID.kTimeoutMs);

    solverMotor.configAllowableClosedloopError(0, Constants.SolverPID.kPIDLoopIdx, Constants.SolverPID.kTimeoutMs); // Set the allowable error

    // Config slot0 gains
    solverMotor.config_kF(Constants.SolverPID.kPIDLoopIdx, Constants.SolverPID.kGains.kF, Constants.SolverPID.kTimeoutMs);
    solverMotor.config_kP(Constants.SolverPID.kPIDLoopIdx, Constants.SolverPID.kGains.kP, Constants.SolverPID.kTimeoutMs);
    solverMotor.config_kI(Constants.SolverPID.kPIDLoopIdx, Constants.SolverPID.kGains.kI, Constants.SolverPID.kTimeoutMs);
    solverMotor.config_kD(Constants.SolverPID.kPIDLoopIdx, Constants.SolverPID.kGains.kD, Constants.SolverPID.kTimeoutMs);
  }

  /**
   * Takes a number just for testing and 
   * sets the solver motor to that speed
   * @author Joe Sedutto
   * @param speed
   */
  public void testSolver(double speed){
    solverMotor.set(ControlMode.PercentOutput, speed);
  }

  /**
   * Rotates the drum until that color
   * @author Joe Sedutto
   * @param speed
   */
  public void solveUntillColor(double red, double green, double blue){
    final Color detectedColor = m_colorSensor.getColor(); 
    final double IR = m_colorSensor.getIR(); 
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
