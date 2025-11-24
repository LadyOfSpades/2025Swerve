package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LinearActuator;

public class RetractActuator extends Command {
    private LinearActuator linearActuator;
    private Timer timer;
    public RetractActuator(LinearActuator la) {
        linearActuator = la;
        timer = new Timer();
        timer.reset();
    }
    @Override
    public void initialize() {
        timer.restart();
    }
    @Override
    public void execute() {
        linearActuator.retract();
    }
    @Override
    public void end(boolean interrupted) {
        linearActuator.subtractTime(timer.get());
        linearActuator.stop();
    }
}
