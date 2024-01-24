package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
// Import necessary libraries and classes
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "main")

public class Main extends OpMode {

    // Define motor objects
    private DcMotor frontLeft, frontRight, rearLeft, rearRight, lift, knee, knee1;
    Servo claw1, claw2, ankle;

    @Override
    public void init() {
        // Initialize motors from hardware map
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        rearLeft = hardwareMap.dcMotor.get("rearLeft");
        rearRight = hardwareMap.dcMotor.get("rearRight");
        knee = hardwareMap.dcMotor.get("knee");
        knee1 = hardwareMap.dcMotor.get("knee1");
        ankle = hardwareMap.servo.get("ankle");

        // Set motor directions (assuming motors are oriented correctly)
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        rearLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        rearRight.setDirection(DcMotor.Direction.FORWARD);
        knee.setDirection(DcMotorSimple.Direction.FORWARD);
        knee1.setDirection(DcMotorSimple.Direction.REVERSE);

        // Set motor run modes
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rearLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rearRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        knee.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        knee.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Set zero power behavior
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rearLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rearRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        knee.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        knee1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        lift = hardwareMap.get(DcMotor.class, "lift");
        claw1 = hardwareMap.get(Servo.class, "claw1");
        claw2 = hardwareMap.get(Servo.class, "claw2");
        claw1.setPosition(.1);
        claw2.setPosition(.2);
    }

    @Override
    public void loop() {
        // Calculate power for each motor based on gamepad inputs
        double drive = -gamepad1.left_stick_y;
        double strafe = -gamepad1.left_stick_x;
        double rotate = gamepad1.right_stick_x;

        double frontLeftPower = drive - strafe + rotate;
        double frontRightPower = drive + strafe - rotate;
        double rearLeftPower = drive + strafe + rotate;
        double rearRightPower = drive - strafe - rotate;

        // Normalize the powers so they don't exceed +/-1
        double maxPower = Math.max(
                Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower)),
                Math.max(Math.abs(rearLeftPower), Math.abs(rearRightPower))
        );
        if (maxPower > 1.0) {
            frontLeftPower /= maxPower;
            frontRightPower /= maxPower;
            rearLeftPower /= maxPower;
            rearRightPower /= maxPower;
        }

        // Set power to motors
        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        rearLeft.setPower(rearLeftPower);
        rearRight.setPower(rearRightPower);

        // Display motor powers on telemetry
        telemetry.addData("Front Left Power", frontLeftPower);
        telemetry.addData("Front Right Power", frontRightPower);
        telemetry.addData("Rear Left Power", rearLeftPower);
        telemetry.addData("Rear Right Power", rearRightPower);
        telemetry.update();

        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        lift.setPower(-gamepad2.right_stick_y / 2);

      if(gamepad2.right_bumper){

          claw1.setPosition(.25);
          claw2.setPosition(0);

      }
      if(gamepad2.left_bumper) {
          claw1.setPosition(.1);
          claw2.setPosition(.2);
      }

      double KneePower = gamepad2.left_stick_y;
      knee.setPower(KneePower);
      knee1.setPower(KneePower);


    }

    @Override
    public void stop() {
        // Stop all motors
        frontLeft.setPower(0);
        frontRight.setPower(0);
        rearLeft.setPower(0);
        rearRight.setPower(0);
    }
}
