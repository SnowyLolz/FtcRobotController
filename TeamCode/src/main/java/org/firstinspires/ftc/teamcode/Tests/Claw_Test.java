package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Robot.Claw;

@TeleOp(name = "Test_Claw", group = "Test")
//@Disabled
public class Claw_Test extends LinearOpMode {
    Claw claw;
    Gamepad currGamepad;
    Gamepad prevGamepad;

    @Override
    public void runOpMode() throws InterruptedException {
        claw = new Claw(this);
        currGamepad = new Gamepad();
        prevGamepad = new Gamepad();
        claw.init();

        waitForStart();

        while (opModeIsActive() && !isStopRequested()){
            updateGamepads();

            claw.keybind(currGamepad, prevGamepad);

            telemetry.addData("Claw1",
                    "%.4f / %.4f / %.4f",
                    claw.getClaw1Min(), claw.getClaw1Pos(), claw.getClaw1Max());
            telemetry.addData("Claw2",
                    "%.4f / %.4f / %.4f",
                    claw.getClaw2Min(), claw.getClaw2Pos(), claw.getClaw2Max());
            telemetry.addData("Ankle",
                    "%.4f / %.4f / %.4f",
                    claw.getAnkleMin(), claw.getAnklePos(), claw.getAnkleMax());
            telemetry.addData("Claw state",
                    claw.getClawState());
            telemetry.addData("Triggers",
                    "%.4f / %.4f",
                    claw.getLeftTrigger(), claw.getRightTrigger());
            telemetry.update();
        }
    }

    void updateGamepads(){
        prevGamepad.copy(currGamepad);
        currGamepad.copy(gamepad1);
    }
}
