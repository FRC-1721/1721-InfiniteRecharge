package frc.robot;

@SuppressWarnings("checkstyle:MemberName")
public class Gains {
  public final double kP;
  public final double kI;
  public final double kD;
  public final double kF;
  public final int kIzone;
  public final double kPeakOutput;
  
  /**
   * Gains class for handling gains objects.
   * @param _kP Possibly depreciated
   * @param _kI Possibly depreciated
   * @param _kD Possibly depreciated
   * @param _kF Possibly depreciated
   * @param _kIzone Possibly depreciated
   * @param _kPeakOutput Possibly depreciated
   */
  @SuppressWarnings("checkstyle:ParameterName")
  public Gains(double _kP, double _kI, double _kD, double _kF, int _kIzone, double _kPeakOutput) {
    kP = _kP;
    kI = _kI;
    kD = _kD;
    kF = _kF;
    kIzone = _kIzone;
    kPeakOutput = _kPeakOutput;
  }
}