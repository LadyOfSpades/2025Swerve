package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;

public class SpinWheel extends Command {
    private Elevator elevator;
    private boolean forwards;
    public SpinWheel(Elevator elevator, boolean forwards){
        this.elevator = elevator;
    
        this.forwards = forwards;
    }

    public void initialize(){
        System.out.println("we're spinning <|:D");
    }

    public void execute(){
        if (forwards){
            elevator.setWheel(-.05);
        } else {
            elevator.setWheel(.05);
        }
    }

    public void end(boolean interrupted){
        elevator.setWheel(0);
    }

    public boolean isFinished(){
        return false;
    }
}
