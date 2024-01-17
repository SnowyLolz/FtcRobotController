package org.firstinspires.ftc.teamcode;
// Import necessary libraries and classes
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "main")

public class Main extends OpMode {

    // Define motor objects
    private DcMotor frontLeft, frontRight, rearLeft, rearRight, lift, lift1;
    CRServo intake, intake1, intake2;
    Servo claw1, claw2, airplane;

    @Override
    public void init() {
        // Initialize motors from hardware map
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        rearLeft = hardwareMap.dcMotor.get("rearLeft");
        rearRight = hardwareMap.dcMotor.get("rearRight");

        // Set motor directions (assuming motors are oriented correctly)
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        rearLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        rearRight.setDirection(DcMotor.Direction.FORWARD);

        // Set motor run modes
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rearLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rearRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Set zero power behavior
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rearLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rearRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //intake
        intake = hardwareMap.get(CRServo.class, "intake");
        intake1 = hardwareMap.get(CRServo.class, "intake1");
        intake2 = hardwareMap.get(CRServo.class, "intake2");
        lift = hardwareMap.get(DcMotor.class, "lift");
        lift1 = hardwareMap.get(DcMotor.class, "lift1");
        claw1 = hardwareMap.get(Servo.class, "claw1");
        claw2 = hardwareMap.get(Servo.class, "claw2");
        airplane = hardwareMap.get(Servo.class, "airplane");
        claw1.setPosition(.1);
        claw2.setPosition(.3);
        airplane.setPosition(.6);
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

        //intake
        double Ispeed = gamepad2.left_stick_y / 2;
        intake.setPower(-Ispeed);
        intake1.setPower(-Ispeed / 5);
        intake2.setPower(Ispeed);
        lift.setPower(-gamepad2.right_stick_y / 3);
        lift1.setPower(gamepad2.right_stick_y / 3);

      if(gamepad2.right_bumper){

          claw1.setPosition(.3);
          claw2.setPosition(0);

      }
      if(gamepad2.left_bumper) {
          claw1.setPosition(.1);
          claw2.setPosition(.2);
      }
      if(gamepad1.y){

          airplane.setPosition(.7);

      }
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
