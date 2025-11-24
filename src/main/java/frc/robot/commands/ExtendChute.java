package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;

@Deprecated
public class ExtendChute extends Command{
    private Elevator elevator;
    private double speed;
    public ExtendChute(Elevator elevator, double speed){
        this.elevator = elevator;
        this.speed = speed;
        addRequirements(elevator);
    }

    public void initialize(){

    }

    public void execute(){
        elevator.setChute(speed);
    }

    public void end(boolean interrupted){
        elevator.setChute(0);
    }

    public boolean isFinished(){
        return false;
    }

}
