package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Test extends OpMode {

    DcMotor motor;

    @Override
    public void init() {

        motor = hardwareMap.get(DcMotor.class, "motor")

    }

    @Override
    public void loop() {

        motor.setPower(gamepad1.left_stick_y);

    }
}
