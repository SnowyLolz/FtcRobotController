package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
// Import necessary libraries and classes
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "main")

public class Main extends OpMode {

    private DcMotor frontLeft, frontRight, rearLeft, rearRight, lift, knee, knee1;
    Servo claw1, claw2, ankle, airplane;
    public int  kneePOS = -20;
    public int knee1POS = -90;
    boolean toggle = false;
    Gamepad curGamepad1 = new Gamepad();
    Gamepad curGamepad2 = new Gamepad();
    Gamepad prevGamepad1 = new Gamepad();
    Gamepad prevGamepad2 = new Gamepad();

    @Override
    public void init() {
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        rearLeft = hardwareMap.dcMotor.get("rearLeft");
        rearRight = hardwareMap.dcMotor.get("rearRight");
        knee = hardwareMap.dcMotor.get("knee");
        knee1 = hardwareMap.dcMotor.get("knee1");
        ankle = hardwareMap.servo.get("ankle");
        airplane = hardwareMap.servo.get("ankle");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        rearLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        rearRight.setDirection(DcMotor.Direction.FORWARD);
        knee.setDirection(DcMotorSimple.Direction.REVERSE);
        knee1.setDirection(DcMotorSimple.Direction.FORWARD);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rearLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rearRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        knee.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        knee1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rearLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rearRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        knee.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        knee1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        lift = hardwareMap.get(DcMotor.class, "lift");
        claw1 = hardwareMap.get(Servo.class, "claw1");
        claw2 = hardwareMap.get(Servo.class, "claw2");
        claw1.setPosition(.4);
        claw2.setPosition(0);
        ankle.setPosition(.355);
        airplane.setPosition(0);
        knee.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        knee1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        knee.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        knee1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        telemetry.addData("Knee Position" , String.valueOf(knee.getCurrentPosition()));
//        telemetry.addData("Knee1 Position" , String.valueOf(knee1.getCurrentPosition()));
//        telemetry.addData("Knee Position", kneePOS);
//        telemetry.addData("Knee1 Position", knee1POS);
//        telemetry.addData("Putere Knee", String.valueOf(knee.getPower()));
//        telemetry.addData("Putere Knee1", String.valueOf(knee1.getPower()));
//        telemetry.update();

    }

    @Override
    public void loop() {
        prevGamepad1.copy(curGamepad1);
        prevGamepad2.copy(curGamepad1);
        curGamepad1.copy(gamepad1);
        curGamepad2.copy(gamepad2);

        double drive = -gamepad1.left_stick_y;
        double strafe = -gamepad1.left_stick_x;
        double rotate = gamepad1.right_stick_x;

        double frontLeftPower = drive - strafe + rotate;
        double frontRightPower = drive + strafe - rotate;
        double rearLeftPower = drive + strafe + rotate;
        double rearRightPower = drive - strafe - rotate;

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

        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        rearLeft.setPower(rearLeftPower);
        rearRight.setPower(rearRightPower);

        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
       lift.setPower(-gamepad2.right_stick_y / 1.5);

        if(gamepad1.a){
            airplane.setPosition(.3);
        }

      if(gamepad2.right_bumper){
          claw1.setPosition(0);
          claw2.setPosition(0.4);
      }
      if(gamepad2.left_bumper) {
          claw1.setPosition(.4);
          claw2.setPosition(0);
      }

    if(gamepad2.y){
          ankle.setPosition(.381);
    }

    if(gamepad2.a){
          ankle.setPosition(.366);
    }

    int MIN = 0, MAX = -60;
    int MIN2 = 0, MAX2 = -320;
//      double min = -1, max = 1;
//      if(knee.getCurrentPosition() >= MIN) {
//          min = 0;
//          max = .7;
//      }
//      else if(knee.getCurrentPosition() < MAX){
//          min = -.7;
//          max = 0;
//      }
//      knee.setPower(Range.clip(-gamepad2.left_stick_y * .1, min, max));
//      knee1.setPower(Range.clip(-gamepad2.left_stick_y * .1, min, max));

//    toggle = curGamepad2.b && ! prevGamepad2.b;
//    if(toggle){
//        knee.setTargetPosition(MAX);
//        knee1.setTargetPosition(MAX2);
//        knee.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        knee1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        knee.setPower(-.2);
//        knee1.setPower(-.2);
//    }
//    else{
//        knee.setTargetPosition(MIN);
//        knee1.setTargetPosition(MIN2);
//        knee.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        knee1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        knee.setPower(-.5);
//        knee1.setPower(-.5);
//    }

    if(knee.getCurrentPosition() < MAX){
        knee.setPower(gamepad2.left_stick_y);
        if(knee.getCurrentPosition() == MAX){
            knee.setTargetPosition(MAX);
            knee.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }
    if(knee1.getCurrentPosition() < MAX2){
        knee1.setPower(gamepad2.left_stick_y);
        if(knee1.getCurrentPosition() == MAX2){
            knee1.setTargetPosition(MAX2);
            knee1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }


//      double KneePower = -gamepad2.left_stick_y / 3.9 ;
//      knee.setPower(KneePower);
//      knee1.setPower(KneePower);

      telemetry.addData("Knee Position" , String.valueOf(knee.getCurrentPosition()));
      telemetry.addData("Knee1 Position" , String.valueOf(knee1.getCurrentPosition()));
      telemetry.addData("Putere Knee", String.valueOf(knee.getPower()));
      telemetry.addData("Putere Knee1", String.valueOf(knee1.getPower()));
      telemetry.addData("Left Y", -gamepad2.left_stick_y);
//      telemetry.addData("Target", String.valueOf(target));
      telemetry.update();
    }

}


