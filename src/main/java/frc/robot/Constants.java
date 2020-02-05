/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    //motor IDs
    public static final int FL_TALON = 10;
    public static final int BL_TALON = 11;
    public static final int FR_TALON = 12;
    public static final int BR_TALON = 13;
    public static final int INTAKE_MOTOR_PORT = 18;

    //TODO: Make these accurate
    public static final int WINCH1_PORT = 0;
    public static final int WINCH2_PORT = 0;

    //solenoid IDs
    public static final int HIGH_GEAR_SOLENOID_ID = 7;
	//public static final int LOW_GEAR_SOLENOID_ID = 7;
    
    //auton
    public static final double AUTON_DRIVE_FORWARD_DISTANCE = 500;
	public static final double AUTON_DRIVE_FORWARD_SPEED = .5;
	
    //speed values
	public static final int INTAKE_SPEED = 1;
    public static final int OUTTAKE_SPEED = -1;
    public static final double STOP_MOTOR_SPEED = 0;
    public static final double NORMAL_SPEED_MODIFIER = 1;
	public static final double LOW_SPEED_MODIFIER = 0.5;
    public static final double MAX_MOTOR_SPEED = 1;
    
    //PID slots
	public static final int DRIVE_PID_SLOT = 0;
	public static final double MAX_GLIDE_SPEED = 0;
	public static final double MAX_LIFT_SPEED = 0;
	public static final int CLIMB_ARM1_PORT = 0;
	
}

