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
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.Constants;

public class TurretSubsystem extends PIDSubsystem {

  private final VictorSPX turretMotor = new VictorSPX(Constants.TURRET_MOTOR_PORT); 
  private DigitalInput leftLimitSwitch = new DigitalInput(Constants.TURRET_LEFT_DIO);
  private DigitalInput rightLimitSwitch = new DigitalInput(Constants.TURRET_RIGHT_DIO);
  
  
  private NetworkTable table;
  private double angleOffset = 0;
  //private final SimpleMotorFeedforward m_shooterFeedforward =
  //    new SimpleMotorFeedforward(ShooterConstants.kSVolts,
  //                               ShooterConstants.kVVoltSecondsPerRotation);

  /**
   * The shooter subsystem for the robot.
   */
  public TurretSubsystem() {
    super(new PIDController(Constants.TURRET_kP, Constants.TURRET_kI, Constants.TURRET_kD));
    getController().setTolerance(Constants.TURRET_TOLERANCE);
    setSetpoint(Constants.TURRET_TARGET_ANGLE);

    NetworkTableInstance instance = NetworkTableInstance.getDefault();
    table = instance.getTable("Vision");

  }

  @Override
  public void useOutput(double output, double setpoint) {
    System.out.println("Output: " + output);
    System.out.println("Current Angle in Output: " + table.getEntry("Angle").getDouble(0));

    Double measurement = getMeasurement();
    double calculatedOutput = getController().calculate(measurement, setpoint);
    // System.out.println("Calculated Output: " + calculatedOutput);
    
    setSpeed(-calculatedOutput);  
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
    setSpeed(speed);
  }

  public void stopTurret() {
    setSpeed(Constants.STOP_MOTOR_SPEED);
  }

  public double getOffset() {
    return angleOffset;
  }

  public void changeOffset(double value) {
    angleOffset += value;
    setSetpoint(Constants.TURRET_TARGET_ANGLE + angleOffset);
  }

  public void setSpeed(double speed){
    if (leftLimitSwitch.get()) {
      if (speed < 0)
        turretMotor.set(ControlMode.PercentOutput, Constants.STOP_MOTOR_SPEED);
      else
        turretMotor.set(ControlMode.PercentOutput, speed);
      
    } else if (rightLimitSwitch.get()) {
      if (speed > 0)
        turretMotor.set(ControlMode.PercentOutput, Constants.STOP_MOTOR_SPEED);
      else
        turretMotor.set(ControlMode.PercentOutput, speed);

    } else {
      turretMotor.set(ControlMode.PercentOutput, speed);
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

}
