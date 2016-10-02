package sensor;

import java.util.Random;

public class SmokeSensor implements Sensor
{
	private int status;
	private String message;
	private String location;

	/**
	 * Set the status of the sensor
	 * @param status the status of the sensor. 1 for triggered, 0 for not triggered
	 */
	public SmokeSensor(String location)
	{
		this.status = NOT_TRIGGERED;
		this.location = location;
	}

	/**
	 * Return the status of the sensor
	 * @return status of the sensor
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
	 * Return the message from the sensor
	 * @return the message from the sensor
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
	 * Set the location of the sensor
	 * @param location the location of the sensor
	 */
	public void setLocation(String location) 
	{
		this.location = location;

	}

	/**
	 * Return the location of the sensor.
	 * @return the location of the sensor
	 */
	public String getLocation() 
	{
		return location;
	}

	/**
	 * Randomize the status of the sensor
	 */
	public void randomizeStatus() 
	{
		 Random rand = new Random();
		 int randStatus = rand.nextInt(2);
		 
		 status = randStatus;
		
	}

}
