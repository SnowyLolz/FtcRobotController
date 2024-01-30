package org.firstinspires.ftc.teamcode.Robot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class MecanumDrivetrain implements Mechanism {

    private DcMotor frontLeft, frontRight, rearLeft, rearRight;


    @Override
    public void init(HardwareMap hardwareMap) {
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        rearLeft = hardwareMap.dcMotor.get("rearLeft");
        rearRight = hardwareMap.dcMotor.get("rearRight");

        setZeroPowerBehaviors(DcMotor.ZeroPowerBehavior.BRAKE, frontLeft, frontRight, rearLeft, rearRight);
        setDirections(DcMotorSimple.Direction.FORWARD,frontRight, rearRight);
        setDirections(DcMotorSimple.Direction.REVERSE, frontLeft, rearLeft);

        setRunModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setPowers(0);
    }

    @Override
    public void keybind(Gamepad gamepad) {

    }

    @Override
    public void run() {

    }
}
