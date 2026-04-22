package frc.robot.constants;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 */
public final class Constants {
  /** Constants for the Operator Interface (OI), i.e., the controllers. */
  public static final class OIConstants {
    /** The USB port ID for the driver's controller. */
    public static final int kDriverControllerPort = 0;
    /**
     * The deadband threshold (0.0 to 1.0). Inputs smaller than this are ignored to prevent
     * unintended movement caused by joystick drift.
     */
    public static final double kDriveDeadband = 0.15;
  }

  /** Constants for the Drivetrain subsystem (motors and IDs). */
  public static final class DrivetrainConstants {
    /** CAN ID for the left master motor (TalonFX). */
    public static final int kLeftMasterPort = 6;
    /** CAN ID for the left follower motor (TalonFX). */
    public static final int kLeftFollowerPort = 7;
    /** CAN ID for the right master motor (TalonFX). */
    public static final int kRightMasterPort = 4;
    /** CAN ID for the right follower motor (TalonFX). */
    public static final int kRightFollowerPort = 5;
  }

  /** Constants for the Intake subsystem. */
  public static final class IntakeConstants {
    /** CAN ID for the intake motor (TalonSRX). */
    public static final int kIntakeMotorPort = 11;
    /** PCM port for the solenoid extension (forward). */
    public static final int kIntakeSolenoidForwardPort = 0;
    /** PCM port for the solenoid retraction (reverse). */
    public static final int kIntakeSolenoidReversePort = 1;
    /** The motor speed for intaking balls (0.0 to 1.0). */
    public static final double kIntakeSpeed = 0.75;
  }

  /** Constants for the Magazine (ball indexer) subsystem. */
  public static final class MagazineConstants {
    /** CAN ID for the magazine motor (TalonSRX). */
    public static final int kMagazineMotorPort = 10;
    /** Analog Input port for the Infrared (IR) sensor. */
    public static final int kMagazineIRPort = 1;
    /** Speed to run the magazine when pulling balls in from the intake. */
    public static final double kMagazineIntakeSpeed = 0.4;
    /** Speed to run the magazine when feeding balls into the shooter. */
    public static final double kMagazineShootSpeed = 0.8;
  }

  /** Constants for the Flywheel (Shooter) subsystem. */
  public static final class FlywheelConstants {
    /** CAN ID for the main flywheel motor (SparkMax). */
    public static final int kFlywheelMainPort = 1;
    /** CAN ID for the follower flywheel motor (SparkMax). */
    public static final int kFlywheelFollowerPort = 2;
    /** The target speed for the flywheel when shooting (0.0 to 1.0). */
    public static final double kFlywheelShootSpeed = 0.6;
    /** The available target speeds for the flywheel. */
    public static final double[] kFlywheelSpeeds = {0.6, 0.8, 1.0};
  }

  /** Constants for shooter logic and timing. */
  public static final class ShooterConstants {
    /** Time in seconds to wait for the flywheel to spin up before feeding a ball. */
    public static final double kFlywheelSpinUpDelay = 0.3; // seconds
  }
}