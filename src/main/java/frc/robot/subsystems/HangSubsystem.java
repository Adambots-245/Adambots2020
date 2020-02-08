

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class HangSubsystem extends SubsystemBase{

    //TODO Add hangmotor Constant
    public static WPI_TalonSRX hangMotor;
    public static WPI_TalonSRX winchMotor1;
    public static WPI_TalonSRX winchmotor2;
    public static WPI_TalonSRX gondola;

    public HangSubsystem(){
        super();

        //TODO add actual value for motor
        hangMotor = new WPI_TalonSRX(Constants.CLIMBING_RAISE_ELEVATOR_MOTOR_PORT);
        winchMotor1 = new WPI_TalonSRX(Constants.CLIMBING_1_MOTOR_PORT);
        winchmotor2 = new WPI_TalonSRX(Constants.CLIMBING_2_MOTOR_PORT);
        gondola = new WPI_TalonSRX(Constants.CLIMBING_GONDOLA_ADJUSTMENT_MOTOR_PORT);
    }

    public static void climb(double speed){
        hangMotor.set(ControlMode.PercentOutput,speed);
    }

    public static void gondola(double speed){
        gondola.set(ControlMode.PercentOutput, speed);
    }

    public static void winchDown(){
        //TODO: add actual constants for the winch mechanism
            winchmotor2.set(ControlMode.PercentOutput, Constants.WINCH_SPEED);
            winchMotor1.set(ControlMode.PercentOutput, Constants.WINCH_SPEED);
        }
    
    
    public void periodic(){


    }
}
