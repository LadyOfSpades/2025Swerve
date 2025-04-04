// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.DepositCoral;
import frc.robot.commands.ElevatorUp;
import frc.robot.commands.SpinWheel;
import frc.robot.commands.ExtendChute;
import frc.robot.commands.ReadyChute;
import frc.robot.commands.swervedrive.Forward;
import frc.robot.commands.swervedrive.auto.AutoForward;
import frc.robot.commands.swervedrive.drivebase.AbsoluteDriveAdv;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import java.io.File;

import com.pathplanner.lib.auto.NamedCommands;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very
 * little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer
{
  

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController driverXbox = new CommandXboxController(0);
  private final CommandXboxController shooterXbox = new CommandXboxController(1);
  // The robot's subsystems and commands are defined here...
  private final SwerveSubsystem       drivebase  = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),
                                                                                "swerve/neo"));
  private final Elevator elevator = new Elevator();
  private final ElevatorUp up0 = new ElevatorUp(elevator, 0, shooterXbox);
  private final ElevatorUp up1 = new ElevatorUp(elevator, 1, shooterXbox);
  private final ElevatorUp up2 = new ElevatorUp(elevator, 2, shooterXbox);
  private final ElevatorUp up3 = new ElevatorUp(elevator, 3, shooterXbox);
  private final SpinWheel spinWheel = new SpinWheel(elevator, true);
  private final SpinWheel spinWheelBackwards = new SpinWheel(elevator, false);
  private final ExtendChute extendChute = new ExtendChute(elevator, 1);
  private final ExtendChute retractChute = new ExtendChute(elevator, -1);
  // Applies deadbands and inverts controls because joysticks
  // are back-right positive while robot
  // controls are front-left positive
  // left stick controls translation
  // right stick controls the rotational velocity 
  // buttons are quick rotation positions to different ways to face
  // WARNING: default buttons are on the same buttons as the ones defined in configureBindings
  AbsoluteDriveAdv closedAbsoluteDriveAdv = new AbsoluteDriveAdv(drivebase,
                                                                 () -> -MathUtil.applyDeadband(driverXbox.getLeftY(),
                                                                                               OperatorConstants.LEFT_Y_DEADBAND),
                                                                 () -> -MathUtil.applyDeadband(driverXbox.getLeftX(),
                                                                                               OperatorConstants.LEFT_X_DEADBAND),
                                                                 () -> -MathUtil.applyDeadband(driverXbox.getRightX(),
                                                                                               OperatorConstants.RIGHT_X_DEADBAND),
                                                                 driverXbox.getHID()::getYButtonPressed,
                                                                 driverXbox.getHID()::getAButtonPressed,
                                                                 driverXbox.getHID()::getXButtonPressed,
                                                                 driverXbox.getHID()::getBButtonPressed, 
                                                                 elevator, 
                                                                 () -> -MathUtil.applyDeadband(shooterXbox.getRightY(),
                                                                                               OperatorConstants.RIGHT_X_DEADBAND),
                                                                () -> MathUtil.applyDeadband(shooterXbox.getLeftY(), OperatorConstants.LEFT_Y_DEADBAND));

  // Applies deadbands and inverts controls because joysticks
  // are back-right positive while robot
  // controls are front-left positive
  // left stick controls translation
  // right stick controls the desired angle NOT angular rotation
  Command driveFieldOrientedDirectAngle = drivebase.driveCommand(
      () -> MathUtil.applyDeadband(driverXbox.getLeftY(), OperatorConstants.LEFT_Y_DEADBAND),
      () -> MathUtil.applyDeadband(driverXbox.getLeftX(), OperatorConstants.LEFT_X_DEADBAND),
      () -> driverXbox.getRightX(),
      () -> driverXbox.getRightY());

  // Applies deadbands and inverts controls because joysticks
  // are back-right positive while robot
  // controls are front-left positive
  // left stick controls translation
  // right stick controls the angular velocity of the robot
  Command driveFieldOrientedAnglularVelocity = drivebase.driveCommand(
      () -> MathUtil.applyDeadband(driverXbox.getLeftY() * -1, OperatorConstants.LEFT_Y_DEADBAND),
      () -> MathUtil.applyDeadband(driverXbox.getLeftX() * -1, OperatorConstants.LEFT_X_DEADBAND),
      () -> driverXbox.getRightX() * -1);
/*
  Command driveFieldOrientedDirectAngleSim = drivebase.simDriveCommand(
      () -> MathUtil.applyDeadband(driverXbox.getLeftY(), OperatorConstants.LEFT_Y_DEADBAND),
      () -> MathUtil.applyDeadband(driverXbox.getLeftX(), OperatorConstants.LEFT_X_DEADBAND),
      () -> driverXbox.getRawAxis(2));

  Command driveSetpointGenSim = drivebase.simDriveCommand(
      () -> MathUtil.applyDeadband(driverXbox.getLeftY(), OperatorConstants.LEFT_Y_DEADBAND),
      () -> MathUtil.applyDeadband(driverXbox.getLeftX(), OperatorConstants.LEFT_X_DEADBAND),
      () -> driverXbox.getRawAxis(2));*/

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer()
  {
    NamedCommands.registerCommand("DepositCoral", new DepositCoral(elevator));
    NamedCommands.registerCommand("ReadyChute", new ReadyChute(elevator));
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary predicate, or via the
   * named factories in {@link edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller PS4}
   * controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight joysticks}.
   */
  private void configureBindings()
  {
    if (Robot.isSimulation())
    {
      driverXbox.start().onTrue(Commands.runOnce(() -> drivebase.resetOdometry(new Pose2d(3, 3, new Rotation2d()))));
    }
    if (DriverStation.isTest())
    {/*
      driverXbox.b().whileTrue(drivebase.sysIdDriveMotorCommand());
      driverXbox.x().whileTrue(Commands.runOnce(drivebase::lock, drivebase).repeatedly());
      driverXbox.y().whileTrue(drivebase.driveToDistanceCommand(1.0, 0.2));
      driverXbox.start().onTrue((Commands.runOnce(drivebase::zeroGyro)));
      driverXbox.back().whileTrue(drivebase.centerModulesCommand());
      driverXbox.leftBumper().onTrue(Commands.none());
      driverXbox.rightBumper().onTrue(Commands.none());*/
      driverXbox.a().whileTrue(new Forward(drivebase));
      driverXbox.start().onTrue((Commands.runOnce(drivebase::zeroGyro)));
      drivebase.setDefaultCommand(closedAbsoluteDriveAdv);
    } else
    {
      //driverXbox.a().onTrue((Commands.runOnce(drivebase::zeroGyro)));
      driverXbox.start().onTrue((Commands.runOnce(drivebase::zeroGyro)));
      //driverXbox.x().onTrue(Commands.runOnce(drivebase::addFakeVisionReading));
      //driverXbox.b().whileTrue(
      //    drivebase.driveToPose(
      //        new Pose2d(new Translation2d(4, 4), Rotation2d.fromDegrees(0)))
     //                         );
      /* 
      driverXbox.y().onTrue(new ElevatorUp(elevator, 0, driverXbox));
      driverXbox.b().onTrue(new ElevatorUp(elevator, 1, driverXbox));
      driverXbox.a().onTrue(new ElevatorUp(elevator, 2, driverXbox));
      driverXbox.x().onTrue(new ElevatorUp(elevator, 3, driverXbox));      //driverXbox.start().whileTrue(Commands.none());
      */
      shooterXbox.y().onTrue(up0);
      shooterXbox.b().onTrue(up1);
      shooterXbox.a().onTrue(up2);
      shooterXbox.x().onTrue(up3);
      shooterXbox.rightBumper().whileTrue(extendChute);
      shooterXbox.leftBumper().whileTrue(retractChute);
      shooterXbox.start().onTrue((Commands.runOnce(elevator::zeroEncoders)));
      shooterXbox.back().onTrue((Commands.runOnce(elevator::printPos)));
     // driverXbox.back().whileTrue(Commands.none());
      //driverXbox.leftBumper().whileTrue(Commands.runOnce(drivebase::lock, drivebase).repeatedly());
      //driverXbox.rightBumper().onTrue(Commands.none());
      //driverXbox.y().whileTrue(new Forward(drivebase));
      drivebase.setDefaultCommand(closedAbsoluteDriveAdv);

    }
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand()
  {
    // An example command will be run in autonomous
    return new AutoForward(drivebase, elevator);
    //return new AutoForeward(drivebase);
    //return new Forward(drivebase);
  }

  public void setDriveMode()
  {
    configureBindings();
  }

  public void setMotorBrake(boolean brake)
  {
    drivebase.setMotorBrake(brake);
  }
}
