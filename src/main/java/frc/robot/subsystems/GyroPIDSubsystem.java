/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.Constants;

public class GyroPIDSubsystem extends PIDSubsystem {
  /**
   * Creates a new GyroPIDSubsystem.
   */
  GyroSubsystem gyroSubsystem;
  public GyroPIDSubsystem() {
    super(new PIDController(Constants.GYRO_kP, Constants.GYRO_kI, Constants.GYRO_kD));
    getController().setTolerance(Constants.GYRO_TOLERANCE);
    setSetpoint(0);
    gyroSubsystem = GyroSubsystem.getInstance();
  }

  @Override
  public void useOutput(double output, double targetAngle) {
    //setSetpoint(targetAngle);

    // Use the output here
  }

  public GyroSubsystem getGyroSubsystem(){
    return gyroSubsystem;
  }

  @Override
  public double getMeasurement() {
    // Return the process variable measurement here
    return gyroSubsystem.getYaw();
  }

  public boolean atSetpoint() {
    return m_controller.atSetpoint();
  }
}
