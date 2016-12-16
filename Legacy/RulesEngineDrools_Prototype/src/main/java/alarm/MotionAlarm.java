package alarm;

import java.util.ArrayList;
import sensor.*;

/**
 * A motion alarm contain a heat sensor and a smoke sensor which are triggered by environment data. Sensors and alarms are monitored by the Rules Tables.
 */

public class MotionAlarm extends Alarm
{
	//The motion sensor contained within the alarm
	private Sensor motionSensor;

	/**
	 * Constructor for MotionAlarm. The constructor take in a location which represents where the motion alarm is currently installed
	 * @param location The installed location of the motion alarm
	 */
	public MotionAlarm(String location){
		this.status = NOT_TRIGGERED;
		this.location = location;
		sensors.add(motionSensor = new MotionSensor(location));
	}

	/**
	 * Return the status of the alarm
	 * @return status of the alarm
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Set the status of the motion alarm. This method is used for testing purposes only, a motion alarm status should be set though it's sensors
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
	 * Return the motionSensor
	 * @return the motionSensor
	 */
	public Sensor getMotionSensor() {
		return motionSensor;
	}

	/**
	 * Set the motionSensor status
	 * @param motionSensorStatus the motionSensorStatus to set
	 */
	public void setMotionSensor(int motionSensorStatus) {
		this.motionSensor.status = motionSensorStatus;
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
