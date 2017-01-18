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
	private int usbCameraIndex = 0;
	private CameraServer cameraServer;
	private UsbCamera usbCameras[];
	
	@Override
	public void robotInit()
	{
		cameraServer = CameraServer.getInstance();
		
		UsbCameraInfo infos[] = UsbCamera.enumerateUsbCameras();
		usbCameras = new UsbCamera[infos.length];
		for (int i = 0; i < usbCameras.length; i++)
		{
			usbCameras[i] = new UsbCamera("USB", infos[i].path);
			usbCameras[i].setResolution(640, 480);
			usbCameras[i].setFPS(10);
			System.out.println("Created USB camera: " + usbCameras[i].getPath());
			CameraServer.getInstance().startAutomaticCapture(usbCameras[i]);
		}
		
		//CameraServer.getInstance().startAutomaticCapture(usbCameras[0]);

		oldUserState = false;
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
				CameraServer.getInstance().removeServer("serve_USB");
				if (++usbCameraIndex >= usbCameras.length)
				{
					usbCameraIndex = 0;
				}
				System.out.println("Switching to USB camera " + usbCameraIndex);
				CameraServer.getInstance().startAutomaticCapture(usbCameras[usbCameraIndex]);
		
			}
		}
	}

}
