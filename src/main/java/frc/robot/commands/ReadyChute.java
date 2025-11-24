package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.Elevator;

@Deprecated
public class ReadyChute extends ExtendChute {
    Timer timer;
    public ReadyChute(Elevator elevator){
        super(elevator, 1);
        timer = new Timer();
    }

    public void initialize(){
        super.initialize();
        timer.restart();
    }

    public boolean isFinished(){
        return timer.get() > 5;
    }
}
