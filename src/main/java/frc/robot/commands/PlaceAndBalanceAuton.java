// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Spinner;
import frc.robot.subsystems.Swerve;

public class PlaceAndBalanceAuton extends CommandBase {
  
  Timer timer;
  Swerve swerveSubsystem;
  AutonDrive driveToGoal, driveToStation;
  Spinner spinnerSubsystem;

  public PlaceAndBalanceAuton(Swerve swerveSubsystem, Spinner spinnerSubsystem) {
    this.timer = new Timer();
    this.swerveSubsystem = swerveSubsystem;
    this.spinnerSubsystem = spinnerSubsystem;
    driveToGoal = new AutonDrive(swerveSubsystem, 0, 0, 0, 0); //TODO: Determine these values
    driveToStation = new AutonDrive(swerveSubsystem, 0, 0, 0, 0); //TODO: Determine these values
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.restart();
    swerveSubsystem.stop();
    spinnerSubsystem.stop();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driveToGoal.schedule();
    timer.reset();
    if (!timer.hasElapsed(2)) spinnerSubsystem.shoot();
    else {
      spinnerSubsystem.stop();
      driveToStation.schedule();
    }
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    timer.stop();
    swerveSubsystem.stop();
    spinnerSubsystem.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
