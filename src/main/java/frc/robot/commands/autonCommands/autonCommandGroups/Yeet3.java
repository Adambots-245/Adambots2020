/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.autonCommands.autonCommandGroups;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.BlasterDistanceBasedCommand;
import frc.robot.commands.ConveyorCommand;
import frc.robot.commands.IndexToBlasterCommand;
import frc.robot.commands.ManualTurretCommand;
import frc.robot.commands.TurnToTargetCommand;
import frc.robot.commands.autonCommands.DriveForwardGyroDistanceCommand;
import frc.robot.commands.autonCommands.TimedBlasterDistanceBasedCommand;
import frc.robot.commands.autonCommands.TimedCommand;
<<<<<<< HEAD
import frc.robot.commands.autonCommands.TimedManualTurretCommand;
import frc.robot.subsystems.BlasterSubsystem;
=======
import frc.robot.sensors.Lidar;
>>>>>>> e887a2d9761597a0efca33e497f0d9e85dd2cb0a
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.TurretSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class Yeet3 extends SequentialCommandGroup {
  /**
   * Creates a new Yeet3.
   */
<<<<<<< HEAD
  public Yeet3(TurretSubsystem turretSubsystem, DriveTrainSubsystem driveTrainSubsystem, ConveyorSubsystem conveyorSubsystem, IntakeSubsystem intakeSubsystem, LidarSubsystem lidarSubsystem, BlasterSubsystem blasterSubsystem, XboxController joystick) {
=======
  public Yeet3(TurretSubsystem turretSubsystem, DriveTrainSubsystem driveTrainSubsystem, ConveyorSubsystem conveyorSubsystem, IntakeSubsystem intakeSubsystem, Lidar lidar) {
>>>>>>> e887a2d9761597a0efca33e497f0d9e85dd2cb0a
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
      // new ParallelCommandGroup(
        new DriveForwardGyroDistanceCommand(driveTrainSubsystem, Constants.AUTON_DRIVE_OFF_LINE_DISTANCE, -Constants.AUTON_DRIVE_OFF_LINE_SPEED, 0, true),//true
        new TimedManualTurretCommand(turretSubsystem, () -> 0, () -> 1, 2350),
        new TimedManualTurretCommand(turretSubsystem, () -> 0, () -> 0, 1000),
      // ),

<<<<<<< HEAD
    //  new TimedCommand(new TurnToTargetCommand(turretSubsystem, lidarSubsystem), 3000),
    new TurnToTargetCommand(turretSubsystem, lidarSubsystem),
    new TimedManualTurretCommand(turretSubsystem, () -> 0, () -> 0, 1000),
    // new TimedBlasterDistanceBasedCommand(blasterSubsystem, lidarSubsystem, 20),
    new TimedBlasterDistanceBasedCommand(blasterSubsystem, lidarSubsystem, 2000),
    new ParallelCommandGroup(
      new BlasterDistanceBasedCommand(blasterSubsystem, lidarSubsystem, joystick),
      new IndexToBlasterCommand(intakeSubsystem),
      new ConveyorCommand(conveyorSubsystem, ()->-.75)
    )
=======
     //new TimedCommand(TurnToTargetCommand(turretSubsystem, lidar), 3000),
     new TurnToTargetCommand(turretSubsystem, lidar),

      new ParallelCommandGroup(
        new TimedCommand(new IndexToBlasterCommand(intakeSubsystem), 5000),
        new TimedCommand(new ConveyorCommand(conveyorSubsystem, ()->1), 5000)
      )
>>>>>>> e887a2d9761597a0efca33e497f0d9e85dd2cb0a
    );
  }
}
