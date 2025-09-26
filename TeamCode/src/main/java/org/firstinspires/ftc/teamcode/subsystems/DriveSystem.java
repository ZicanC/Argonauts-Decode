package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.routines.Routine;

public class DriveSystem extends Subsystem
{

    private DcMotor drive_motor_fl;
    private DcMotor drive_motor_fr;
    private DcMotor drive_motor_bl;
    private DcMotor drive_motor_br;

    public DcMotor getDrive_motor_fl()
    {
        return drive_motor_fl;
    }

    public DcMotor getDrive_motor_fr()
    {
        return drive_motor_fr;
    }

    public DcMotor getDrive_motor_bl()
    {
        return drive_motor_bl;
    }

    public DcMotor getDrive_motor_br()
    {
        return drive_motor_br;
    }

    public void driveOnTick(double x, double y, double rx) {
        x = -x;

        getDrive_motor_fl().setPower(-(y + x + rx));
        getDrive_motor_fr().setPower(-(y - x + rx));
        getDrive_motor_bl().setPower((y - x - rx));
        getDrive_motor_br().setPower((y + x - rx));
    }



    public void driveOnTickVector(double x, double y, double rx){
        x = -x;
        rx = -rx;
        double theta = Math. (y, x);
        double power = Math.hypot(x, y);

        double sin = Math.sin(theta - Math.PI/4);
        double cos = Math.cos(theta - Math.PI/4);
        double max = Math.max(Math.abs(sin), Math.abs(cos));

        double leftFront = power * cos/max + rx;
        double rightFront = power * sin/max - rx;
        double leftRear = power * sin/max + rx;
        double rightRear = power * cos/max - rx;

        if ((power + Math.abs(rx)) > 1) {
            leftFront   /= power + Math.abs(rx);
            rightFront /= power + Math.abs(rx);
            leftRear    /= power + Math.abs(rx);
            rightRear  /= power + Math.abs(rx);
        }

        getDrive_motor_fl().setPower(leftFront);
        getDrive_motor_fr().setPower(-rightFront);
        getDrive_motor_bl().setPower(leftRear);
        getDrive_motor_br().setPower(-rightRear);
    }

    public DriveSystem(Routine routine)
    {
        super(routine);
        drive_motor_fl = routine.hardwareMap.get(DcMotor.class, "drive_motor_fl");
        drive_motor_fr = routine.hardwareMap.get(DcMotor.class, "drive_motor_fr");
        drive_motor_bl = routine.hardwareMap.get(DcMotor.class, "drive_motor_bl");
        drive_motor_br = routine.hardwareMap.get(DcMotor.class, "drive_motor_br");

        drive_motor_fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drive_motor_bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drive_motor_fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drive_motor_br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

//        drive_motor_fr.setDirection(DcMotorSimple.Direction.REVERSE);
//        drive_motor_bl.setDirection(DcMotorSimple.Direction.REVERSE);
    }
}
