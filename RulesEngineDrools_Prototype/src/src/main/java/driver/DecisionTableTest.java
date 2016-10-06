package driver;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.util.Scanner;
import sensor.*;

public class DecisionTableTest 
{

    public static final void main(String[] args) 
    {
        try {
            // load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("ksession-dtables");
        	
            //Instantiate the scanner and sensors
        	Scanner scanner = new Scanner(System.in);
            SmokeSensor kitchenSmokeSensor = new SmokeSensor("Kitchen"); 
            HeatSensor kitchenHeatSensor = new HeatSensor("Kitchen");
            SmokeSensor LivingQuartersSmokeSensor = new SmokeSensor("Living Quarters"); 
            HeatSensor LivingQuartersHeatSensor = new HeatSensor("Living Quarters");
           
            //Add the sensors to the rules engine
            kSession.insert(kitchenSmokeSensor);
            kSession.insert(kitchenHeatSensor);
            kSession.insert(LivingQuartersSmokeSensor);
            kSession.insert(LivingQuartersHeatSensor);

            //Fire the rules engine
            kSession.fireAllRules(); 
            
            //Prompts the user to fire all rules by pressing 1
            System.out.println("Enter '1' to test rules. Enter any other character to exit");
            
            //Continues to fire rules as the user presses 1
            while (scanner.next().equals("1"))
            {
            	//randomize the status of the sensor to signal data actively effecting the sensor
            	kitchenSmokeSensor.randomizeStatus();
            	kitchenHeatSensor.randomizeStatus();
            	LivingQuartersSmokeSensor.randomizeStatus();
            	LivingQuartersHeatSensor.randomizeStatus();
            	
            	//Prompts the user to fire all rules by pressing 1
            	System.out.println("Enter '1' to test rules. Enter any other character to exit");
            	
            	//create a new session and add rules to it
            	kSession = kContainer.newKieSession("ksession-dtables");            	
            	kSession.insert(kitchenSmokeSensor);
            	kSession.insert(kitchenHeatSensor);
            	kSession.insert(LivingQuartersSmokeSensor);
                kSession.insert(LivingQuartersHeatSensor);
            
            	//Fire the rules engine
            	kSession.fireAllRules();
            	
            	//Trash the rules
            	kSession.dispose();

            }
            
            System.out.println("\nThanks for Testing...");
            
            scanner.close();
            
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
     	

}