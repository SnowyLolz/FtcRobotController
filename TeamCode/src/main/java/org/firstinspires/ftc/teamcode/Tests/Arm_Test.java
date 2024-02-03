package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Robot.Arm;

@TeleOp(name = "Test_Arm", group = "Test")
//@Disabled
public class Arm_Test extends LinearOpMode {
    Arm arm;
    Gamepad currGamepad;
    Gamepad prevGamepad;

    @Override
    public void runOpMode() throws InterruptedException {
        arm = new Arm(this);
        currGamepad = new Gamepad();
        prevGamepad = new Gamepad();
        arm.init();

        waitForStart();

        while (opModeIsActive() && !isStopRequested()){
            updateGamepads();

            arm.keybind(currGamepad, prevGamepad);

            telemetry.addData("Servo1 position",
                    ".4f / .4f",
                    arm.getServo1Position(), Arm.MAX1);
            telemetry.addData("Servo2 position",
                    ".4f / .4f",
                    arm.getServo2Position(), Arm.MAX2);
            telemetry.addData("Scale", Arm.SCALE);
            telemetry.addData("Power", arm.getServoPower());
            telemetry.update();
        }
    }

    void updateGamepads(){
        prevGamepad.copy(currGamepad);
        currGamepad.copy(gamepad1);
    }
}
