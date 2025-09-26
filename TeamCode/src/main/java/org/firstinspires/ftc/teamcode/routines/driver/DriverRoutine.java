package org.firstinspires.ftc.teamcode.routines.driver;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.routines.Routine;
import org.firstinspires.ftc.teamcode.subsystems.ClawSystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSystem;
import org.firstinspires.ftc.teamcode.subsystems.System;
import org.firstinspires.ftc.teamcode.subsystems.ViperSystem;

@TeleOp(name = "Driver")
public class DriverRoutine extends Routine {
	public ViperSystem viperSystem;
	public ClawSystem clawSystem;
	public DriveSystem driveSystem;

	private double threshold = 0.6;

	@Override
	public void onInit() {
		super.onInit();
//		viperSystem = new ViperSystem(this);
//		clawSystem = new ClawSystem(this);
//		driveSystem = new DriveSystem(this);
	}

	@Override
	public void onStart() {
		super.onStart();
		//viperSystem = new ViperSystem(this);
		//clawSystem = new ClawSystem(this);
		driveSystem = new DriveSystem(this);
		try
		{
			Thread.sleep(1000);

		}catch(InterruptedException e){

		}
		while(opModeIsActive()){
			viperSystem.moveOnTick(gamepad1.dpad_up, gamepad1.dpad_down);

			viperSystem.holdDown(gamepad1.left_trigger == 1.0);


			telemetry.addData("Left Viper Motor", viperSystem.getViper_motor_l().getCurrentPosition());
			telemetry.addData("Right Viper Motor", viperSystem.getViper_motor_r().getCurrentPosition());

			clawSystem.toggleClaw(gamepad1.right_bumper);
			telemetry.addData("Left Servo Claw", clawSystem.getClaw_servo().getPosition());

			clawSystem.shoulderOnTick(gamepad1.b, gamepad1.x);
			telemetry.addData("Shoulder Position", clawSystem.getShoulder_servo_l().getPosition());

			telemetry.addData("Shoulder ACTUAL Position", clawSystem.getActualPos());



			driveSystem.driveOnTickVector(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);

			telemetry.addData("left_stick_x", gamepad1.left_stick_x);
			telemetry.addData("left_stick_y", gamepad1.left_stick_x);
			telemetry.addData("right_stick_x", gamepad1.right_stick_x);
			telemetry.addData("Has Reached Position", clawSystem.isHasReached);



			clawSystem.presetPosition(gamepad1.dpad_left, gamepad1.dpad_right, gamepad1.y, gamepad1.a);
			if(!clawSystem.isHasReached) {
				if (Math.abs(clawSystem.getShoulder_servo_l().getPosition() - clawSystem.getSetPos()) >= 0.005) {
					clawSystem.setShoulderPos(clawSystem.getShoulder_servo_l().getPosition() + clawSystem.getDeltaPos());
				} else {
					clawSystem.isHasReached = true;
				}
			}

			if((Math.abs(gamepad1.left_stick_x) > threshold || Math.abs(gamepad1.left_stick_y) > threshold || Math.abs(gamepad1.right_stick_x) > threshold)&& clawSystem.isOpen()){
				clawSystem.setClawPos(0.0);//test
			}// Maybe add this for vipers as well
			else if(clawSystem.isOpen()){
				clawSystem.setClawPos(0.55);
			}

			telemetry.update();
		}

	}

}
