package frc.robot.subsystems;

import com.revrobotics.spark.SparkAbsoluteEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {

    private SparkFlex elevatorMotorLeft;
    private SparkFlex elevatorMotorRight;
    private SparkAbsoluteEncoder leftEnc;
    private SparkAbsoluteEncoder rightEnc;
    
    private double leftEncOffset;
    private double rightEncOffset;
    

    public Elevator() {
        elevatorMotorLeft = new SparkFlex(10, MotorType.kBrushless);
        elevatorMotorRight = new SparkFlex(9, MotorType.kBrushless);
        leftEnc = elevatorMotorLeft.getAbsoluteEncoder();
        rightEnc = elevatorMotorRight.getAbsoluteEncoder();
        zeroEncoders();
    }

    public void zeroEncoders() {
        leftEncOffset = leftEnc.getPosition();
        rightEncOffset = rightEnc.getPosition();
    }

    public void setMotors(double speed) {
        elevatorMotorLeft.set(speed);
    }

    public double getEncoderValue() {
        return 0.0;
    }

    @Override
    public void periodic() {
        System.out.println(elevatorMotorLeft.get());
    }
    
}
