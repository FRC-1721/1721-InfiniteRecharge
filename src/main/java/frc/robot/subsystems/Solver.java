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

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Solver extends SubsystemBase {
  // Motors
  // Creates a new motor
  private static final TalonSRX solverMotor
      = new TalonSRX(Constants.CANAddresses.TalonSRX_Solver_Address);

  /**
   * Creates a new Solver.
   */
  public Solver() {
    solverMotor.configFactoryDefault(); // Clear the programming to prevent weirdness

    solverMotor.configSelectedFeedbackSensor(
        FeedbackDevice.CTRE_MagEncoder_Relative, // Configure the feedback sensor
        Constants.SolverPID.kPIDLoopIdx,
        Constants.SolverPID.kTimeoutMs);

    solverMotor.setSensorPhase(Constants.SolverPID.sensorPhase); // Configure the phase

    // Configure nominal outputs
    solverMotor.configNominalOutputForward(0, Constants.SolverPID.kTimeoutMs);
    solverMotor.configNominalOutputReverse(0, Constants.SolverPID.kTimeoutMs);
    solverMotor.configPeakOutputForward(1, Constants.SolverPID.kTimeoutMs);
    solverMotor.configPeakOutputReverse(-1, Constants.SolverPID.kTimeoutMs);

    // Set the allowable error
    solverMotor.configAllowableClosedloopError(
        0, 
        Constants.SolverPID.kPIDLoopIdx, 
        Constants.SolverPID.kTimeoutMs);

    // Config slot0 gains
    solverMotor.config_kF(
        Constants.SolverPID.kPIDLoopIdx, 
        Constants.SolverPID.kGains.kF, 
        Constants.SolverPID.kTimeoutMs);
    solverMotor.config_kP(
        Constants.SolverPID.kPIDLoopIdx, 
        Constants.SolverPID.kGains.kP, 
        Constants.SolverPID.kTimeoutMs);
    solverMotor.config_kI(
        Constants.SolverPID.kPIDLoopIdx, 
        Constants.SolverPID.kGains.kI, 
        Constants.SolverPID.kTimeoutMs);
    solverMotor.config_kD(
        Constants.SolverPID.kPIDLoopIdx, 
        Constants.SolverPID.kGains.kD, 
        Constants.SolverPID.kTimeoutMs);
  }

  /**
   * Takes a number just for testing and 
   * sets the solver motor to that speed.
   * @author Joe Sedutto
   * @param speed A speed value for the solver
   * @deprecated Only use for testing!
   */
  public void testSolver(double speed) {
    solverMotor.set(ControlMode.PercentOutput, speed);
  }

  /**
   * Takes a target and drives the solver
   * to there using only the encoder.
   * @author Joe Sedutto
   * @param target (A target in controlpanel rotations)
   */
  public void setPositionNoColor(double target) {
    solverMotor.set(ControlMode.Position, (target * Constants.SolverPID.TicksPerRotation));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
