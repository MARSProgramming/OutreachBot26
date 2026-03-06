package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants.MagazineConstants;

public class Magazine extends SubsystemBase {
  // Motor to move balls inside the robot
  private final TalonSRX m_magazineMotor = new TalonSRX(MagazineConstants.kMagazineMotorPort);
  
  // Infrared sensor to detect if a ball is present
  private final AnalogInput m_magIR = new AnalogInput(MagazineConstants.kMagazineIRPort);

  /** Creates a new Magazine. */
  public Magazine() {
    m_magazineMotor.configFactoryDefault();
    m_magazineMotor.setInverted(false);
  }

  /** Runs the magazine motor at a specific speed. */
  private void runMotor(double speed) {
    m_magazineMotor.set(TalonSRXControlMode.PercentOutput, speed);
  }

  /** Stops the magazine motor. */
  private void stopMotor() {
    m_magazineMotor.set(TalonSRXControlMode.PercentOutput, 0);
  }

  /** A command to run the magazine motor at a given speed. */
  public Command runCommand(double speed) {
    return Commands.startEnd(() -> runMotor(speed), this::stopMotor, this);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("magIR_Value", m_magIR.getAverageValue());
    SmartDashboard.putNumber("magMotorOutput", m_magazineMotor.getMotorOutputPercent());
  }
}