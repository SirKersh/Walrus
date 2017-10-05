package alarm;

public interface Alarm 
{

	public static final int NOT_TRIGGERED = 0;
	public static final int TRIGGERED = 1;
	
	/**
	 * Set the status of the alarm
	 * @param status the status of the alarm. 1 for triggered, 0 for not triggered
	 */
	public void setStatus(int status);
	
	/**
	 * Return the status of the alarm
	 * @return status of the alarm
	 */
	public int getStatus();
	
	/**
	 * Set the message to be printed based on status
	 * @param message the message to be printed
	 */
	public void setMessage(String message);
	
	/**
	 * Return the message from the alarm
	 * @return the message from the alarm
	 */
	public String getMessage();
	
	/**
	 * Set the location of the alarm
	 * @param location the location of the alarm
	 */
	public void setLocation(String location);
	
	/**
	 * Return the location of the alarm.
	 * @return the location of the alarm
	 */
	public String getLocation();
	
	/**
	 * Randomize the status of the alarm
	 */
	public void randomizeStatus();
	
	

}
