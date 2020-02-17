
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
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Gamepad.DPad_JoystickButton;
import frc.robot.Gamepad.GamepadConstants;
import frc.robot.commands.AlignColorCommand;
import frc.robot.commands.BlasterConstantOutputCommand;
import frc.robot.commands.BlasterPercentOutput;
import frc.robot.commands.BackboardToggleCommand;
import frc.robot.commands.ConveyorCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.DriveForwardDistanceCommand;
import frc.robot.commands.DriveForwardGyroDistanceCommand;
import frc.robot.commands.GyroDriveForDistCommand;
import frc.robot.commands.IndexToBlasterCommand;
import frc.robot.commands.WinchCommand;
import frc.robot.commands.LowerIntakeArmCommand;
import frc.robot.commands.ManualTurretCommand;
import frc.robot.commands.GondolaCommand;
import frc.robot.commands.MeasureDistanceCommand;
import frc.robot.commands.PanelMotor;
import frc.robot.commands.RaiseIntakeArmCommand;
import frc.robot.commands.ReverseIndexToBlasterCommand;
import frc.robot.commands.RotatePanelCommand;
import frc.robot.commands.SetLowSpeedCommand;
import frc.robot.commands.SetNormalSpeedCommand;
import frc.robot.commands.RaiseElevatorCommand;
import frc.robot.commands.ShiftHighGearCommand;
import frc.robot.commands.ShiftLowGearCommand;
import frc.robot.subsystems.HangSubsystem;
import frc.robot.subsystems.GyroSubsystem;
import frc.robot.commands.StopIntakeOuttakeCommand;
import frc.robot.commands.TestCommand;
import frc.robot.commands.TurnToTargetCommand;
import frc.robot.commands.TurretManualCommand;
import frc.robot.commands.StartOuttakeCommand;
import frc.robot.commands.StartIntakeCommand;
import frc.robot.subsystems.BlasterSubsystem;
import frc.robot.subsystems.ControlPanelSubsystem;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.GondolaSubsystem;
import frc.robot.subsystems.GyroPIDSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LidarSubsystem;
import frc.robot.subsystems.TurretSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
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
  private GyroSubsystem gyroSubsystem = GyroSubsystem.getInstance();
  private final DriveTrainSubsystem driveTrainSubsystem = new DriveTrainSubsystem(gyroSubsystem);
  private final HangSubsystem hangSubsystem = new HangSubsystem();
  private final ConveyorSubsystem conveyorSubsystem = new ConveyorSubsystem();
  private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
  private LidarSubsystem lidarSubsystem = null;
  private TurretSubsystem turretSubsystem = null;
  private final GondolaSubsystem gondolaSubsystem = new GondolaSubsystem();
  private final ControlPanelSubsystem panelSubsystem = new ControlPanelSubsystem();
  private final BlasterSubsystem blasterSubsystem = new BlasterSubsystem();
  
  // commands
  private DriveForwardDistanceCommand autonDriveForwardDistanceCommand;
  private GyroDriveForDistCommand autonGyroDriveForwardDistanceCommand;
  private SequentialCommandGroup autonDriveForwardGyroDistanceCommand;
  private WinchCommand winchCommand;
  private RaiseElevatorCommand raiseElevatorCommand;
  private GondolaCommand gondolaCommand;
  private ConveyorCommand conveyorCommand;
  private BackboardToggleCommand backboardToggleCommand;
  
  

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    if (Robot.isReal()) {
      lidarSubsystem = new LidarSubsystem();
      // gyroSubsystem = new GyroSubsystem();
      turretSubsystem = new TurretSubsystem();
    }
    
    // Configure the button bindings
    configureButtonBindings();
    driveTrainSubsystem.resetEncoders();

    //auton commands
    autonDriveForwardDistanceCommand = new DriveForwardDistanceCommand(driveTrainSubsystem,
        Constants.AUTON_DRIVE_FORWARD_DISTANCE, Constants.AUTON_DRIVE_FORWARD_SPEED);

    // autonGyroDriveForwardDistanceCommand = new GyroDriveForDistCommand(driveTrainSubsystem,
        // Constants.AUTON_DRIVE_FORWARD_DISTANCE, Constants.AUTON_DRIVE_FORWARD_SPEED, gyroSubsystem.getYaw());
        double autonSpeed = .75;
    autonDriveForwardGyroDistanceCommand = new DriveForwardGyroDistanceCommand(driveTrainSubsystem, Constants.AUTON_PUSH_ROBOT_DISTANCE, autonSpeed*.5, 0, true).andThen(new WaitCommand(1)).andThen(new DriveForwardGyroDistanceCommand(driveTrainSubsystem, Constants.AUTON_FORWARD_BALL_PICKUP_DISTANCE, -autonSpeed, 0, false));
    

  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */

  private void configureButtonBindings() {
    //primary buttons
    final JoystickButton primaryBackButton = new JoystickButton(primaryJoystick, GamepadConstants.BUTTON_BACK);
    final JoystickButton primaryStartButton = new JoystickButton(primaryJoystick, GamepadConstants.BUTTON_START);
    final JoystickButton primaryXButton = new JoystickButton(primaryJoystick, GamepadConstants.BUTTON_X);
    final JoystickButton primaryYButton = new JoystickButton(primaryJoystick, GamepadConstants.BUTTON_Y);
    final JoystickButton primaryBButton = new JoystickButton(primaryJoystick, GamepadConstants.BUTTON_B);
    final JoystickButton primaryAButton = new JoystickButton(primaryJoystick, GamepadConstants.BUTTON_A);
    final JoystickButton primaryLB = new JoystickButton(primaryJoystick, GamepadConstants.BUTTON_LB);
    final JoystickButton primaryRB = new JoystickButton(primaryJoystick, GamepadConstants.BUTTON_RB);
    final JoystickButton primaryLeftStickButton = new JoystickButton(primaryJoystick, GamepadConstants.BUTTON_LEFT_STICK);
    final JoystickButton primaryRightStickButton = new JoystickButton(primaryJoystick, GamepadConstants.BUTTON_RIGHT_STICK);
    //primary DPad
    final DPad_JoystickButton primaryDPadN = new DPad_JoystickButton(secondaryJoystick, GamepadConstants.DPAD_N_ANGLE);
    final DPad_JoystickButton primaryDPadNW = new DPad_JoystickButton(secondaryJoystick, GamepadConstants.DPAD_NW_ANGLE);
    final DPad_JoystickButton primaryDPadW = new DPad_JoystickButton(secondaryJoystick, GamepadConstants.DPAD_W_ANGLE);
    final DPad_JoystickButton primaryDPadSW = new DPad_JoystickButton(secondaryJoystick, GamepadConstants.DPAD_SW_ANGLE);
    final DPad_JoystickButton primaryDPadS = new DPad_JoystickButton(secondaryJoystick, GamepadConstants.DPAD_S_ANGLE);
    final DPad_JoystickButton primaryDPadSE = new DPad_JoystickButton(secondaryJoystick, GamepadConstants.DPAD_SE_ANGLE);
    final DPad_JoystickButton primaryDPadE = new DPad_JoystickButton(secondaryJoystick, GamepadConstants.DPAD_E_ANGLE);
    final DPad_JoystickButton primaryDPadNE = new DPad_JoystickButton(secondaryJoystick, GamepadConstants.DPAD_NE_ANGLE);
    //primary axes
    //RIGHT TRIGGER       primaryJoystick.getTriggerAxis(Hand.kRight)
    //LEFT TRIGGER        primaryJoystick.getTriggerAxis(Hand.kLeft)
    //LEFT STICK X AXIS   primaryJoystick.getX(Hand.kLeft)
    //LEFT STICK Y AXIS   primaryJoystick.getY(Hand.kLeft)
    //RIGHT STICK X AXIS  primaryJoystick.getX(Hand.kRight)
    //RIGHT STICK Y AXIS  primaryJoystick.getY(Hand.kRight)
    
    //secondary buttons
    final JoystickButton secondaryBackButton = new JoystickButton(secondaryJoystick, GamepadConstants.BUTTON_BACK);
    final JoystickButton secondaryStartButton = new JoystickButton(secondaryJoystick, GamepadConstants.BUTTON_START);
    final JoystickButton secondaryXButton = new JoystickButton(secondaryJoystick, GamepadConstants.BUTTON_X);
    final JoystickButton secondaryYButton = new JoystickButton(secondaryJoystick, GamepadConstants.BUTTON_Y);
    final JoystickButton secondaryBButton = new JoystickButton(secondaryJoystick, GamepadConstants.BUTTON_B);
    final JoystickButton secondaryAButton = new JoystickButton(secondaryJoystick, GamepadConstants.BUTTON_A);
    final JoystickButton secondaryLB = new JoystickButton(secondaryJoystick, GamepadConstants.BUTTON_LB);
    final JoystickButton secondaryRB = new JoystickButton(secondaryJoystick, GamepadConstants.BUTTON_RB);  
    //secondary DPad  
    final JoystickButton secondaryLeftStickButton = new JoystickButton(secondaryJoystick, GamepadConstants.BUTTON_LEFT_STICK);
    final JoystickButton secondaryRightStickButton = new JoystickButton(secondaryJoystick, GamepadConstants.BUTTON_RIGHT_STICK);
    final DPad_JoystickButton secondaryDPadN = new DPad_JoystickButton(secondaryJoystick, GamepadConstants.DPAD_N_ANGLE);
    final DPad_JoystickButton secondaryDPadNW = new DPad_JoystickButton(secondaryJoystick, GamepadConstants.DPAD_NW_ANGLE);
    final DPad_JoystickButton secondaryDPadW = new DPad_JoystickButton(secondaryJoystick, GamepadConstants.DPAD_W_ANGLE);
    final DPad_JoystickButton secondaryDPadSW = new DPad_JoystickButton(secondaryJoystick, GamepadConstants.DPAD_SW_ANGLE);
    final DPad_JoystickButton secondaryDPadS = new DPad_JoystickButton(secondaryJoystick, GamepadConstants.DPAD_S_ANGLE);
    final DPad_JoystickButton secondaryDPadSE = new DPad_JoystickButton(secondaryJoystick, GamepadConstants.DPAD_SE_ANGLE);
    final DPad_JoystickButton secondaryDPadE = new DPad_JoystickButton(secondaryJoystick, GamepadConstants.DPAD_E_ANGLE);
    final DPad_JoystickButton secondaryDPadNE = new DPad_JoystickButton(secondaryJoystick, GamepadConstants.DPAD_NE_ANGLE);
    //secondary axes    
    //RIGHT TRIGGER       secondaryJoystick.getTriggerAxis(Hand.kRight)
    //LEFT TRIGGER        secondaryJoystick.getTriggerAxis(Hand.kLeft)
    //LEFT STICK X AXIS   secondaryJoystick.getX(Hand.kLeft)
    //LEFT STICK Y AXIS   secondaryJoystick.getY(Hand.kLeft)
    //RIGHT STICK X AXIS  secondaryJoystick.getX(Hand.kRight)
    //RIGHT STICK Y AXIS  secondaryJoystick.getY(Hand.kRight)

    driveTrainSubsystem.setDefaultCommand(new DriveCommand(driveTrainSubsystem, () -> primaryJoystick.getY(Hand.kLeft),
    () -> primaryJoystick.getX(Hand.kRight)));

    hangSubsystem.setDefaultCommand(new RaiseElevatorCommand(hangSubsystem, () -> secondaryJoystick.getY(Hand.kLeft)));
    gondolaSubsystem.setDefaultCommand(new GondolaCommand(gondolaSubsystem, () -> secondaryJoystick.getX(Hand.kLeft)));
    intakeSubsystem.setDefaultCommand(new StartIntakeCommand(intakeSubsystem, () -> secondaryJoystick.getY(Hand.kRight)));
    //turretSubsystem.setDefaultCommand(new TurretManualCommand(turretSubsystem, ()->secondaryJoystick.getTriggerAxis(Hand.kLeft), ()->secondaryJoystick.getTriggerAxis(Hand.kRight)));
    turretSubsystem.setDefaultCommand(new ManualTurretCommand(turretSubsystem, ()->secondaryJoystick.getTriggerAxis(Hand.kLeft), ()->secondaryJoystick.getTriggerAxis(Hand.kRight)));
    conveyorSubsystem.setDefaultCommand(new ConveyorCommand(conveyorSubsystem, ()-> secondaryJoystick.getY(Hand.kRight)));
    // blasterSubsystem.setDefaultCommand(new BlasterPercentOutput(blasterSubsystem, () -> primaryJoystick.getTriggerAxis(Hand.kRight)));
    // BlasterSubsystem.setDefaultCommand(new *command*() );
    SmartDashboard.putData(new BlasterConstantOutputCommand(blasterSubsystem));
    secondaryLB.whenHeld(new BlasterConstantOutputCommand(blasterSubsystem));
    secondaryRB.whileHeld(new TurnToTargetCommand(turretSubsystem), false);

    // intake subsystem
    secondaryDPadN.whenPressed(new RaiseIntakeArmCommand(intakeSubsystem));
    secondaryDPadS.whenPressed(new LowerIntakeArmCommand(intakeSubsystem));    
    secondaryYButton.whenHeld(new IndexToBlasterCommand(intakeSubsystem));
    secondaryBButton.whenHeld(new ReverseIndexToBlasterCommand(intakeSubsystem));
    SmartDashboard.putData(new IndexToBlasterCommand(intakeSubsystem));

    //secondaryYButton.whenPressed(new BackboardToggleCommand(BlasterSubsystem));
    
    // startIntakeCommand.addRequirements(elevatorSubsystem, conveyorSubsystem, alignmentBeltSubsystem);
    // StartIntakeCommand startIntakeCommand = new StartIntakeCommand(intakeSubsystem, () -> secondaryJoystick.getY(Hand.kRight));
    // primaryYButton.whenPressed(new StartOuttakeCommand(intakeSubsystem));
    // primaryAButton.whenReleased(new StopIntakeOuttakeCommand(intakeSubsystem));
    // //primaryAButton.whileHeld(new TestCo  mmand());
    // primaryYButton.whenReleased(new StopIntakeOuttakeCommand(intakeSubsystem));

    // Turret subsystem
    //TurretManualCommand turretManualCommand = new TurretManualCommand(turretSubsystem,
    //    () -> secondaryJoystick.getTriggerAxis(Hand.kLeft), () -> secondaryJoystick.getTriggerAxis(Hand.kRight));
    //secondaryLB.whenHeld(new TurnToTargetCommand(turretSubsystem));

    // drive subsystem
    primaryAButton.whenPressed(new ShiftLowGearCommand(driveTrainSubsystem));
    primaryYButton.whenPressed(new ShiftHighGearCommand(driveTrainSubsystem));
    primaryLB.whenPressed(new SetLowSpeedCommand(driveTrainSubsystem));
    primaryRB.whenPressed(new SetNormalSpeedCommand(driveTrainSubsystem));

    //hang subsystem
    secondaryAButton.whenHeld(new WinchCommand(hangSubsystem), false);
    //raiseElevatorCommand = new RaiseElevatorCommand(hangSubsystem, () -> secondaryJoystick.getY(Hand.kLeft));    
    //gondolaCommand = new GondolaCommand(hangSubsystem, ()->secondaryJoystick.getX(Hand.kLeft));
    
    //control panel
    primaryXButton.whenPressed(new RotatePanelCommand(panelSubsystem), false);
    primaryBButton.whenPressed(new AlignColorCommand(panelSubsystem), false);
    secondaryXButton.whenHeld(new PanelMotor(panelSubsystem));

    // lidar susbsystem
    // primaryXButton.whenPressed(new MeasureDistanceCommand(lidarSubsystem));

    //MODE SHIFTING
    //secondaryBackButton.whenPressed(startIntakeCommand);
    secondaryStartButton.whenPressed(new StopIntakeOuttakeCommand(intakeSubsystem));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // autonDriveForwardDistanceCommand will run in autonomous
    return autonDriveForwardGyroDistanceCommand;
  }
}
