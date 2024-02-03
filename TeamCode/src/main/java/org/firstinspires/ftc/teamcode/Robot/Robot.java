package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;

public class Robot implements Mechanism {
    public MecanumDrivetrain drivetrain;
    public Slider slider;
    public Arm arm;
    public Claw claw;
    public IMU imu;
    private OpMode opMode;

    public Robot (OpMode opMode){
        this.opMode = opMode;
    }

    @Override
    public void init() {
        imu = opMode.hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        imu.initialize(parameters);
        drivetrain = new MecanumDrivetrain(opMode, MecanumDrivetrain.DriveMode.FIELD_CENTERED, imu);
        drivetrain.init();
//        arm = new Arm(opMode);
//        arm.init();
//        claw = new Claw(opMode);
//        claw.init();
    }

    @Override
    public void keybind(Gamepad currGamepad, Gamepad prevGamepad) {

    }
}
