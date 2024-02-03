package org.firstinspires.ftc.teamcode.Robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Arm implements Mechanism {
    private Servo arm1, arm2;
    private OpMode opMode;
    private double y;
    public static double MAX1 = 1, MAX2 = 1, SCALE = .1;

    public Arm(OpMode opMode){
        this.opMode = opMode;
    }

    @Override
    public void init() {
        arm1 = opMode.hardwareMap.get(Servo.class, "arm1");
        arm2 = opMode.hardwareMap.get(Servo.class, "arm2");
        arm1.setDirection(Servo.Direction.FORWARD);
        arm2.setDirection(Servo.Direction.REVERSE);
        arm1.setPosition(0);
        arm2.setPosition(0);
    }

    @Override
    public void keybind(Gamepad currGamepad, Gamepad prevGamepad) {
        y = -currGamepad.right_stick_y * SCALE;
        arm1.setPosition(arm1.getPosition() + y);
        arm2.setPosition(arm1.getPosition() + y);
    }

    public double getServo1Position(){
        return arm1.getPosition();
    }

    public double getServo2Position(){
        return arm2.getPosition();
    }

    public double getServoPower(){
        return y;
    }
}
