package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.constants.Constants.IntakeConstants;

public class Intake extends SubsystemBase {
  // Motor controller for the intake wheels
  private final TalonSRX m_intakeMotor = new TalonSRX(IntakeConstants.kIntakeMotorPort);
  
  // Pneumatic solenoid to extend/retract the intake
  private final DoubleSolenoid m_intakeSolenoid =
      new DoubleSolenoid(
          PneumaticsModuleType.CTREPCM,
          IntakeConstants.kIntakeSolenoidForwardPort,
          IntakeConstants.kIntakeSolenoidReversePort);
          
  // Air compressor
  private final Compressor m_compressor = new Compressor(PneumaticsModuleType.CTREPCM);

  /** Creates a new Intake. */
  public Intake() {
    m_intakeMotor.configFactoryDefault();
    m_intakeMotor.setInverted(false);
    m_intakeSolenoid.set(Value.kReverse); // Start retracted
  }

  /** Runs the intake motor at the constant speed. */
  private void runMotor() {
    m_intakeMotor.set(TalonSRXControlMode.PercentOutput, IntakeConstants.kIntakeSpeed);
  }

  /** Stops the intake motor. */
  private void stopMotor() {
    m_intakeMotor.set(TalonSRXControlMode.PercentOutput, 0);
  }

  /** Toggles the solenoid state (In <-> Out). */
  private void toggleSolenoid() {
    m_intakeSolenoid.toggle();
  }

  /** A command to run the intake motor. */
  public Command runCommand() {
    return Commands.startEnd(this::runMotor, this::stopMotor, this);
  }

  /** A command to toggle the intake solenoid. */
  public Command toggleCommand() {
    return this.runOnce(this::toggleSolenoid);
  }

  /** Returns true if the intake is currently extended (Forward). */
  public boolean isDeployed() {
    return m_intakeSolenoid.get() == Value.kForward;
  }

  /** Returns the compressor object for diagnostics. */
  public Compressor getCompressor() {
    return m_compressor;
  }

  @Override
  public void periodic() {
    // The compressor will automatically run when pressure is low.
    SmartDashboard.putBoolean("Compressor Enabled", m_compressor.isEnabled());
    SmartDashboard.putBoolean("Pressure Switch", m_compressor.getPressureSwitchValue());
  }
}