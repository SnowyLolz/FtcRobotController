package org.firstinspires.ftc.teamcode.Robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Claw implements Mechanism{
    private Servo claw1, claw2, ankle;
    private OpMode opMode;
    private boolean clawToggle = false;
    private double ly, ry;
    public static double OPEN1 = 0, OPEN2 = 0,
            CLOSED1 = 1, CLOSED2 = 1,
            ANKLE_MIN = 0, ANKLE_MAX = 1, ANKLE_SCALE = .1;

    public Claw(OpMode opMode){
        this.opMode = opMode;
    }

    @Override
    public void init() {
        claw1 = opMode.hardwareMap.get(Servo.class, "claw1");
        claw2 = opMode.hardwareMap.get(Servo.class, "claw2");
        ankle = opMode.hardwareMap.get(Servo.class, "ankle");
        setDirections(Servo.Direction.FORWARD, claw1, claw2, ankle);
        claw1.setPosition(0);
        claw2.setPosition(0);
    }

    @Override
    public void keybind(Gamepad currGamepad, Gamepad prevGamepad) {
        if(currGamepad.left_bumper && !prevGamepad.left_bumper){
            claw1.setPosition(CLOSED1);
            claw2.setPosition(CLOSED2);
        }
        if(currGamepad.right_bumper && !prevGamepad.right_bumper){
            claw1.setPosition(OPEN1);
            claw2.setPosition(OPEN2);
        }
//        if(currGamepad.right_bumper && !prevGamepad.right_bumper)
//            clawToggle = !clawToggle;
//        if(clawToggle){
//            claw1.setPosition(CLOSED1);
//            claw2.setPosition(CLOSED2);
//        }
//        else {
//            claw1.setPosition(OPEN1);
//            claw2.setPosition(OPEN2);
//        }
        ly = currGamepad.left_trigger * ANKLE_SCALE;
        ry = currGamepad.right_trigger * ANKLE_SCALE;
        if(ankle.getPosition() > ANKLE_MAX)
            ankle.setPosition(ankle.getPosition() - ly);
        if(ankle.getPosition() < ANKLE_MIN)
            ankle.setPosition(ankle.getPosition() + ry);
        else
            ankle.setPosition((ankle.getPosition() + ry - ly));
    }

    public double getClaw1Min(){
        return CLOSED1;
    }

    public double getClaw2Min(){
        return CLOSED2;
    }

    public double getClaw1Max(){
        return OPEN1;
    }

    public double getClaw2Max(){
        return OPEN2;
    }

    public double getClaw1Pos(){
        return claw1.getPosition();
    }

    public double getClaw2Pos(){
        return claw2.getPosition();
    }

    public double getAnkleMin(){
        return ANKLE_MIN;
    }

    public double getAnkleMax(){
        return ANKLE_MAX;
    }

    public double getAnkleScale(){
        return ANKLE_SCALE;
    }

    public double getAnklePos(){
        return ankle.getPosition();
    }

    public String getClawState(){
        return clawToggle ? "Open" : "Closed";
    }

    public double getRightTrigger(){
        return ry;
    }

    public double getLeftTrigger(){
        return -ly;
    }
}
