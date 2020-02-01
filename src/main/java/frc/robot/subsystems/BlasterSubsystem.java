package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class BlasterSubsystem extends SubsystemBase {
    /**
     * Creates a new Intake.
     */

    public WPI_TalonSRX BlasterMotor;
    public Solenoid Backboard;

    public BlasterSubsystem() {
        super();

        BlasterMotor = new WPI_TalonSRX(Constants.BLASTER_MOTOR_PORT);
        Backboard = new Solenoid(Constants.BACKBOARD_SOLENOID_PORT);
    }

    public void output(double speed) {
        BlasterMotor.set(ControlMode.PercentOutput, speed);
    }

    public void periodic() {
        // This method will be called once per scheduler run
    }

}