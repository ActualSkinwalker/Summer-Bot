// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Pneumatics;
// import frc.robot.subsystems.Spinner;

public class IntakeCommand extends CommandBase {
  //TODO: UNCOMMENT ALL SPINNER THINGS WHEN IT'S ADDED!!!!!!!!!
  Pneumatics pneumaticsSubsystem;

  // Spinner spinnerSubsystem;

  BooleanSupplier runIntake, runIntakeReleased;

  Timer timer;

  public IntakeCommand(Pneumatics pneumaticsSubsystem, /**Spinner spinnerSubsystem,*/ BooleanSupplier runIntake, BooleanSupplier runIntakeReleased) {
    this.pneumaticsSubsystem = pneumaticsSubsystem;
    // this.spinnerSubsystem = spinnerSubsystem;
    this.runIntake = runIntake;
    this.runIntakeReleased = runIntakeReleased;
    timer = new Timer();
    addRequirements(pneumaticsSubsystem/**, spinnerSubsystem*/);
  }

  // Called when the command is initially scheduled. 
  @Override
  public void initialize() {
    pneumaticsSubsystem.enableSubsystem();
    timer.restart();
    // spinnerSubsystem.stop();
  }

  boolean continueIntake;

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    /**If the BooleanSupplier returns a true value, run the toggleSolenoid()
     method in the pneumaticsSubsystem */
     
      if (runIntake.getAsBoolean() && pneumaticsSubsystem.getState() == Value.kReverse) {
        pneumaticsSubsystem.changeState(Value.kForward);
        //spinnerSubsystem.runSpinner();
      }
      else if (runIntakeReleased.getAsBoolean()) {
        pneumaticsSubsystem.changeState(Value.kReverse);
        //spinnerSubsystem.stop()
      }
      
    /**if (runIntake.getAsBoolean() && timer.get() > 6) {
      timer.reset();
      continueIntake = true;
    }   
    if (continueIntake) {
      if (!timer.hasElapsed(5)) { //TODO: discuss optimal running time with Andrew
        pneumaticsSubsystem.changeState(Value.kForward);
        // spinnerSubsystem.runSpinner();
      }
      else {
        pneumaticsSubsystem.changeState(Value.kReverse);
        // spinnerSubsystem.stop();
        continueIntake = false;
      }
    }

    if (cancelIntake.getAsBoolean()) {
      //spinnerSubsystem.stop();
      pneumaticsSubsystem.changeState(Value.kReverse);
    }
    */
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    pneumaticsSubsystem.disableSubsystem();
    // spinnerSubsystem.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
