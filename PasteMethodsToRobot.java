import org.usfirst.frc.team20.robot.AutoConstants;
import org.usfirst.frc.team20.robot.RobotGrid;

import edu.wpi.first.wpilibj.RobotDrive;

public class PasteMethodsToRobot {
	//if you couldn't tell by the name paste this method into robot.java
	//drive.masterLeft.getEncPosition() is the encoder ticks on the left side of the robot
	//AutoConstants.TICKS_PER_INCH is the conversion from ticks to inches
	//drive.followerRightOne.getEncPosition() is the encoder ticks on the right side of the robot the right side of our robot ticked backwards so it is negated if your robot doesnt do that then dont have this value negated
	//gyro.getYaw() angle of the robot from -180 to 180
	//in the line "myDrive.arcadeDrive(-speed, -((gyro.getYaw() - angleToDrive) /360*8),true);" the * 8 is a tuning value increase this value if it paths wide decrease this value if it goes spastic or narrow
	//gotStartingENCClicks needs to be a global variable started at false
	//myDrive is the robotDrive class
	public boolean spline(double speed, RobotGrid spline) {
    	int startingENCClicksLeft;
    	int startingENCClicksRight;
		if (gotStartingENCClicks == false) {
			gotStartingENCClicks = true;
			startingENCClicksLeft = drive.masterLeft.getEncPosition();
			startingENCClicksRight = -drive.followerRightOne.getEncPosition();
		}
		if (spline.getDistance() <= (((drive.masterLeft.getEncPosition() - startingENCClicksLeft)/ AutoConstants.TICKS_PER_INCH + (-drive.followerRightOne.getEncPosition() - startingENCClicksRight)/ AutoConstants.TICKS_PER_INCH))/2) {
			drive.masterLeft.set(0.00);
			drive.masterRight.set(0.00);
			System.out.println("Final NavX Angle: " + gyro.getYaw());
			System.out.println("Enc value after speed 0 " + drive.masterLeft.getEncPosition());
			//System.out.println(spline.toString());
			gotStartingENCClicks = false;
			return true;
		} else {
			double angleToDrive;
			if (speed > 0)
				angleToDrive = (spline.getAngle(Math.abs(((drive.masterLeft.getEncPosition() - startingENCClicksLeft)/ AutoConstants.TICKS_PER_INCH + (-drive.followerRightOne.getEncPosition() - startingENCClicksRight)/ AutoConstants.TICKS_PER_INCH))/2));
			else
				angleToDrive = (spline.getReverseAngle(Math.abs(((drive.masterLeft.getEncPosition() - startingENCClicksLeft)/ AutoConstants.TICKS_PER_INCH + (-drive.followerRightOne.getEncPosition() - startingENCClicksRight)/ AutoConstants.TICKS_PER_INCH))/2));
			if (spline.getDistance() > 0) {
				if (spline.getDistance() > Math.abs(((drive.masterLeft.getEncPosition() - startingENCClicksLeft)/ AutoConstants.TICKS_PER_INCH + (-drive.followerRightOne.getEncPosition() - startingENCClicksRight)/ AutoConstants.TICKS_PER_INCH))/2)
				{
				System.out.println("speed = " + speed);
				System.out.println("angle = " + gyro.getYaw());
					if(angleToDrive < -90 && gyro.getYaw() > 90){
						double temp = -180 - angleToDrive;
						temp += -(180 - gyro.getYaw());
						myDrive.arcadeDrive(speed, -temp /360*8,true);
					} else if (angleToDrive > 90 && gyro.getYaw() < -90){
						double temp = 180 - angleToDrive;
						temp += (180 + gyro.getYaw());
						myDrive.arcadeDrive(speed, -temp /360*8,true);					
					} else {
						myDrive.arcadeDrive(speed, -((gyro.getYaw() - angleToDrive) /360*8),true);
					}
					//System.out.println(gyro.getYaw() - angleToDrive);
				}
			} else {
				myDrive.arcadeDrive(-speed, -((gyro.getYaw() - angleToDrive) /360*8),true);
			}
		}
		return false;
	}
}
