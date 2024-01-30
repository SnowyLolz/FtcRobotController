package org.firstinspires.ftc.teamcode.Robot;
import androidx.annotation.NonNull;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

import java.util.Arrays;
import java.util.List;


public class MecanumDrivetrain implements Mechanism {
    private DcMotor frontLeft, frontRight, rearLeft, rearRight;
    private OpMode opMode;
    private BNO055IMU imu;
    private double frontL, frontR, rearL, rearR;
    private boolean slowToggle = false;
    private DriveMode driveMode;
    enum DriveMode {
        NORMAL,
        FIELD_CENTERED,
    }

    public MecanumDrivetrain(OpMode opMode, BNO055IMU imu) {
        this(opMode, DriveMode.FIELD_CENTERED, imu);
    }

    public MecanumDrivetrain(@NonNull OpMode opMode, DriveMode driveMode, BNO055IMU imu) {
        this.driveMode = driveMode;
        this.opMode = opMode;
        this.imu = imu;

    }

    @Override
    public void init() {
        frontLeft = opMode.hardwareMap.dcMotor.get("frontLeft");
        frontRight = opMode.hardwareMap.dcMotor.get("frontRight");
        rearLeft = opMode.hardwareMap.dcMotor.get("rearLeft");
        rearRight = opMode.hardwareMap.dcMotor.get("rearRight");

        setZeroPowerBehaviors(DcMotor.ZeroPowerBehavior.BRAKE, frontLeft, frontRight, rearLeft, rearRight);
        setDirections(DcMotorSimple.Direction.FORWARD,frontRight, rearRight);
        setDirections(DcMotorSimple.Direction.REVERSE, frontLeft, rearLeft);
        setRunModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER, frontLeft, frontRight, rearLeft, rearRight);
        setPowers(0, frontLeft, frontRight, rearLeft, rearRight);
    }

    @Override
    public void keybind(Gamepad gamepad) {

    }

    @Override
    public void run() {

    }

    public void setDriveMode(DriveMode driveMode){
        this.driveMode = driveMode;
    }

    public DriveMode getDriveMode(){
        return this.driveMode;
    }

    public void drive(Gamepad gamepad, Gamepad prevGamepad) {
        switch (driveMode) {
            case NORMAL: {
                if (gamepad.a) setDriveMode(DriveMode.FIELD_CENTERED);
                calculatePowers(gamepad, prevGamepad);
            }break;
            case FIELD_CENTERED: {
                if (gamepad.a) setDriveMode(DriveMode.NORMAL);
                calculatePowers2(gamepad, prevGamepad);
            }break;
        }
        frontLeft.setPower(frontL);
        frontRight.setPower(frontR);
        rearLeft.setPower(rearL);
        rearRight.setPower(rearR);
    }

    public void drive(double power, double angle, double turn) {
        double pow1 = Range.clip(Math.sin(Math.toRadians(angle) - Math.PI/4) * power, -1, 1);
        double pow2 = Range.clip(Math.sin(Math.toRadians(angle) + Math.PI/4) * power, -1, 1);
        frontRight.setPower(pow1 - turn);
        rearLeft.setPower(pow1 + turn);
        frontLeft.setPower(pow2 + turn);
        rearRight.setPower(pow2 - turn);
    }

    protected void calculatePowers(@NonNull Gamepad gamepad, Gamepad prevGamepad) {
        double x = gamepad.left_stick_x;
        double y = -gamepad.left_stick_y;
        double turn = gamepad.right_stick_x;
        double magnitude = Range.clip(Math.hypot(x, y), 0, 1);
        double gamepadAngle = Math.atan2(y, x);
        double pow1 = Range.clip(Math.sin(gamepadAngle - Math.PI/4) * magnitude, -1, 1);
        double pow2 = Range.clip(Math.sin(gamepadAngle + Math.PI/4) * magnitude, -1, 1);
        if(gamepad.left_stick_button && !prevGamepad.left_stick_button) slowToggle = !slowToggle;
        double scale = slowToggle  ? .5 : 1;

        frontR = (pow1 - turn ) * scale;
        rearL = (pow1 + turn ) * scale;
        frontL = (pow2 + turn ) * scale;
        rearR = (pow2 - turn ) * scale;
    }

    private void calculatePowers2(@NonNull Gamepad gamepad, Gamepad prevGamepad) {
        double x = -gamepad.left_stick_x;
        double y = gamepad.left_stick_y;
        double turn = gamepad.right_stick_x;
        double magnitude = Range.clip(Math.hypot(x, y), 0, 1);
        double gamepadAngle = Math.atan2(y, x);
        double robotAngle = getAngle();
        double movementAngle = gamepadAngle + robotAngle;
        double pow1 = Range.clip(Math.sin(movementAngle - Math.PI/4) * magnitude, -1, 1);
        double pow2 = Range.clip(Math.sin(movementAngle + Math.PI/4) * magnitude, -1, 1);
        if(gamepad.left_stick_button && !prevGamepad.left_stick_button) slowToggle = !slowToggle;
        double scale = slowToggle  ? .5 : 1;

        frontR = (pow1 - turn)* scale;
        rearL = (pow1 + turn)* scale;
        frontL = (pow2 + turn)* scale;
        rearR = (pow2 - turn)* scale;
    }

    public double getAngle() {
        return imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.YXZ, AngleUnit.RADIANS).firstAngle + Math.toRadians(180);
    }
}
