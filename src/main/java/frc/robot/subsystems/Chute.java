package frc.robot.subsystems;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Chute extends SubsystemBase {
    private SparkFlex chuteMotor;

    public Chute(){
        chuteMotor = new SparkFlex(11, MotorType.kBrushless);
    }

    public void setMotor(double speed){
        chuteMotor.set(speed);
    }
}
