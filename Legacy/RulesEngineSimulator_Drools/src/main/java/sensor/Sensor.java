package sensor;

public interface Sensor 
{
	//sensors are in alarms
	public static final int NOT_TRIGGERED = 0;
	public static final int TRIGGERED = 1;
	
	/**
	 * Set the status of the sensor
	 * @param status the status of the sensor. 1 for triggered, 0 for not triggered
	 */
	public void setStatus(int status);
	
	/**
	 * Return the status of the sensor
	 * @return status of the sensor
	 */
	public int getStatus();
	
	/**
	 * Set the message to be printed based on status
	 * @param message the message to be printed
	 */
	public void setMessage(String message);
	
	/**
	 * Return the message from the sensor
	 * @return the message from the sensor
	 */
	public String getMessage();
	
	/**
	 * Set the location of the sensor
	 * @param location the location of the sensor
	 */
	public void setLocation(String location);
	
	/**
	 * Return the location of the sensor.
	 * @return the location of the sensor
	 */
	public String getLocation();
	
	/**
	 * Randomize the status of the sensor
	 */
	public void randomizeStatus();
	
	

}
