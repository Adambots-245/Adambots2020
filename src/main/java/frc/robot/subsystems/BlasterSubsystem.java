package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class BlasterSubsystem extends SubsystemBase {
    /**
     * Creates a new Intake.
     */

    public WPI_TalonFX BlasterMotor;
    public Solenoid Backboard;

    public BlasterSubsystem() {
        super();

        BlasterMotor = new WPI_TalonFX(Constants.BLASTER_MOTOR_PORT);
        Backboard = new Solenoid(Constants.RAISE_BLASTER_HOOD_SOL_PORT);
        BlasterMotor.config_kF(0, Constants.BLASTER_KF);
        BlasterMotor.config_kP(0, Constants.BLASTER_KP);
        BlasterMotor.config_kI(0, Constants.BLASTER_KI);
        BlasterMotor.config_kD(0, Constants.BLASTER_KD);
    }

    public void output(double speed) {
        BlasterMotor.set(ControlMode.PercentOutput, speed);
    }
    public void setVelocity(double speed){
        BlasterMotor.set(ControlMode.Velocity, speed);

    }
    public int getVelocity(){
        return BlasterMotor.getSelectedSensorVelocity();
    }

    public void periodic() {
        // This method will be called once per scheduler run

    }

}