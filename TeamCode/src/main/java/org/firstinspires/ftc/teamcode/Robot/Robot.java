package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

public class Robot implements Mechanism {
    public MecanumDrivetrain drivetrain;
    public Arm arm;
    public Claw claw;
    public BNO055IMU imu;
    private OpMode opMode;

    public Robot (OpMode opMode){
        this.opMode = opMode;
    }

    @Override
    public void init() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = opMode.hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        drivetrain = new MecanumDrivetrain(opMode, MecanumDrivetrain.DriveMode.FIELD_CENTERED, imu);
        drivetrain.init();
//        arm = new Arm(opMode);
//        arm.init();
//        claw = new Claw(opMode);
//        claw.init();
    }

    @Override
    public void keybind(Gamepad gamepad) {

    }

    @Override
    public void run() {

    }
}
