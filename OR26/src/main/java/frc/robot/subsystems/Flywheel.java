package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.constants.Constants.FlywheelConstants;

public class Flywheel extends SubsystemBase {
  // Main motor (Right side)
  private final SparkMax m_flywheelMain =
      new SparkMax(FlywheelConstants.kFlywheelMainPort, MotorType.kBrushless);
  // Follower motor (Left side)
  private final SparkMax m_flywheelFollower =
      new SparkMax(FlywheelConstants.kFlywheelFollowerPort, MotorType.kBrushless);

  /** Creates a new Flywheel. */
  public Flywheel() {
    // Create configuration objects for the SparkMax motors
    SparkMaxConfig mainConfig = new SparkMaxConfig();
    mainConfig.idleMode(IdleMode.kCoast);

    SparkMaxConfig followerConfig = new SparkMaxConfig();
    followerConfig.idleMode(IdleMode.kCoast);
    // Tell the follower to mimic the main motor, but inverted (since they face opposite directions)
    followerConfig.follow(FlywheelConstants.kFlywheelMainPort, true);

    // Apply the configurations to the motors
    m_flywheelMain.configure(
        mainConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    m_flywheelFollower.configure(
        followerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }
  
  /** Runs the flywheel at the shooting speed. */
  private void runMotor() {
    m_flywheelMain.set(FlywheelConstants.kFlywheelShootSpeed);
  }

  /** Stops the flywheel. */
  private void stopMotor() {
    m_flywheelMain.set(0);
  }

  /** A command to run the flywheel motor. */
  public Command runCommand() {
    return Commands.startEnd(this::runMotor, this::stopMotor, this);
  }
}