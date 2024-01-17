package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "AutonomousRightFront", group = "Autonomous")
public class Autonomous3 extends LinearOpMode {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor rearLeft;
    private DcMotor rearRight;

    @Override
    public void runOpMode() {
        // Initialize motors
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        rearLeft = hardwareMap.dcMotor.get("rearLeft");
        rearRight = hardwareMap.dcMotor.get("rearRight");

        // Reverse the right side motors
//        frontRight.setDirection(DcMotor.Direction.REVERSE);
        rearLeft.setDirection(DcMotor.Direction.REVERSE);

        // Wait for the start button to be pressed
        waitForStart();

        // Autonomous logic goes here
        mecanumDrive(0.67 , 0, 0); // Move forward at half power
        sleep(1200); // Sleep for 1 second (adjust as needed)
        mecanumDrive(0,0,0.298);
        sleep(1000);
        mecanumDrive(0.4,0,0);
        sleep(1000);
        stopRobot();

        // Additional autonomous actions can be added here

        // Stop the robot
        stopRobot();
    }

    private void mecanumDrive(double forward, double strafe, double rotate) {
        double frontLeftPower = forward - strafe + rotate;
        double frontRightPower = forward + strafe - rotate;
        double rearLeftPower = forward + strafe + rotate;
        double rearRightPower = forward - strafe - rotate;

        // Set motor powers
        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        rearLeft.setPower(rearLeftPower);
        rearRight.setPower(rearRightPower);
    }

    private void stopRobot() {
        mecanumDrive(0, 0, 0);
    }
}
