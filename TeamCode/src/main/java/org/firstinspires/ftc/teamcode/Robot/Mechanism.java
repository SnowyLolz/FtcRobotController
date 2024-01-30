package org.firstinspires.ftc.teamcode.Robot;


import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public interface Mechanism {

    void init();

    void keybind(Gamepad gamepad);

    void run();

    default void setZeroPowerBehaviors(DcMotor.ZeroPowerBehavior b, @NonNull DcMotor... motors){

        for (DcMotor motor : motors){

            motor.setZeroPowerBehavior(b);

        }


    }
    default void setDirections(DcMotorSimple.Direction d, @NonNull DcMotor... motors){

        for (DcMotor motor : motors){

            motor.setDirection(d);

        }


    }
    default void setRunModes (DcMotor.RunMode m, @NonNull DcMotor... motors){

        for (DcMotor motor : motors){

            motor.setMode(m);

        }


    }
    default void setPowers (double p, @NonNull DcMotor... motors){

        for (DcMotor motor : motors){

            motor.setPower(p);

        }


    }
    default void setDirections(Servo.Direction d, @NonNull Servo... servos){

        for (Servo servo : servos){

            servo.setDirection(d);

        }


    }




}
