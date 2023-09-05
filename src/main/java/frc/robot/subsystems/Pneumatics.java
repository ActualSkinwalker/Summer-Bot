// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pneumatics extends SubsystemBase {
  
  DoubleSolenoid leftSolenoid, rightSolenoid;

  PneumaticsControlModule PCM;

  /**Creates a new Pneumatics subsystem with a PCM and two solenoids */
  public Pneumatics(int pcmID) {
    PCM = new PneumaticsControlModule(pcmID);

    leftSolenoid = PCM.makeDoubleSolenoid(1, 0); //TODO: Find channel values
    rightSolenoid = PCM.makeDoubleSolenoid(2, 3);
  }

  /**Runs the toggle() method on both solenoids.*/
  public void toggleSolenoids () {
    leftSolenoid.toggle();
    rightSolenoid.toggle();
  }

  public Value getState () {
    return leftSolenoid.get();
  }

  boolean leftState, rightState;
  public boolean changeState (Value state) {
    if (leftSolenoid.get() != state && leftSolenoid.get() != Value.kOff) {
      leftSolenoid.set(state);
      leftState = true;
    }
    else leftState = false;
    if (rightSolenoid.get() != state && rightSolenoid.get() != Value.kOff) {
       rightSolenoid.set(state);
       rightState = true;
    }
    else rightState = false;

    if (!leftState) return true;
    else return false;
  }

  /**Enables the subsystem. Returns true if the compressor was enabled, false if not. 
   * Note: compressor would only not be enabled if it already was.*/
  public boolean enableSubsystem() {
    if (leftSolenoid.get() == Value.kOff || leftSolenoid.get() == Value.kForward) leftSolenoid.set(Value.kReverse);
    if (rightSolenoid.get() == Value.kOff || rightSolenoid.get() == Value.kForward) rightSolenoid.set(Value.kReverse);
    if (!PCM.getCompressor()) {
    PCM.enableCompressorDigital();
    return true;
    }
    else return false;
  }

  /**Sets both solenoids to "off", then disables the compressor. */
  public void disableSubsystem () {
    leftSolenoid.set(Value.kReverse); //TODO: Find which channel extends/retracts to determine which direction is undeployed
    rightSolenoid.set(Value.kReverse);
    leftSolenoid.set(Value.kOff);
    rightSolenoid.set(Value.kOff);
    PCM.disableCompressor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
