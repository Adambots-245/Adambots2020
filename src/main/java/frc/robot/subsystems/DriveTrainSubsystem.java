/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrainSubsystem extends SubsystemBase {
  /**
   * Creates a new DriveTrainNew.
   */
  private DoubleSolenoid GearShifter;

  private WPI_TalonSRX BackLeftMotor;
  private WPI_TalonSRX FrontRightMotor;
  private WPI_TalonSRX FrontLeftMotor;
  private WPI_TalonSRX BackRightMotor;

  DifferentialDrive drive;

  private double speedModifier;

  public DriveTrainSubsystem() {
    super();
    speedModifier = Constants.NORMAL_SPEED_MODIFIER;

    GearShifter = new DoubleSolenoid(Constants.HIGH_GEAR_SOLENOID_ID, Constants.LOW_GEAR_SOLENOID_ID);

    FrontRightMotor = new WPI_TalonSRX(Constants.FR_TALON);
    FrontLeftMotor = new WPI_TalonSRX(Constants.FL_TALON);
    BackLeftMotor = new WPI_TalonSRX(Constants.BL_TALON);
    BackRightMotor = new WPI_TalonSRX(Constants.BR_TALON);
    BackLeftMotor.follow(FrontLeftMotor);
    BackRightMotor.follow(FrontRightMotor);

    BackLeftMotor.setInverted(true);
    FrontLeftMotor.setInverted(true);
    BackRightMotor.setInverted(true);
    FrontRightMotor.setInverted(true);

    drive = new DifferentialDrive(FrontLeftMotor, FrontRightMotor);
  }

  public void resetEncoders() {
    FrontLeftMotor.getSensorCollection().setQuadraturePosition(0, 0);
    FrontRightMotor.getSensorCollection().setQuadraturePosition(0, 0);
    // BackLeftMotor.getSensorCollection().setQuadraturePosition(0, 0);
    // BackRightMotor.getSensorCollection().setQuadraturePosition(0, 0);
  }

  public double getAverageDriveEncoderValue() {
    double averageEncoderPos = Math
        .abs((FrontLeftMotor.getSelectedSensorPosition() + FrontRightMotor.getSelectedSensorPosition()) / 2);
    System.out.println(averageEncoderPos);
    return averageEncoderPos;
  }

  public void setLowSpeed() {
    speedModifier = Constants.LOW_SPEED_MODIFIER;
  }

  public void setNormalSpeed() {
    speedModifier = Constants.NORMAL_SPEED_MODIFIER;
  }

  public void arcadeDrive(double speed, double turnSpeed) {
    int frontRobotDirection = -1;
    double straightSpeed = frontRobotDirection * speed * speedModifier;
    System.out.println("straightSpeed = " + straightSpeed);
    System.out.println("turnSpeed = " + turnSpeed * speedModifier);

    // double leftSpeed = Math.min(straightSpeed + turnSpeed* speedModifier, Constants.MAX_MOTOR_SPEED);
    // double rightSpeed = Math.min(straightSpeed - turnSpeed* speedModifier, Constants.MAX_MOTOR_SPEED);

    // FrontRightMotor.set(ControlMode.PercentOutput, rightSpeed);
    // FrontLeftMotor.set(ControlMode.PercentOutput, leftSpeed);
    // FrontLeftMotor.setExpiration(.5);
    // FrontRightMotor.setExpiration(.5);
    // System.out.println(FrontLeftMotor.getExpiration());
    drive.arcadeDrive(straightSpeed, turnSpeed * speedModifier);
  }

  public void shiftHighGear() {
    GearShifter.set(Value.kForward);
  }

  public void shiftLowGear() {
    GearShifter.set(Value.kReverse);
  }
  /*
   * public void driveDistance(double distance){
   * FrontLeftMotor.set(ControlMode.Position, distance);
   * FrontRightMotor.set(ControlMode.Position, distance); }
   */

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
