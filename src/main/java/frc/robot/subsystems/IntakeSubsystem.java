/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
  /**
   * Creates a new Intake.
   */

  public WPI_TalonSRX IntakeMotor;
  private static DoubleSolenoid armRaiseLower;

  public IntakeSubsystem() {
    super();
    armRaiseLower = new DoubleSolenoid(Constants.RAISE_PCM, Constants.LOWER_PCM); // raise = forward lower = kreverse
    IntakeMotor = new WPI_TalonSRX(Constants.INTAKE_MOTOR_PORT);

  }

  public void intake(double speed) {
    IntakeMotor.set(ControlMode.PercentOutput, speed);
  }

  public void outtake() {
    IntakeMotor.set(ControlMode.PercentOutput, Constants.OUTTAKE_SPEED);
  }
  public void stop(){
    IntakeMotor.set(ControlMode.PercentOutput, Constants.STOP_MOTOR_SPEED);
  }

  public void arm(double armSpeed) {
    
  }
    public void RaiseIntake() {
        // Supposedly, if(D-Pad up is pressed)
        //if (GamepadConstants.AXIS_DPAD_POV==true)
        // lol I tried
        armRaiseLower.set(Value.kForward);
        
    }
    
    public void LowerIntake(){
        //Supposedly, if(D-Pad down is pressed)
        //if (GamepadConstants.BUTTON_RB==false)
        //lol I tried
            armRaiseLower.set(Value.kReverse);
        
    }
}


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
