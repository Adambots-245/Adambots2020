/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.autonCommands;

import java.util.Date;
import java.util.Timer;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TimedCommand extends CommandBase {
  /**
   * Creates a new TimedCommand.
   */
  Date timer;
  long startTime;
  Command command;
  long timeInMilliseconds;
  public TimedCommand(Command command, long timeInMilliseconds) {
    // Use addRequirements() here to declare subsystem dependencies.
    timer = new Date();
    this.command = command;
    this.timeInMilliseconds = timeInMilliseconds;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startTime = timer.getTime();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    command.schedule();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    command.end(true);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return ((timer.getTime()-startTime) >= timeInMilliseconds);
  }
}
