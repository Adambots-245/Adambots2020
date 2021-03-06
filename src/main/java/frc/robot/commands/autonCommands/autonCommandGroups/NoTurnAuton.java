/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.autonCommands.autonCommandGroups;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.BlasterDistanceBasedCommand;
import frc.robot.commands.ConveyorCommand;
import frc.robot.commands.IndexToBlasterCommand;
import frc.robot.commands.LowerIntakeArmCommand;
import frc.robot.commands.autonCommands.DriveForwardGyroDistanceCommand;
import frc.robot.commands.autonCommands.TimedBlasterDistanceBasedCommand;
import frc.robot.subsystems.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class NoTurnAuton extends SequentialCommandGroup {
  /**
   * Creates a new NoTurnAuton.
   */
  
  public NoTurnAuton(TurretSubsystem turretSubsystem, DriveTrainSubsystem driveTrainSubsystem, ConveyorSubsystem conveyorSubsystem, IntakeSubsystem intakeSubsystem, LidarSubsystem lidarSubsystem, BlasterSubsystem blasterSubsystem, XboxController joystick) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
      new LowerIntakeArmCommand(intakeSubsystem),
      new DriveForwardGyroDistanceCommand(driveTrainSubsystem, Constants.AUTON_DRIVE_OFF_LINE_DISTANCE, Constants.AUTON_DRIVE_OFF_LINE_SPEED, 0, true),
      
      new TimedBlasterDistanceBasedCommand(blasterSubsystem, lidarSubsystem, 2000),
      
      new ParallelCommandGroup(
        new BlasterDistanceBasedCommand(blasterSubsystem, lidarSubsystem, joystick),
        new IndexToBlasterCommand(intakeSubsystem),
        new ConveyorCommand(conveyorSubsystem, ()->-0.7)
      )
      // new ParallelCommandGroup(
        // TEST AUTON DRIVE OFF LINE DISTANCE!!!!!!!!!!!!!!!!!!!!
    //     new TimedManualTurretCommand(turretSubsystem, () -> 0, () -> 1, 2000),
    //   // ),

    // //  new TimedCommand(new TurnToTargetCommand(turretSubsystem, lidarSubsystem), 3000),
    // new TurnToTargetCommand(turretSubsystem, lidarSubsystem),
    
    );
  }
}
