package driver;

import java.util.Random;

import alarm.Alarm;
import sensor.*;

/**
 * Randomizer randomizes the statuses of the sensors contained in all the alarms. This is strictly used for simulation purposes and would be replaced by real data feeds when implemented.
 */
public class Randomizer 
{

	/**
	 * Looks through all of the alarms in a rules engine and then through all of the sensors in each alarm and randomizes a status for that sensor.
	 * Different sensors require different status updates, I.E smoke sensors are binary where they trigger on or off while heat sensors take in a heat index and trigger based on the temperature.
	 * @param alarms
	 */
	public void randomize(Alarm[] alarms) 
	{
		for(Alarm alarm : alarms)
		{
			for(Sensor sensor : alarm.getSensors())
			{
				Random rand = new Random();

				if(sensor instanceof SmokeSensor || sensor instanceof MotionSensor)
					//set the status of binary sensors
					sensor.setStatus(rand.nextInt(2));
				else if(sensor instanceof HeatSensor)
				{
					//heat sensors read in temperature. When a temperature reads in at a high level it will trigger the sensor.
					((HeatSensor) sensor).setHeatIndex(rand.nextInt(2000));
				}
			}
		}
		
	}

}
