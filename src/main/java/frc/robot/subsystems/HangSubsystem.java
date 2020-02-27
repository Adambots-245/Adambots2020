
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class HangSubsystem extends SubsystemBase {

    // TODO Add hangmotor Constant
    public WPI_VictorSPX hangMotor;
    public WPI_VictorSPX winchMotor1;
    public WPI_VictorSPX winchmotor2;

    public HangSubsystem() {
        super();

        // TODO add actual value for motor
        hangMotor = new WPI_VictorSPX(Constants.CLIMBING_RAISE_ELEVATOR_MOTOR_PORT);
        winchMotor1 = new WPI_VictorSPX(Constants.CLIMBING_1_MOTOR_PORT);
        winchmotor2 = new WPI_VictorSPX(Constants.CLIMBING_2_MOTOR_PORT);

        winchmotor2.setInverted(true);
    }

    public void climb(double speed) {
        hangMotor.set(ControlMode.PercentOutput, speed);
    }

    public void winchDown() {
        // TODO: add actual constants for the winch mechanism
        winchmotor2.set(ControlMode.PercentOutput, Constants.WINCH_SPEED);
        winchMotor1.set(ControlMode.PercentOutput, Constants.WINCH_SPEED);
    }

    public void winchOff() {
        winchmotor2.set(ControlMode.PercentOutput, 0);
        winchMotor1.set(ControlMode.PercentOutput, 0);

    }

    public void periodic() {

    }

}
