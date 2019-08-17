import lejos.hardware.motor.Motor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.subsumption.Behavior;

public class DetectHit implements Behavior {

	private boolean suppressed = false;
	private EV3TouchSensor rightBumper;
	private EV3TouchSensor leftBumper;
	private float[] sampleRight;
	private float[] sampleLeft;
	
    public DetectHit(EV3TouchSensor rightBumper, EV3TouchSensor leftBumper){
    	this.rightBumper = rightBumper;
    	this.leftBumper = leftBumper;
    	sampleRight = new float[1];
    	sampleLeft = new float[1];
    }

    public boolean takeControl() {
    	rightBumper.getTouchMode().fetchSample(sampleRight, 0);
    	leftBumper.getTouchMode().fetchSample(sampleLeft, 0);
    	return sampleRight[0] == 1 || sampleLeft[0] == 1 ;
    }

    public void suppress() {
       suppressed = true;
    }
    
    public void action() {
		Motor.A.setSpeed(200);
		Motor.B.setSpeed(200);
    	suppressed = false;
    	Motor.A.rotate(-360, true);
    	Motor.B.rotate(-360, true);
    	if(sampleRight[0] == 1 && sampleLeft[0] == 1) {
        	Motor.B.rotate(90, true);
    	} else if(sampleRight[0] == 1) {
        	Motor.B.rotate(90, true);
    	} else if(sampleLeft[0] == 1) {
        	Motor.A.rotate(90, true);
    	} else {
    		System.out.println("DIDNT CONSIDER THIS!!");
    	}
    	while(!suppressed && Motor.B.isMoving()) Thread.yield();
    }
    
    	
}