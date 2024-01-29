package org.firstinspires.ftc.teamcode.Robot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;



public class Arm implements Mechanism {

    DcMotor lift, knee, knee1;


    @Override
    public void init(HardwareMap hardwareMap) {

        knee = hardwareMap.dcMotor.get("knee");
        knee1 = hardwareMap.dcMotor.get("knee1");
        lift = hardwareMap.dcMotor.get("lift");


        setZeroPowerBehaviors(DcMotor.ZeroPowerBehavior.BRAKE, knee, knee1, lift);
        setDirections(DcMotorSimple.Direction.FORWARD, knee1, lift);
        setDirections(DcMotorSimple.Direction.REVERSE, knee);
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
