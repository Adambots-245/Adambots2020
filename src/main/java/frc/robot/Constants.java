/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import org.opencv.core.*;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatch;

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
    // motor IDs
    public static final int FL_TALON = 10;
    public static final int BL_TALON = 11;
    public static final int FR_TALON = 12;
    public static final int BR_TALON = 13;

    public static final int BLASTER_MOTOR_PORT = 14;

    public static final int CLIMBING_1_MOTOR_PORT = 15;
    public static final int CLIMBING_2_MOTOR_PORT = 16;
    public static final int CLIMBING_RAISE_ELEVATOR_MOTOR_PORT = 23;
    public static final int CLIMBING_GONDOLA_ADJUSTMENT_MOTOR_PORT = 24;

    public static final int INTAKE_MOTOR_PORT = 18;
    public static final int INFEED_CONVEYOR_MOTOR_PORT = 19;
    public static final int INFEED_CONVEYOR_INDEXER_MOTOR_PORT = 20;
    public static final int FEED_TO_BLASTER_MOTOR_PORT = 21;

    public static final int TURRET_MOTOR_PORT = 22;

    // TODO: Add accurate PANEL_MOTOR_PORTs (ports of motors that spin control
    // panel)
    public static final int PANEL_MOTOR_PORT = 1;

    // solenoid IDs
    public static final int RAISE_POWER_CELL_INTAKE_SOL_PORT = 0;
    public static final int LOWER_POWER_CELL_INTAKE_SOL_PORT = 1;

    public static final int RAISE_BLASTER_HOOD_SOL_PORT = 2;
    public static final int LOWER_BLASTER_HOOD_SOL_PORT = 3;
    public static final int BACKBOARD_SOLENOID_PORT = 2; //3?

    public static final int YELLOW_LEDS_SOL_PORT = 6;

    public static final int HIGH_GEAR_SOL_PORT = 7;

	
    // sensor IDs
    public static final int LIDAR_DIO = 0;


    // speed values
    public static final int INTAKE_SPEED = 1;
    public static final int OUTTAKE_SPEED = -1;
    public static final double STOP_MOTOR_SPEED = 0;
    public static final double NORMAL_SPEED_MODIFIER = 1;
    public static final double LOW_SPEED_MODIFIER = 0.5;
    public static final double MAX_MOTOR_SPEED = 1;
    public static final double WINCH_SPEED = -0.5;

    // control panel constants
    public final static int MIN_ROTATIONS = 3;
    public final static int MAX_ROTATIONS = 5;
    // TODO: Add accurate panel motor speeds (motors that spin control panel)
    public static final double PANEL_MOTOR_SPEED = 0.5;

    public final static I2C.Port I2C_PORT = I2C.Port.kOnboard;

    public final static ColorSensorV3 M_COLOR_SENSOR = new ColorSensorV3(I2C_PORT);

    public final static ColorMatch M_COLOR_MATCHER = new ColorMatch();

    public final static Color BLUE_TARGET = ColorMatch.makeColor(0.125, 0.424, 0.450);
    public final static Color GREEN_TARGET = ColorMatch.makeColor(0.167, 0.580, 0.252);
    public final static Color RED_TARGET = ColorMatch.makeColor(0.518, 0.347, 0.134);
    public final static Color YELLOW_TARGET = ColorMatch.makeColor(0.311, 0.566, 0.121);

    // The following color order is defined for the sensor moving in a clockwise
    // direction
    // If the control panel itself turns clockwise, the sensor will move in a
    // counterclockwise direction
    public final static String[] COLOR_ORDER = { "Blue", "Green", "Red", "Yellow" };

    // Note: The direction that the color wheel itself turns will be the opposite of
    // the below direction
    public final static String DIRECTION = "Clockwise";

    // The distance between our color sensor and the game's color sensor in number
    // of color slices away
    public final static int DIFFERENTIAL = 2;

    // PID slots
    public static final int DRIVE_PID_SLOT = 0;

    // Vision Constants
    public static final int CAM_NUMBER = 0;
    public static final int IMG_WIDTH = 320;
    public static final int IMG_HEIGHT = 240;
    public static final int HOR_FOV_DEGREES = 60;
    public static final double HOR_DEGREES_PER_PIXEL = (double) HOR_FOV_DEGREES / IMG_WIDTH;
    public static final int IMG_HOR_MID = IMG_WIDTH / 2;
    public static final int DRIVER_STATION_FPS = 6;
    public static final int PROCESSING_FPS = 30;

    // Colors are (B, G, R) (Don't ask me)
    public static final Scalar RED = new Scalar(0, 0, 255);
    public static final Scalar GREEN = new Scalar(0, 255, 0);
    public static final Scalar BLUE = new Scalar(255, 0, 0);

    // turret PID constants
    public static double TURRET_kP = 0.5;
    public static double TURRET_kI = 0;
    public static double TURRET_kD = 0;

    public static int TURRET_TOLERANCE = 5;
    public static final double TURRET_TARGET_ANGLE = 0;
    public static final double TURRET_SPEED = 0.5;

    //gyro PID constants
	public static final double GYRO_kP = 0.3;
	public static final double GYRO_kI = 0;
	public static final double GYRO_kD = 0;
	public static final double GYRO_TOLERANCE = 0;
    public static final float GYRO_TARGET_ANGLE = 0;
    
    //auton constants
	public static final double AUTON_PUSH_ROBOT_DISTANCE = 644;
    public static final double AUTON_FORWARD_BALL_PICKUP_DISTANCE = 7500 + 644;
    public static final double AUTON_DRIVE_FORWARD_DISTANCE = 500;
    public static final double AUTON_DRIVE_FORWARD_SPEED = .5;
    

}
