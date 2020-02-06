/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.Gamepad.DPad_JoystickButton;
import frc.robot.Gamepad.GamepadConstants;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.DriveForwardDistanceCommand;
import frc.robot.commands.GyroDriveForDistCommand;
import frc.robot.commands.LowerIntakeArmCommand;
import frc.robot.commands.MeasureDistanceCommand;
import frc.robot.commands.RaiseIntakeArmCommand;
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
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  // private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  //
  // private final ExampleCommand m_autoCommand = new
  // ExampleCommand(m_exampleSubsystem);

  // controllers
  private final XboxController primaryJoystick = new XboxController(GamepadConstants.PRIMARY_DRIVER);
  private final XboxController secondaryJoystick = new XboxController(GamepadConstants.SECONDARY_DRIVER);

  // subsystems
  private final DriveTrainSubsystem driveTrainSubsystem = new DriveTrainSubsystem();
  private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
  private LidarSubsystem lidarSubsystem = null;
  private GyroSubsystem gyroSubsystem = null;

  // commands
  private DriveForwardDistanceCommand autonDriveForwardDistanceCommand;
  private GyroDriveForDistCommand autonGyroDriveForwardDistanceCommand;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    if (Robot.isReal()) {
      lidarSubsystem = new LidarSubsystem();
      gyroSubsystem = new GyroSubsystem();
    }
    
    driveTrainSubsystem.resetEncoders();
    driveTrainSubsystem.setDefaultCommand(new DriveCommand(driveTrainSubsystem, () -> primaryJoystick.getY(Hand.kLeft),
        () -> primaryJoystick.getX(Hand.kRight)));

    //commands
    autonDriveForwardDistanceCommand = new DriveForwardDistanceCommand(driveTrainSubsystem,
        Constants.AUTON_DRIVE_FORWARD_DISTANCE, Constants.AUTON_DRIVE_FORWARD_SPEED);

    autonGyroDriveForwardDistanceCommand = new GyroDriveForDistCommand(driveTrainSubsystem,
        Constants.AUTON_DRIVE_FORWARD_DISTANCE, Constants.AUTON_DRIVE_FORWARD_SPEED, gyroSubsystem.getYaw());

  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    //primary buttons
    final JoystickButton primaryAButton = new JoystickButton(primaryJoystick, GamepadConstants.BUTTON_A);
    final JoystickButton primaryYButton = new JoystickButton(primaryJoystick, GamepadConstants.BUTTON_Y);
    final JoystickButton primaryLB = new JoystickButton(primaryJoystick, GamepadConstants.BUTTON_LB);
    final JoystickButton primaryRB = new JoystickButton(primaryJoystick, GamepadConstants.BUTTON_RB);
    final JoystickButton primaryXButton = new JoystickButton(primaryJoystick, GamepadConstants.BUTTON_X);
    final JoystickButton primaryBButton = new JoystickButton(primaryJoystick, GamepadConstants.BUTTON_B);

    //secondary buttons
    final JoystickButton secondaryStartButton = new JoystickButton(secondaryJoystick, GamepadConstants.BUTTON_START);
    final JoystickButton secondaryBackButton = new JoystickButton(secondaryJoystick, GamepadConstants.BUTTON_BACK);
    final DPad_JoystickButton secondaryDPadN = new DPad_JoystickButton(secondaryJoystick, GamepadConstants.DPAD_N_ANGLE);
    final DPad_JoystickButton secondaryDPadS = new DPad_JoystickButton(secondaryJoystick, GamepadConstants.DPAD_S_ANGLE);

    StartIntakeCommand startIntakeCommand = new StartIntakeCommand(intakeSubsystem,
        () -> secondaryJoystick.getY(Hand.kRight));

    // intake subsystem

    // startIntakeCommand.addRequirements(elevatorSubsystem, conveyorSubsystem, alignmentBeltSubsystem);
    secondaryBackButton.whenPressed(startIntakeCommand);
    secondaryStartButton.whenPressed(new StopIntakeOuttakeCommand(intakeSubsystem));

    primaryYButton.whenPressed(new StartOuttakeCommand(intakeSubsystem));
    primaryAButton.whenReleased(new StopIntakeOuttakeCommand(intakeSubsystem));
    primaryYButton.whenReleased(new StopIntakeOuttakeCommand(intakeSubsystem));

    secondaryDPadN.whenPressed(new RaiseIntakeArmCommand(intakeSubsystem));
    secondaryDPadS.whenPressed(new LowerIntakeArmCommand(intakeSubsystem));

    // drive subsystem
    primaryLB.whenPressed(new ShiftLowGearCommand(driveTrainSubsystem));
    primaryRB.whenPressed(new ShiftHighGearCommand(driveTrainSubsystem));
    primaryXButton.whenPressed(new SetLowSpeedCommand(driveTrainSubsystem));
    primaryBButton.whenPressed(new SetNormalSpeedCommand(driveTrainSubsystem));

    // primaryXButton.whenPressed(new MeasureDistanceCommand(lidarSubsystem));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // autonDriveForwardDistanceCommand will run in autonomous
    return autonDriveForwardDistanceCommand;
  }
}
