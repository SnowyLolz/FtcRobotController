package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw implements Mechanism{
    private Servo claw1, claw2, ankle;
    private OpMode opMode;

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
    public void keybind(Gamepad gamepad) {

    }

    @Override
    public void run() {

    }
}
