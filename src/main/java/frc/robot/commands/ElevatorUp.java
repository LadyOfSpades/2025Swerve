package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Elevator;

public class ElevatorUp extends Command {

    Elevator elevator;
    Timer timer;
    double level;
    double rotations;
    SimpleMotorFeedforward simpleMotorFeedforward;
    PIDController pidController;
    

    public ElevatorUp(Elevator e, int l) {
        elevator = e;
        timer = new Timer();
        level = (double)l;
        rotations = level * 2;
        pidController = new PIDController(Constants.MotorPID.p, Constants.MotorPID.i, Constants.MotorPID.d);
    }

    @Override
    public void initialize() {
        timer.restart();
    }

    @Override
    public void execute() {
        elevator.setMotors(pidController.calculate(rotations));
    }

    @Override
    public boolean isFinished() {
        System.out.println("Time = "+timer.get());
        if (timer.get() > 3){
            elevator.setMotors(0);
            return true;
        }
        
        return false; // Change so it stops at some point
        
    }
    
}
