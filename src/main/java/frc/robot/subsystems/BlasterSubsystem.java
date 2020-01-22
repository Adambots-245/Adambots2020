package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class BlasterSubsystem extends SubsystemBase {
    /**
     * Creates a new Intake.
     */

    public WPI_TalonSRX BlasterMotor;
    public WPI_TalonSRX TurretMotor;
    public Solenoid Backboard;

    public BlasterSubsystem() {
        super();

        BlasterMotor = new WPI_TalonSRX(Constants.BLASTER_MOTOR_PORT);
        TurretMotor = new WPI_TalonSRX(Constants.TURRET_MOTOR_PART);
        Backboard = new Solenoid(Constants.SOLENOID)
    }

    public void output(double speed) {
        BlasterMotor.set(ControlMode.PercentOutput, speed);
    }

    public void periodic() {
        // This method will be called once per scheduler run
    }

}