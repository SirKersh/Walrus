package driver;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import alarm.Alarm;
import alarm.FireAlarm;
import alarm.MotionAlarm;

import java.util.Scanner;

public class DecisionTableTest 
{

    public static final void main(String[] args) 
    {
        try {
        	//array of alarms used for randomizing statuses and adding rules
        	Alarm[] alarms = new Alarm[3];
        	
        	Randomizer randomizer = new Randomizer();
        	
            // load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("ksession-dtables");
        	
            //Instantiate the scanner and alarms
        	Scanner scanner = new Scanner(System.in);
            FireAlarm kitchenFireAlarm = new FireAlarm("Kitchen");
            FireAlarm bedroomFireAlarm = new FireAlarm("Bedroom");
            MotionAlarm vaultMotionSensor = new MotionAlarm("Vault");
            
            //add the alarms to an array for easy randomization and deployment into rules engine
            alarms[0] = kitchenFireAlarm;
            alarms[1] = bedroomFireAlarm;
            alarms[2] = vaultMotionSensor;
           
            //Add the alarms to the rules engine
            for(Alarm alarm : alarms)
            {
            	kSession.insert(alarm);
            }

            //Fire the rules engine
            kSession.fireAllRules(); 
            
            //Prompts the user to fire all rules by typing 'Go'
            System.out.println("Enter 'Go' to test rules. Enter any other character to exit");
            
            //Continues to fire rules as the user enters 'Go'
            while (scanner.next().equals("Go"))
            {
            	//randomize the status of the sensors in the alarms to signal data actively effecting the sensor
            	randomizer.randomize(alarms);
            	
            	//create a new session and add rules to it
            	kSession = kContainer.newKieSession("ksession-dtables");
            	
            	//Add the alarms to the rules engine
                for(Alarm alarm : alarms)
                {
                	kSession.insert(alarm);
                }
            
            	//Fire the rules engine
            	kSession.fireAllRules();
            	
            	//Trash the rules
            	kSession.dispose();
            	
            	//Prompts the user to fire all rules by typing 'Go'
            	System.out.println("Enter 'Go' to test rules. Enter any other character to exit");

            }
            
            System.out.println("\nThanks for Testing...");
            
            //Close the scanner
            scanner.close();
            
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
     	

}