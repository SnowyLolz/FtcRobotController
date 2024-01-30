package org.firstinspires.ftc.teamcode.Robot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;



public class Arm implements Mechanism {
    private DcMotor lift, knee, knee1;
    private OpMode opMode;

    public Arm(OpMode opMode){
        this.opMode = opMode;
    }

    @Override
    public void init() {
        knee = opMode.hardwareMap.dcMotor.get("knee");
        knee1 = opMode.hardwareMap.dcMotor.get("knee1");
        lift = opMode.hardwareMap.dcMotor.get("lift");

        setZeroPowerBehaviors(DcMotor.ZeroPowerBehavior.BRAKE, knee, knee1, lift);
        setDirections(DcMotorSimple.Direction.FORWARD, knee1, lift);
        setDirections(DcMotorSimple.Direction.REVERSE, knee);
        setRunModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER, knee, knee1, lift);
        setPowers(0, knee, knee1, lift);
    }

    @Override
    public void keybind(Gamepad gamepad) {

    }

    @Override
    public void run() {

    }
}
