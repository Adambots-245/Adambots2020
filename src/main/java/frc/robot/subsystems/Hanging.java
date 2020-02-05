/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class Hanging extends SubsystemBase {
    /**
     * Creates a new Climb/Elevator Subsystem
     */

    public WPI_VictorSPX elevator;
    //motors for winches and horiz
    public WPI_VictorSPX grab1, grab2;


    public Hanging() {
        super();
        grab1 = new WPI_VictorSPX(Constants.WINCH1_PORT);
        grab2 = new WPI_VictorSPX(Constants.WINCH2_PORT);
        elevator = new WPI_VictorSPX(Constants.CLIMB_ARM1_PORT);
    }

    private double liftSpeed(double elevateSpeed) {
        return Math.min(elevateSpeed, Constants.MAX_LIFT_SPEED);
    }

    private double shiftSpeed(double glideSpeed) {
        return Math.min(glideSpeed, Constants.MAX_GLIDE_SPEED);
    }

    //Rise/lower the elevator arms
    public void elevate(double elevatorSpeed) {
        elevatorSpeed = liftSpeed(elevatorSpeed);
        elevator.set(elevatorSpeed);
    }


    //Move horizontally along the Shield Generator bar
    public void glide(double glideSpeed) {
        double shiftSpeed = shiftSpeed(glideSpeed);
    }

    //Winches gripping the bar
    public void grip(double winchSpeed) {
        double speed = winchSpeed > Constants.MAX_MOTOR_SPEED ? Constants.MAX_MOTOR_SPEED : winchSpeed;
    }

}