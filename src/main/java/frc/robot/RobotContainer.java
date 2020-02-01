/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.Gamepad.DPad_JoystickButton;
import frc.robot.Gamepad.GamepadConstants;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.DriveForwardDistanceCommand;
import frc.robot.commands.GyroDriveForDistCommand;
import frc.robot.commands.MeasureDistanceCommand;
import frc.robot.commands.SetLowSpeedCommand;
import frc.robot.commands.SetNormalSpeedCommand;
import frc.robot.commands.ShiftHighGearCommand;
import frc.robot.commands.ShiftLowGearCommand;
import frc.robot.subsystems.GyroSubsystem;
import frc.robot.commands.StopIntakeOuttakeCommand;
import frc.robot.commands.StartOuttakeCommand;
import frc.robot.commands.StartIntakeCommand;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LidarSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  //private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  //
  //private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  private final XboxController primaryJoystick = new XboxController(1);
  private final XboxController secondaryJoystick = new XboxController(2);

  private final DriveTrainSubsystem driveTrainSubsystem = new DriveTrainSubsystem();
  private DriveCommand drivecommand;

  private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();

  private DriveForwardDistanceCommand autonDriveForwardDistanceCommand;
  private GyroDriveForDistCommand autonGyroDriveForwardDistanceCommand;
  private LidarSubsystem lidarSubsystem = null;
  private GyroSubsystem gyroSubsystem = null;

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    if (Robot.isReal()) {
      lidarSubsystem = new LidarSubsystem();
      gyroSubsystem = new GyroSubsystem();
    }

    // drivecommand = new DriveCommand(drivetrain, primaryJoystick.getY(Hand.kLeft), primaryJoystick.getX(Hand.kRight));
    driveTrainSubsystem.setDefaultCommand(new DriveCommand(driveTrainSubsystem, ()->primaryJoystick.getY(Hand.kLeft), ()->primaryJoystick.getX(Hand.kRight)));

    driveTrainSubsystem.resetEncoders();
    autonDriveForwardDistanceCommand = new  DriveForwardDistanceCommand(driveTrainSubsystem, Constants.AUTON_DRIVE_FORWARD_DISTANCE, Constants.AUTON_DRIVE_FORWARD_SPEED);  
    //autonGyroDriveForwardDistanceCommand = new GyroDriveForDistCommand(driveTrainSubsystem, Constants.AUTON_DRIVE_FORWARD_DISTANCE, Constants.AUTON_DRIVE_FORWARD_SPEED, gyroSubsystem.getYaw());  
 
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    final JoystickButton AButton = new JoystickButton(primaryJoystick, GamepadConstants.BUTTON_A);  
    final JoystickButton YButton = new JoystickButton(primaryJoystick, GamepadConstants.BUTTON_Y);  
    final JoystickButton LB = new JoystickButton(primaryJoystick, GamepadConstants.BUTTON_LB);  
    final JoystickButton RB = new JoystickButton(primaryJoystick, GamepadConstants.BUTTON_RB);
    final JoystickButton XButton = new JoystickButton(primaryJoystick, GamepadConstants.BUTTON_X);
    final JoystickButton BButton = new JoystickButton(primaryJoystick, GamepadConstants.BUTTON_B);
    
    final DPad_JoystickButton DPadN = new DPad_JoystickButton(primaryJoystick, GamepadConstants.DPAD_N_ANGLE);    

    //intake subsystem
    AButton.whenPressed(new StartIntakeCommand(intakeSubsystem));
    YButton.whenPressed(new StartOuttakeCommand(intakeSubsystem));
    AButton.whenReleased(new StopIntakeOuttakeCommand(intakeSubsystem));
    YButton.whenReleased(new StopIntakeOuttakeCommand(intakeSubsystem));
    
    //drive subsystem
    LB.whenPressed(new ShiftLowGearCommand(driveTrainSubsystem));
    RB.whenPressed(new ShiftHighGearCommand(driveTrainSubsystem));
    XButton.whenPressed(new SetLowSpeedCommand(driveTrainSubsystem));
    BButton.whenPressed(new SetNormalSpeedCommand(driveTrainSubsystem));

    //XButton.whenPressed(new MeasureDistanceCommand(lidarSubsystem));
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
  //An ExampleCommand will run in autonomous
    return autonDriveForwardDistanceCommand;
  }
}
