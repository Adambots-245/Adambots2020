/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import org.opencv.core.*;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final int FL_TALON = 0;
    public static final int BL_TALON = 1;
    public static final int FR_TALON = 2;
    public static final int BR_TALON = 3;
    public static final int INTAKE_MOTOR_PORT = 6;
    
	public static final int INTAKE_SPEED = 1;
    public static final int OUTTAKE_SPEED = -1;
    
    // Vision Constants
    public static final int CAM_NUMBER = 0;
    public static final int IMG_WIDTH = 320;
    public static final int IMG_HEIGHT = 240;
    public static final int HOR_FOV_DEGREES = 60;
    public static final double HOR_DEGREES_PER_PIXEL = (double)HOR_FOV_DEGREES / IMG_WIDTH;
    public static final int IMG_HOR_MID = IMG_WIDTH / 2;
    public static final int DRIVER_STATION_FPS = 6;
    public static final int PROCESSING_FPS = 30;

    // Colors are (B, G, R) (Don't ask me)
    public static final Scalar RED = new Scalar(0, 0, 255);
    public static final Scalar GREEN = new Scalar(0, 255, 0);
    public static final Scalar BLUE = new Scalar(255, 0, 0);

    // turret PID constants
    public static int TURRET_MOTOR_PORT = 22;
    public static double TURRET_kP = 0.5;
    public static double TURRET_kI = 0;
    public static double TURRET_kD = 0;
    public static int TURRET_TOLERANCE = 5;
    public static final double TURRET_TARGET_ANGLE = 0;
	public static final double TURRET_SPEED = 0.5;

}

