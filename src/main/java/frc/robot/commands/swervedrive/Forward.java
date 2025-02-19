package frc.robot.commands.swervedrive;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;

public class Forward extends Command {

    SwerveSubsystem swerveSubsystem;
    
    public Forward(SwerveSubsystem subsystem) {
        swerveSubsystem = subsystem;
    }
    public void execute() {
        swerveSubsystem.drive(new Translation2d(0.5, 0), 0, false);
    }
}
