package sensor;

/**
 * A sensor is part of an alarm. Sensors detect a change temperature, oxygen, pressure, etc. and trigger accordingly.
 */
public abstract class Sensor 
{
	//Boolean constants representing that a sensor is triggered or not triggered
	public static final int NOT_TRIGGERED = 0;
	public static final int TRIGGERED = 1;
	
	//The location of the sensor
	public String location;
	
	//The current status of the sensor
	public int status;
	
	/**
	 * Set the status of the sensor
	 * @param status the status of the sensor. 1 for triggered, 0 for not triggered
	 */
	public abstract void setStatus(int status);
	
	/**
	 * Return the status of the sensor
	 * @return status of the sensor
	 */
	public abstract int getStatus();
	
	/**
	 * Set the location of the sensor
	 * @param location the location of the sensor
	 */
	public abstract void setLocation(String location);
	
	/**
	 * Return the location of the sensor.
	 * @return the location of the sensor
	 */
	public abstract String getLocation();
	
}
