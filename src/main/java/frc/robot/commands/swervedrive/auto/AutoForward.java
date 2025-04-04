package frc.robot.commands.swervedrive.auto;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;

public class AutoForward extends Command {
    private SwerveSubsystem swerveSubsystem;
    private Timer timer;
    private Translation2d forwardTranslation2d = new Translation2d(-1, 0);
    private Translation2d stopTranslation2d = new Translation2d(0, 0);
    private Elevator elevator;

    public AutoForward(SwerveSubsystem subsystem, Elevator elevator) {
        swerveSubsystem = subsystem;
        timer = new Timer();
        addRequirements(swerveSubsystem);
        this.elevator = elevator;
    }

    @Override
    public void initialize() {
        timer.restart();
        System.out.println("Initialized auto forward");
        
    }

    @Override
    public void execute() {
        System.out.println("Executing time = "+timer.get()+","+forwardTranslation2d.getX()+","+forwardTranslation2d.getY());
        if (timer.get()<2){
            swerveSubsystem.drive(new Translation2d(0.5, 0), 0, true);
        } else {
            swerveSubsystem.drive(new Translation2d(0, 0), 0, true);
        }
        elevator.setChute(1);
    }

    @Override
    public boolean isFinished() {
        return (timer.get() > 5);
    }

    @Override
    public void end(boolean interrupted) {
        swerveSubsystem.drive(new Translation2d(0, 0), 0, true);
        elevator.setChute(0);
        System.out.println("Ended auto forward");
    }
} 
