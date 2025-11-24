package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkAbsoluteEncoder;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkRelativeEncoder;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {

    private SparkFlex elevatorMotorLeft;
    private SparkFlex elevatorMotorRight;
    private SparkFlex wheelMotor;
    private SparkAbsoluteEncoder leftEnc;
    private RelativeEncoder rightEnc;
    private SparkClosedLoopController closedLoopController;
    //private Spark chuteMotor;
    
    private double leftEncOffset;
    public double rightEncOffset;
    private SparkFlexConfig motorConfig;
    private double elevatorPos = 0;
    

    public Elevator() {
        elevatorMotorLeft = new SparkFlex(10, MotorType.kBrushless);
        elevatorMotorRight = new SparkFlex(9, MotorType.kBrushless);
        wheelMotor = new SparkFlex(11, MotorType.kBrushless);
        leftEnc = elevatorMotorLeft.getAbsoluteEncoder();
        rightEnc = elevatorMotorRight.getEncoder();
        //chuteMotor = new Spark(0);
        zeroEncoders();
        closedLoopController = elevatorMotorRight.getClosedLoopController();
        elevatorPos = rightEncOffset;

        motorConfig = new SparkFlexConfig();
        motorConfig.encoder
        .positionConversionFactor(1)
        .velocityConversionFactor(1);
        motorConfig.closedLoop
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        // Set PID values for position control. We don't need to pass a closed loop
        // slot, as it will default to slot 0.
        .p(0.1)
        .i(0)
        .d(0)
        .outputRange(-1, 1)
        // Set PID values for velocity control in slot 1
        .p(0.0001, ClosedLoopSlot.kSlot1)
        .i(0, ClosedLoopSlot.kSlot1)
        .d(0, ClosedLoopSlot.kSlot1)
        .velocityFF(1.0 / 5767, ClosedLoopSlot.kSlot1)
        .outputRange(-1, 1, ClosedLoopSlot.kSlot1);
        elevatorMotorRight.configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    }

    public void zeroEncoders() {
        leftEncOffset = leftEnc.getPosition();
        rightEncOffset = rightEnc.getPosition();
        elevatorPos = rightEncOffset;
    }

    public void setPosition(double position) {
        elevatorPos = position + rightEncOffset;
        closedLoopController.setReference(position, ControlType.kPosition, ClosedLoopSlot.kSlot0);
    }

    public void changePos(double joystickInput){
        elevatorPos = Math.min(rightEncOffset + 1, Math.max(rightEncOffset - 17, elevatorPos + joystickInput));
        closedLoopController.setReference(elevatorPos, ControlType.kPosition, ClosedLoopSlot.kSlot0);
        System.out.println("elevator pos: " + elevatorPos);
    }

    public void printPos(){
        System.out.println("elevator pos: " + elevatorPos + ", encoder: " + rightEnc.getPosition());
    }

    public void setWheel(double speed){
        wheelMotor.set(speed);
    }

    public double getEncoderValue() {
        return rightEnc.getPosition();
    }

    public void setChute(double speed){
        //chuteMotor.set(speed);
    }

    @Override
    public void periodic() {
        //System.out.println(elevatorMotorRight.get());
    }
    
}
