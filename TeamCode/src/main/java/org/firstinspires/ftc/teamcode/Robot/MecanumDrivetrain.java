package org.firstinspires.ftc.teamcode.Robot;
import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

///[Link](https://gm0.org/en/latest/docs/software/tutorials/mecanum-drive.html#field-centric)
@Config
public class MecanumDrivetrain implements Mechanism {
    private DcMotor frontLeft, frontRight, rearLeft, rearRight;
    private OpMode opMode;
    private IMU imu;
    private double frontL, frontR, rearL, rearR;
    private boolean slowToggle = false, driveToggle = false;
    private DriveMode driveMode;
    public static double STRAFING_CONSTANT = 1.1;

    enum DriveMode {
        ROBOT_CENTRIC,
        FIELD_CENTERED,
    }

    public MecanumDrivetrain(OpMode opMode, IMU imu) {
        this(opMode, DriveMode.FIELD_CENTERED, imu);
    }

    public MecanumDrivetrain(@NonNull OpMode opMode, DriveMode driveMode, IMU imu) {
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

        imu.resetYaw();
    }

    @Override
    public void keybind(Gamepad currGamepad, Gamepad prevGamepad) {
        switch (driveMode) {
            case ROBOT_CENTRIC: {
                if (currGamepad.a) setDriveMode(DriveMode.FIELD_CENTERED);
                robotCentricPowers(currGamepad, prevGamepad);
            }break;
            case FIELD_CENTERED: {
                if (currGamepad.a) setDriveMode(DriveMode.ROBOT_CENTRIC);
                fieldCentricPowers2(currGamepad, prevGamepad);
            }break;
        }
        frontLeft.setPower(frontL);
        frontRight.setPower(frontR);
        rearLeft.setPower(rearL);
        rearRight.setPower(rearR);
    }

    public void setDriveMode(DriveMode driveMode){
        this.driveMode = driveMode;
    }

    public DriveMode getDriveMode(){
        return this.driveMode;
    }

    public void setStrafingConstant(double STRAFING_CONSTANT) {
        this.STRAFING_CONSTANT = STRAFING_CONSTANT;
    }

    public double getStrafingConstant(){
        return this.STRAFING_CONSTANT;
    }

    public double getBotYaw() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
    }

    protected void robotCentricPowers(@NonNull Gamepad gamepad, Gamepad prevGamepad) {
        double y = -gamepad.left_stick_y;
        double x = gamepad.left_stick_x * STRAFING_CONSTANT;
        double turn = gamepad.right_stick_x;
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(turn), 1);
//        double magnitude = Range.clip(Math.hypot(x, y), 0, 1);
//        double gamepadAngle = Math.atan2(y, x);
//        double pow1 = Range.clip(Math.sin(gamepadAngle - Math.PI/4) * magnitude, -1, 1);
//        double pow2 = Range.clip(Math.sin(gamepadAngle + Math.PI/4) * magnitude, -1, 1);

        frontL = ( y + x + turn ) / denominator;
        rearL = ( y - x + turn ) / denominator;
        frontR = ( y - x - turn ) / denominator;
        rearR = ( y + x - turn ) / denominator;
    }

    private void fieldCentricPowers(@NonNull Gamepad gamepad, Gamepad prevGamepad) {
        double y = gamepad.left_stick_y;
        double x = -gamepad.left_stick_x;
        double turn = gamepad.right_stick_x;
        double magnitude = Range.clip(Math.hypot(x, y), 0, 1);
        double gamepadAngle = Math.atan2(y, x);
        double robotAngle = getBotYaw();
        double movementAngle = gamepadAngle + robotAngle;
        double pow1 = Range.clip(Math.sin(movementAngle - Math.PI/4) * magnitude, -1, 1);
        double pow2 = Range.clip(Math.sin(movementAngle + Math.PI/4) * magnitude, -1, 1);
        if(gamepad.left_stick_button && !prevGamepad.left_stick_button) slowToggle = !slowToggle;
        double scale = slowToggle  ? .5 : 1;

        frontR = (pow1 - turn) * scale;
        rearL = (pow1 + turn) * scale;
        frontL = (pow2 + turn) * scale;
        rearR = (pow2 - turn) * scale;
    }

    private void fieldCentricPowers2(@NonNull Gamepad gamepad, Gamepad prevGamepad) {
        double y = -gamepad.left_stick_y;
        double x = gamepad.left_stick_x;
        double turn = gamepad.right_stick_x;
        double botHeading = getBotYaw();
        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(turn), 1);
        if(gamepad.left_stick_button && !prevGamepad.left_stick_button) slowToggle = !slowToggle;
        double scale = slowToggle  ? .5 : 1;

        rotX = rotX * STRAFING_CONSTANT;
        frontL = ( rotY + rotX + turn) / denominator;
        rearL = ( rotY - rotX + turn) / denominator;
        frontR = ( rotY - rotX - turn) / denominator;
        rearR = ( rotY + rotX - turn) / denominator;
    }

    public void drive(double power, double angle, double turn) {
        double pow1 = Range.clip(Math.sin(Math.toRadians(angle) - Math.PI/4) * power, -1, 1);
        double pow2 = Range.clip(Math.sin(Math.toRadians(angle) + Math.PI/4) * power, -1, 1);
        frontRight.setPower(pow1 - turn);
        rearLeft.setPower(pow1 + turn);
        frontLeft.setPower(pow2 + turn);
        rearRight.setPower(pow2 - turn);
    }
}
