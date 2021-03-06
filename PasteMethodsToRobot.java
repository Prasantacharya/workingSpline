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
			startingENCClicksLeft = drive.masterLeft.getSelectedSensorPosition(0);
			startingENCClicksRight = -drive.followerRightOne.getSelectedSensorPosition(0);
		}
		double robotDistance = Math.abs((((drive.masterLeft.getSelectedSensorPosition(0) - startingENCClicksLeft) + (-drive.followerRightOne.getSelectedSensorPosition(0) - startingENCClicksRight))/AutoConstants.TICKS_PER_INCH)/2);
		if (spline.getDistance() <= robotDistance) {
			drive.masterLeft.set(PercentOutput,0.00);
			drive.masterRight.set(PercentOutput,0.00);
			System.out.println("Final NavX Angle: " + gyro.getYaw());
			System.out.println("Enc value after speed 0 " + drive.masterLeft.getSelectedSensorPosition(0));
			//System.out.println(spline.toString());
			gotStartingENCClicks = false;
			return true;
		} else {
			double angleToDrive;
			if (speed > 0)
				angleToDrive = (spline.getAngle(robotDistance));
			else
				angleToDrive = (spline.getReverseAngle(robotDistance));
			if (spline.getDistance() > 0) {
				if (spline.getDistance() > robotDistance);
				{
				System.out.println("speed = " + speed);
				System.out.println("angle = " + gyro.getYaw());
					if(angleToDrive < -90 && gyro.getYaw() > 90){
						double temp = -180 - angleToDrive;
						temp += -(180 - gyro.getYaw());
						arcadeDrive(speed, -temp /360*8);
					} else if (angleToDrive > 90 && gyro.getYaw() < -90){
						double temp = 180 - angleToDrive;
						temp += (180 + gyro.getYaw());
						arcadeDrive(speed, -temp /360*8);					
					} else {
						arcadeDrive(speed, -((gyro.getYaw() - angleToDrive) /360*8));
					}
					//System.out.println(gyro.getYaw() - angleToDrive);
				}
			} else {
				arcadeDrive(-speed, -((gyro.getYaw() - angleToDrive) /360*8));
			}
		}
		return false;
	}
}
