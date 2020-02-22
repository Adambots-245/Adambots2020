/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.Constants;

public class TurretSubsystem extends PIDSubsystem {

  private final VictorSPX turretMotor = new VictorSPX(Constants.TURRET_MOTOR_PORT);
  private NetworkTable table;
  private double angleOffset = 0;
  //private final SimpleMotorFeedforward m_shooterFeedforward =
  //    new SimpleMotorFeedforward(ShooterConstants.kSVolts,
  //                               ShooterConstants.kVVoltSecondsPerRotation);
  
  //Tuning values
  static double kP = SmartDashboard.getNumber("kP", 0.03);
  static double kI = SmartDashboard.getNumber("kI", 0);
  static double kD = SmartDashboard.getNumber("kD", 30);
  
  /**
   * The shooter subsystem for the robot.
   */
  public TurretSubsystem() {
    // super(new PIDController(kP, kI, kD));

    super(new PIDController(Constants.TURRET_kP, Constants.TURRET_kI, Constants.TURRET_kD));
    getController().setTolerance(Constants.TURRET_TOLERANCE);
    setSetpoint(Constants.TURRET_TARGET_ANGLE);

    NetworkTableInstance instance = NetworkTableInstance.getDefault();
    table = instance.getTable("Vision");

  }

  @Override
  public void useOutput(double output, double setpoint) {
    // kP = SmartDashboard.getNumber("kP", 0.03);
    // kI = SmartDashboard.getNumber("kI", 0);
    // kD = SmartDashboard.getNumber("kD", 30);
  
    
    // System.out.println("Output: " + output);
    // getController().setP(kP);
    // getController().setI(kI);
    // getController().setD(kD);

    // System.out.println("kP: " + kP + " kD: " + kD);
    // System.out.println("Current Angle in Output: " + table.getEntry("Angle").getDouble(0));

    Double measurement = getMeasurement();
    double calculatedOutput = getController().calculate(measurement, setpoint);
    // System.out.println("Calculated Output: " + calculatedOutput);

    turretMotor.set(ControlMode.PercentOutput, -calculatedOutput);
  }

  @Override
  public double getMeasurement() {
    // System.out.println("Current Angle: " + table.getEntry("Angle").getDouble(0));
    return table.getEntry("Angle").getDouble(0);
  }

  public boolean atSetpoint() {
    System.out.println("At Set Point: " + m_controller.atSetpoint());
    return m_controller.atSetpoint();
  }

  public void runTurret(double speed) {
    turretMotor.set(ControlMode.PercentOutput, speed);
  }

  public void stopTurret() {
    turretMotor.set(ControlMode.PercentOutput, 0);
  }

  public double getOffset() {
    return angleOffset;
  }

  public void changeOffset(double value) {
    angleOffset += value;
    setSetpoint(Constants.TURRET_TARGET_ANGLE + angleOffset);
  }

  public void setSpeed(double speed){
    turretMotor.set(ControlMode.PercentOutput, speed);
  }

  

}
