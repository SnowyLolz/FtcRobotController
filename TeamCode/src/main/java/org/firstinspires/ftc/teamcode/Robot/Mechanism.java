package org.firstinspires.ftc.teamcode.Robot;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public interface Mechanism {

    void init(HardwareMap hardwareMap);
    void keybind(Gamepad gamepad);
    void run();

    default void setZeroPowerBehaviors(DcMotor.ZeroPowerBehavior b, DcMotor... motors){

        for (DcMotor motor : motors){

            motor.setZeroPowerBehavior(b);

        }


    }
    default void setDirections(DcMotorSimple.Direction d, DcMotor... motors){

        for (DcMotor motor : motors){

            motor.setDirection(d);

        }


    }
    default void setRunModes (DcMotor.RunMode m, DcMotor... motors){

        for (DcMotor motor : motors){

            motor.setMode(m);

        }


    }
    default void setPowers (double p, DcMotor... motors){

        for (DcMotor motor : motors){

            motor.setPower(p);

        }


    }
    default void setDirections(Servo.Direction d, Servo... servos){

        for (Servo servo : servos){

            servo.setDirection(d);

        }


    }




}
