package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LinearActuator extends SubsystemBase {
    private double MAX_EXTENSION_TIME = 7.0;
    private Spark actuatorController;
    private double time_already_extended;

    public LinearActuator() {
        actuatorController = new Spark(0);
    }
    public void set() {
        if (time_already_extended > MAX_EXTENSION_TIME) {
            actuatorController.set(0);
        }
        actuatorController.set(1);
    }
    public void addTime(double t) {
        time_already_extended = time_already_extended + t;
    }
    public boolean canExtend(double t) {
        if (t + time_already_extended > MAX_EXTENSION_TIME) {
            return false;
        }
        return true;
    }
    public void retract() {
        actuatorController.set(-1);
    }
    public void subtractTime(double t) {
        time_already_extended = Math.max(time_already_extended - t, 0);
    }
    public void stop() {
        actuatorController.set(0);
    }
}
