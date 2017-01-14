package org.usfirst.frc.team1165.robot;

import java.io.Console;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.UsbCameraInfo;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Utility;

/**
 * Uses the CameraServer class to automatically capture video from a USB webcam
 * and send it to the FRC dashboard without doing any vision processing. This
 * is the easiest way to get camera images to the dashboard. Just add this to the
 * robotInit() method in your program.
 */
public class Robot extends IterativeRobot {
	
	private boolean oldUserState;
	private int camera = 0;
	private CameraServer cameraServer;
	
	@Override
	public void robotInit() {
		cameraServer = CameraServer.getInstance();
		
		CameraServer.getInstance().startAutomaticCapture(camera);
		oldUserState = false;
		
		UsbCameraInfo infos[] = UsbCamera.enumerateUsbCameras();
		for (UsbCameraInfo info : infos)
		{
			System.out.println(info.dev + ": " + info.name);
		}
		/**
		VideoSource sources[] = VideoSource.enumerateSources();
		for (VideoSource source : sources)
		{
			System.out.println(source.getName());
		}
		**/
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		boolean newUserState = Utility.getUserButton();
		if (newUserState != oldUserState)
		{
			oldUserState = newUserState;
			if (oldUserState)
			{
				camera ^= 1;
				CameraServer.getInstance().startAutomaticCapture(camera);
			}
		}
	}

}
