package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants.DrivetrainConstants;

public class Drivetrain extends SubsystemBase {
  // Hardware devices (Falcon 500 motors)
  private final TalonFX m_leftMaster = new TalonFX(DrivetrainConstants.kLeftMasterPort);
  private final TalonFX m_leftFollower = new TalonFX(DrivetrainConstants.kLeftFollowerPort);
  private final TalonFX m_rightMaster = new TalonFX(DrivetrainConstants.kRightMasterPort);
  private final TalonFX m_rightFollower = new TalonFX(DrivetrainConstants.kRightFollowerPort);

  // Control requests for setting motor output (Duty Cycle = Percent Output)
  private final DutyCycleOut m_leftOut = new DutyCycleOut(0);
  private final DutyCycleOut m_rightOut = new DutyCycleOut(0);

  /** Creates a new Drivetrain. */
  public Drivetrain() {
    // Configure Left Side
    var leftConfiguration = new TalonFXConfiguration();
    leftConfiguration.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
    m_leftMaster.getConfigurator().apply(leftConfiguration);
    m_leftFollower.getConfigurator().apply(leftConfiguration);

    // Configure Right Side (Inverted so the robot drives straight)
    var rightConfiguration = new TalonFXConfiguration();
    rightConfiguration.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
    m_rightMaster.getConfigurator().apply(rightConfiguration);
    m_rightFollower.getConfigurator().apply(rightConfiguration);

    // Set followers to mimic their masters
    m_leftFollower.setControl(new Follower(m_leftMaster.getDeviceID(), MotorAlignmentValue.Aligned));
    m_rightFollower.setControl(
        new Follower(m_rightMaster.getDeviceID(), MotorAlignmentValue.Aligned));

    // Enable safety to stop motors if communication is lost
    m_leftMaster.setSafetyEnabled(true);
    m_rightMaster.setSafetyEnabled(true);
  }

  /**
   * Drives the robot using arcade controls.
   *
   * @param fwd the commanded forward movement
   * @param rot the commanded rotation
   */
  public void arcadeDrive(double fwd, double rot) {
    m_leftOut.Output = (fwd + rot) * 0.15;
    m_rightOut.Output = (fwd - rot) * 0.15;
    m_leftMaster.setControl(m_leftOut);
    m_rightMaster.setControl(m_rightOut);
  }
}