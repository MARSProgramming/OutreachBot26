package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.constants.Constants.MagazineConstants;
import frc.robot.constants.Constants.OIConstants;
import frc.robot.constants.Constants.ShooterConstants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Magazine;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems are defined here.
  private final Drivetrain m_drivetrain = new Drivetrain();
  private final Intake m_intake = new Intake();
  private final Magazine m_magazine = new Magazine();
  private final Flywheel m_flywheel = new Flywheel();

  // The driver's controller (Xbox Controller).
  private final CommandXboxController controller = new CommandXboxController(OIConstants.kDriverControllerPort);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the default commands
    m_drivetrain.setDefaultCommand(
        // The default command for the drivetrain is to drive using the joysticks.
        // This runs whenever no other command is using the drivetrain.
        Commands.run(
            () -> m_drivetrain.arcadeDrive(
                // Apply deadband to ignore tiny joystick movements (drift)
                -MathUtil.applyDeadband(controller.getLeftY(), OIConstants.kDriveDeadband),
                MathUtil.applyDeadband(controller.getRightX(), OIConstants.kDriveDeadband)),
            m_drivetrain));

    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its
   * subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
   * it to a {@link
   * JoystickButton}.
   */
  private void configureButtonBindings() {
    // A Button: Toggle intake solenoid (Extend/Retract)
    controller.a().onTrue(m_intake.toggleCommand());

    // Left Trigger: Run intake and magazine, but only if the intake is deployed
    // "whileTrue" runs the command as long as the button is held, and cancels it
    // when released.
    controller.leftTrigger()
        .whileTrue(
            m_intake.runCommand()
                .onlyIf(m_intake::isDeployed));

    // Right Trigger: Spin up flywheel, wait, then feed balls
    // This uses a ParallelCommandGroup to run the flywheel AND the feeding sequence
    // at the same time.
    controller.rightTrigger()
        .whileTrue(
            new ParallelCommandGroup(
                m_flywheel.runCommand(),
                Commands.sequence(
                    Commands.waitSeconds(ShooterConstants.kFlywheelSpinUpDelay),
                    m_magazine.runCommand(MagazineConstants.kMagazineShootSpeed))));

    // X Button: Cycle shooter speed
    controller.x().onTrue(m_flywheel.changeSpeedCommand());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}