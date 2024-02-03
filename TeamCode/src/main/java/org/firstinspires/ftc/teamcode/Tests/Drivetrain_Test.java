package org.firstinspires.ftc.teamcode.Tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.Robot.MecanumDrivetrain;

@TeleOp(name = "Test_Drivetrain", group = "Test")
//@Disabled
@Config
public class Drivetrain_Test extends LinearOpMode {
    private IMU imu;
    private MecanumDrivetrain drivetrain;
    private Gamepad currGamepad;
    private Gamepad prevGamepad;
    public static RevHubOrientationOnRobot.LogoFacingDirection logoFacingDirection = RevHubOrientationOnRobot.LogoFacingDirection.UP;
    public static RevHubOrientationOnRobot.UsbFacingDirection usbFacingDirection = RevHubOrientationOnRobot.UsbFacingDirection.FORWARD;
    public static RevHubOrientationOnRobot revHubOrientation = new RevHubOrientationOnRobot(
            RevHubOrientationOnRobot.LogoFacingDirection.UP,
            RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);

    @Override
    public void runOpMode() throws InterruptedException {
        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                logoFacingDirection,
                usbFacingDirection));
        imu.initialize(parameters);
        drivetrain = new MecanumDrivetrain(this, imu);
        currGamepad = new Gamepad();
        prevGamepad = new Gamepad();
        drivetrain.init();

        waitForStart();

        while (opModeIsActive() && !isStopRequested()){
            prevGamepad.copy(currGamepad);
            currGamepad.copy(gamepad1);
//            updateGamepads();

            drivetrain.keybind(currGamepad, prevGamepad);

            telemetry.addData("Bot Yaw", drivetrain.getBotYaw());
            telemetry.addData("Stick Angle", Math.atan2(-currGamepad.left_stick_y, currGamepad.left_stick_x));
            telemetry.addData("Drive Mode", drivetrain.getDriveMode());
            telemetry.addData("Strafing Constant", drivetrain.getStrafingConstant());
            telemetry.update();
        }
    }

    void updateGamepads(){
        prevGamepad.copy(currGamepad);
        currGamepad.copy(gamepad1);
    }
}
