

package main.java.frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HangSubsystem extends SubsystemBase{

    //TODO Add hangmotor Constant
    public WPI_TalonSRX hangMotor;

    public HangSubsystem(){
        super();

        //hangMotor = new WPI_TalonSRX(*THE NAME OF THE CONSTANT*);
    }

    public void climb(double speed){
        hangMotor.set(ControlMode.PercentOutput, speed);
    }
    
    
    public void periodic(){


    }
}
