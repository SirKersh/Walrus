package sensor;

/**
 * SmokeSensors are binary, they are either triggered or not triggered. They are triggered when there is too much smoke in the atmosphere.
 */
public class SmokeSensor extends Sensor
{
	/**
	 * Constructor for SmokeSensor
	 * @param location the location of the smoke sensor
	 */
	public SmokeSensor(String location){
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
	 * Set the status of the sensor
	 * @param status the status of the sensor. 1 for triggered, 0 for not triggered
	 */
	public void setStatus(int status) {
		if(status == 1)
			this.status = TRIGGERED;
		else
			this.status = NOT_TRIGGERED;
	}

	/**
	 * Set the location of the sensor
	 * @param location the location of the sensor
	 */
	public void setLocation(String location) {
		this.location = location;

	}

	/**
	 * Return the location of the sensor
	 * @return the location of the sensor
	 */
	public String getLocation() {
		return location;
	}


}
