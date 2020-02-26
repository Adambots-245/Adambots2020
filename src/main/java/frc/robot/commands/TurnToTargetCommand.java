/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.TurretSubsystem;

public class TurnToTargetCommand extends CommandBase {
  private TurretSubsystem turretSubsystem;
  /**
   * Creates a new TurnToTargetCommand.
   */
  public TurnToTargetCommand(TurretSubsystem turretSubsystem) {
    this.turretSubsystem = turretSubsystem;
    addRequirements(turretSubsystem);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    turretSubsystem.enable();

    System.out.println("Turret Initialized");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //turretSystem PID loop should deal with movement
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("Auto Turret System Ended");

    if (interrupted) 
      System.out.println("Turret System Interrupted");
      
    turretSubsystem.stopTurret();
    turretSubsystem.disable();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return turretSubsystem.atSetpoint();
  }
}
