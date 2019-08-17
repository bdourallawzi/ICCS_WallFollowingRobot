import lejos.hardware.motor.Motor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;

public class FollowWallProportional implements Behavior
{
	private EV3UltrasonicSensor	ultrasonicSensor;
	private boolean	suppressed	= false;
	 private float[] sample;

	public FollowWallProportional(final EV3UltrasonicSensor ultrasonicSensor)
	{
		this.ultrasonicSensor = ultrasonicSensor;
		sample = new float[1];
	}

	@Override
	public boolean takeControl()
	{
		 	ultrasonicSensor.getDistanceMode().fetchSample(sample, 0);
	    	System.out.println( "S:" + sample[0]);
	    	return !(Math.round(sample[0] * 100.00)/100.00 < 0.2 && Math.round(sample[0] * 100.00)/100.00 >= 0.16); // 0.35 0.2
	}
	
	@Override
	public void suppress()
	{
		suppressed = true;
	}

	@Override
	public void action()
	{
		suppressed = false;
		 Motor.A.setSpeed(600);
		 Motor.B.setSpeed(600);
		 int proportionalCorrection;
		
		 if(sample[0] < 0.16) {
			proportionalCorrection = (int) (( 0.16 - sample[0] ) * 100);
			if(proportionalCorrection > 20) proportionalCorrection = 20;
			if (proportionalCorrection < 5) proportionalCorrection = 5;
        	Motor.A.rotate(proportionalCorrection, true);
        	//System.out.println("sample "+ sample[0] + "rotate" + proportionalCorrection);
    	
		} else if(sample[0] > 0.21) {
			proportionalCorrection = (int) (( sample[0] - 0.2) * 100);
			if(proportionalCorrection > 20) proportionalCorrection = 20;
			if (proportionalCorrection < 5) proportionalCorrection = 5;
        	Motor.B.rotate(proportionalCorrection, true);
        	//System.out.println("sample "+ sample[0] + "rotate" + proportionalCorrection);
    	}
    	
		while(!suppressed && Motor.B.isMoving()) Thread.yield();
	}

}