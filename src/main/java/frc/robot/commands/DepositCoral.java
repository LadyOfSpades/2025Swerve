package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.Elevator;

public class DepositCoral extends SpinWheel {
    Timer timer;
    public DepositCoral(Elevator e) {
        super(e, true);
        timer = new Timer();
    }

    public void initialize() {
        super.initialize();
        timer.restart();
    }

    public boolean isFinished() {
        return timer.get() > 1;
    }
    
}
