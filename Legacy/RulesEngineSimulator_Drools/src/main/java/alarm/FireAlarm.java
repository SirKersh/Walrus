package alarm;

import java.util.Random;

public class FireAlarm implements Alarm
{
	//alarms have sensors
	private int status;
	private String message;
	private String location;

	/**
	 * Set the status of the alarm
	 * @param status the status of the alarm. 1 for triggered, 0 for not triggered
	 */
	public FireAlarm(String location)
	{
		this.status = NOT_TRIGGERED;
		this.location = location;
	}

	/**
	 * Return the status of the alarm
	 * @return status of the alarm
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		if(status == 1)
			this.status = TRIGGERED;
		else
			this.status = NOT_TRIGGERED;
	}

	/**
	 * Return the message from the alarm
	 * @return the message from the alarm
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Set the message to be printed based on status
	 * @param message the message to be printed
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Set the location of the alarm
	 * @param location the location of the alarm
	 */
	public void setLocation(String location) 
	{
		this.location = location;

	}

	/**
	 * Return the location of the alarm.
	 * @return the location of the alarm
	 */
	public String getLocation() 
	{
		return location;
	}

	/**
	 * Randomize the status of the alarm
	 */
	public void randomizeStatus() 
	{
		Random rand = new Random();
		int randStatus = rand.nextInt(2);

		status = randStatus;
	}
}
