import lejos.hardware.*;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.subsumption.*;


public class ICCSRunner
{
	
    public static void main(String[] args) {       
    	
    	System.out.println("Press to start");
        
        Button.waitForAnyPress();
        	      
        Behavior drive = new DriveForward();
        Behavior detectHit = new DetectHit(new EV3TouchSensor(SensorPort.S3), new EV3TouchSensor(SensorPort.S2));
        Behavior follow = new FollowWall(new EV3UltrasonicSensor(SensorPort.S4));

        
        Behavior[] bs = {drive, follow, detectHit};
        Arbitrator arbitrator = new Arbitrator(bs);
        arbitrator.go();

    }
}
