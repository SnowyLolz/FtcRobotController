package org.firstinspires.ftc.teamcode.Robot;


import android.graphics.Path;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot implements Mechanism {

    private OpMode opMode;

    public MecanumDrivetrain drivetrain;
    public Arm arm;
    public Claw claw;
    public BNO055IMU imu;

    public Robot (OpMode opMode){

        this.opMode = opMode;

    }


    @Override
    public void init(HardwareMap hardwareMap) {

        drivetrain = new MecanumDrivetrain();
        arm = new Arm();
        claw = new Claw();

        drivetrain.init(opMode.hardwareMap);
        arm.init(opMode.hardwareMap);
        claw.init(opMode.hardwareMap);
    }

    @Override
    public void keybind(Gamepad gamepad) {

    }

    @Override
    public void run() {

    }
}
