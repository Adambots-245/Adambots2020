package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    }

    public void output(double speed) {
        BlasterMotor.set(ControlMode.PercentOutput, speed);
    }

    public void toggleBackboard() {
        if (Backboard.get()) {
            Backboard.set(false);
            SmartDashboard.putString("Backboard set to ", "near yeeting. ");
        } else { 
            Backboard.set(true);
            SmartDashboard.putString("Backboard set to ", "far yeeting. ");
        }
    }


    public void periodic() {
        // This method will be called once per scheduler run
    }

}