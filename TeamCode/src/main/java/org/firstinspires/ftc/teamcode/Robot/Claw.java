package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw implements Mechanism{

    Servo claw1, claw2, ankle;

    @Override
    public void init(HardwareMap hardwareMap) {

        claw1 = hardwareMap.get(Servo.class, "claw1");
        claw2 = hardwareMap.get(Servo.class, "claw2");
        ankle = hardwareMap.get(Servo.class, "ankle");

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
