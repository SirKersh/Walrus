package sensor;

/**
 * A heat sensor monitors the temperature in the room. 
 */
public class HeatSensor extends Sensor
{
	private int heatIndex;
	private static final int triggerTemp = 1000;
	
	/**
	 * Constructor for HeatSensor
	 * @param location the location of the heat sensor
	 */
	public HeatSensor(String location)
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
	
	/**
	 * Set the heat index of the sensor
	 * @param heat index the location of the sensor
	 */
	public void setHeatIndex(int index) {
		this.heatIndex = index;
		
		if(index > triggerTemp)
			status = TRIGGERED;
	}
	
	/**
	 * Return the heat index of the sensor
	 * @return the heat index of the sensor
	 */
	public int getHeatIndex() {
		return heatIndex;
	}

}
