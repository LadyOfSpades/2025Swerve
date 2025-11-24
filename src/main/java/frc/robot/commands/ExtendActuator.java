package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LinearActuator;


public class ExtendActuator extends Command {
    private LinearActuator linearActuator;
    private Timer timer;
    public ExtendActuator(LinearActuator la) {
        linearActuator = la;
        timer = new Timer();
        timer.reset();
    }    

    @Override
    public void initialize() {
        timer.restart();
    }

    @Override
    public boolean isFinished() {
        if (linearActuator.canExtend(timer.get())) {
            return false;
        }
        return true;
    }

    @Override
    public void execute() {
        linearActuator.set();
    }

    @Override
    public void end(boolean interrupted) {
        linearActuator.addTime(timer.get());
        linearActuator.stop();
    }
}
