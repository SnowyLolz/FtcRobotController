package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

public class Slider implements Mechanism {
    private DcMotor slider1, slider2;
    private int MAX_EXTEND = 0;
    private boolean slowToggle = false;
    private OpMode opMode;

    public Slider(OpMode opMode){
        this.opMode = opMode;
    }

    @Override
    public void init() {
        slider1 = opMode.hardwareMap.get(DcMotor.class, "slider1");
        slider2 = opMode.hardwareMap.get(DcMotor.class, "slider2");

        setZeroPowerBehaviors(DcMotor.ZeroPowerBehavior.BRAKE, slider1, slider2);
        setDirections(DcMotorSimple.Direction.FORWARD, slider1);
        setDirections(DcMotorSimple.Direction.REVERSE, slider2);
        setRunModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER, slider1, slider2);
        setPowers(0, slider1, slider2);
    }

    @Override
    public void keybind(Gamepad currGamepad, Gamepad prevGamepad) {
        if(currGamepad.left_stick_button && !prevGamepad.left_stick_button)
            slowToggle = !slowToggle;
        double scale = slowToggle ? .6 : 1;
        double power = currGamepad.left_stick_y * scale;

        double min = -1, max = 1;
        if(slider1.getCurrentPosition() > MAX_EXTEND){
            min = -1;
            max = 0;
        }
        if(slider1.getCurrentPosition() < 0){
            min = 0;
            max = 1;
        }

        slider1.setPower(Range.clip(power, min, max));
        slider2.setPower(Range.clip(power, min, max));
    }

    public int getMaxExtend() {
        return MAX_EXTEND;
    }

    public int getSlider1Position(){
        return slider1.getCurrentPosition();
    }

    public int getSlider2Position(){
        return slider2.getCurrentPosition();
    }
}
