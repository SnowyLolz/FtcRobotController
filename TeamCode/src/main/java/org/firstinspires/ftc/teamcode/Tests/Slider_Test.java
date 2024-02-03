package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Robot.Slider;

@TeleOp(name = "Test_Slider", group = "Test")
//@Disabled
public class Slider_Test extends LinearOpMode {
    private Slider slider;
    private int MAX_EXTENSION = 0;
    private Gamepad currGamepad;
    private Gamepad prevGamepad;
    
    @Override
    public void runOpMode() throws InterruptedException {
        slider = new Slider(this);
        currGamepad = new Gamepad();
        prevGamepad = new Gamepad();
        slider.init();
        
        waitForStart();
        while (opModeIsActive() && !isStopRequested()){
            updateGamepads();
            
            slider.keybind(currGamepad, prevGamepad);
            
            telemetry.addData("Slider1 Pos", slider.getSlider1Position());
            telemetry.addData("Slider2 Pos", slider.getSlider2Position());
            telemetry.addData("Max Pos", slider.getMaxExtend());
            telemetry.update();
        }
    }

    void updateGamepads(){
        prevGamepad.copy(currGamepad);
        currGamepad.copy(gamepad1);
    }
}
