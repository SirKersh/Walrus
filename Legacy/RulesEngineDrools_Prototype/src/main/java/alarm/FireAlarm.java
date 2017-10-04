package alarm;


import java.util.ArrayList;
import sensor.*;

/**
 * A fire alarm contain a heat sensor and a smoke sensor which are triggered by environment data. Sensors and alarms are monitored by the Rules Tables.
 */

public class FireAlarm extends Alarm
{
	//The smoke and heat sensor contained within the alarm
	private Sensor heatSensor;
	private Sensor smokeSensor;

	/**
	 * Constructor for FireAlarm. The constructor take in a location which represents where the fire alarm is currently installed
	 * @param location The installed location of the fire alarm
	 */
	public FireAlarm(String location){
		this.status = NOT_TRIGGERED;
		this.location = location;
		sensors.add(heatSensor = new HeatSensor(location));
		sensors.add(smokeSensor = new SmokeSensor(location));
	}

	/**
	 * Return the status of the alarm
	 * @return status of the alarm
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Set the status of the fire alarm. This method is used for testing purposes only, a fire alarm status should be set though it's sensors
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		if(status == 1)
			this.status = TRIGGERED;
		else
			this.status = NOT_TRIGGERED;
	}

	/**
	 * Set the location of the alarm
	 * @param location the location of the alarm
	 */
	public void setLocation(String location) {
		this.location = location;

	}

	/**
	 * Return the location of the alarm.
	 * @return the location of the alarm
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Return the heat sensor
	 * @return the heat sensor
	 */
	public Sensor getHeatSensor() {
		return heatSensor;
	}

	/**
	 * Set the heat sensor status
	 * @param heatSensorStatus the heat sensor status to set
	 */
	public void setHeatSensorStatus(int heatSensorStatus) {
		this.heatSensor.status = heatSensorStatus;
	}

	/**
	 * Return the smoke sensor
	 * @return the smoke sensor
	 */
	public Sensor getSmokeSensor() {
		return smokeSensor;
	}

	/**
	 * Set the smoke sensor status
	 * @param smokeSensorStatus the smokeSensorStatus to set
	 */ 
	public void setSmokeSensor(int smokeSensorStatus) {
		this.smokeSensor.status = smokeSensorStatus;
	}

	/**
	 * Return all the sensors in an alarm
	 * @return the sensors
	 */
	public ArrayList<Sensor> getSensors() {
		return sensors;
	}

	/**
	 * Add a sensor to the list of sensors in an alarm
	 * @param sensor the sensor to add
	 */
	public void addSensor(Sensor sensor) {
		sensors.add(sensor);		
	}

}
