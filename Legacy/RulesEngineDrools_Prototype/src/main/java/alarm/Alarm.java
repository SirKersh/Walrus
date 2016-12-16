package alarm;

import java.util.ArrayList;
import sensor.Sensor;

/**
 * Alarms contain sensors which can be triggered by environment data. Sensors and alarms are monitored by the Rules Tables.
 */
public abstract class Alarm 
{
	//Boolean constants representing that an alarm is triggered or not triggered
	public static final int NOT_TRIGGERED = 0;
	public static final int TRIGGERED = 1;
	
	//The current status of the alarm
	public int status;
	
	//The location of the alarm
	public String location;
	
	//The list of all sensors that an alarm contains
	public ArrayList<Sensor> sensors = new ArrayList<Sensor>();
	
	/**
	 * Return all the sensors contained in an alarm
	 * @return the sensors contained in an alarm
	 */
	public abstract ArrayList<Sensor> getSensors();

	/**
	 * Add a sensor to the list of sensors in an alarm
	 * @param sensor the sensor to add
	 */
	public abstract void addSensor(Sensor sensor);

	/**
	 * Set the status of the alarm
	 * @param status the status of the alarm. 1 for triggered, 0 for not triggered
	 */
	public abstract void setStatus(int status);
	
	/**
	 * Return the status of the alarm
	 * @return status of the alarm
	 */
	public abstract int getStatus();
	
	/**
	 * Set the location of the alarm
	 * @param location the location of the alarm
	 */
	public abstract void setLocation(String location);
	
	/**
	 * Return the location of the alarm.
	 * @return the location of the alarm
	 */
	public abstract String getLocation();
	
}
