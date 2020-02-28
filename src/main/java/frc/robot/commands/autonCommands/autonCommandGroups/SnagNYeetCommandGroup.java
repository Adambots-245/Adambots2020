/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.autonCommands.autonCommandGroups;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.*;
import frc.robot.commands.autonCommands.*;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LidarSubsystem;
import frc.robot.subsystems.TurretSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class SnagNYeetCommandGroup extends SequentialCommandGroup {
  /**
   * Creates a new SnagNYeetCommandGroup.
   */
  public SnagNYeetCommandGroup(DriveTrainSubsystem driveTrainSubsystem, IntakeSubsystem intakeSubsystem, TurretSubsystem turretSubsystem, LidarSubsystem lidarSubsystem) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
    new ParallelDeadlineGroup( // deadline because it should move on after it has reached the position
      new DriveForwardGyroDistanceCommand(driveTrainSubsystem, Constants.SNAG_N_YEET_DISTANCE_TO_TRENCH, .75, 0, true),
      // PARALLEL COMMANDS CANNOT REQUIRE THE SAME SUBSYSTEM, THE FOLLOWING IS INCORRECT
      new LowerIntakeArmCommand(intakeSubsystem),
      new StartIntakeCommand(intakeSubsystem, ()->1.0)
    ),
    // the shield generator is offset by 22.5 degrees from the guardrails of the field
    new ParallelDeadlineGroup( // deadline because it should move on after it has reached the position
    new DriveForwardGyroDistanceCommand(driveTrainSubsystem, Constants.SNAG_N_YEET_DISTANCE_ACROSS_FIELD, -.75, 90-22.5, false), 
    new TurnToTargetCommand(turretSubsystem, lidarSubsystem)
    // new SetBlasterVelocity(shooterSubsys),
    ),
    new ParallelCommandGroup(
    new TurnToTargetCommand(turretSubsystem, lidarSubsystem)
    // new SetBlasterVelocity(shooterSubsys),
    // it should break and continue on when ^ the blaster is at the right velocity
    ),
    new ParallelCommandGroup(
      new TurnToTargetCommand(turretSubsystem, lidarSubsystem),
      // new SetBlasterVelocity(shooterSubsys),
      new IndexToBlasterCommand(intakeSubsystem)
    )
    );
  }
}
