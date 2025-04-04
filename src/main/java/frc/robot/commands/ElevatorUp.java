package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.subsystems.Elevator;

public class ElevatorUp extends Command {

    Elevator elevator;
    Timer timer;
    int level;
    double rotations;
    SimpleMotorFeedforward simpleMotorFeedforward;
    PIDController pidController;
    double targetPos = 0;
    CommandXboxController controller;

    public ElevatorUp(Elevator e, int level, CommandXboxController controller) {
        elevator = e;
        this.level = level;
        timer = new Timer();
        rotations = e.rightEncOffset;
        pidController = new PIDController(Constants.MotorPID.p, Constants.MotorPID.i, Constants.MotorPID.d);
        this.controller = controller;
        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        switch (level) {
            case 0:
                elevator.setPosition(Constants.ELEVATOR_LEVEL_0);
                break;
            case 1:
                elevator.setPosition(Constants.ELEVATOR_LEVEL_1);
                break;
            case 2:
                elevator.setPosition(Constants.ELEVATOR_LEVEL_2);
                break;
            case 3:
                elevator.setPosition(Constants.ELEVATOR_LEVEL_3);
                break;
        }
        timer.restart();
    }

    @Override
    public void execute() {
       // rotations = rotations + MathUtil.applyDeadband(controller.getRightY() * -1, OperatorConstants.LEFT_Y_DEADBAND) * .25;
        //elevator.setPosition(rotations);
        //System.out.println("elevator set to: " + rotations);
        //System.out.println("joystick: " + (MathUtil.applyDeadband(controller.getRightY() * -1, OperatorConstants.LEFT_Y_DEADBAND) * .25));
        //System.out.println("current elevator pos: " + elevator.getEncoderValue());
    }

    @Override
    public boolean isFinished() {

        return true; // Change so it stops at some point
        
    }
    
}
