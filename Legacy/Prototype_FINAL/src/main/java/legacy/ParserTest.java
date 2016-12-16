package legacy;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.util.Scanner;
import parser.*;

/**
 * The ParserTest will take in a CSV file and test the creation of a parsing a CSV into a DataObject. 
 * It will then add the DataObject to the KieSession and fire the rules to make sure the CSV imported correctly and that the correct rules fired.
 * This was used for testing purposes and is not used in FINAL design.
 * 
 * @author Walrus
 */
public class ParserTest 
{

    public static final void main(String[] args) 
    {
    	//Create a new parser
    	Parser parser = new Parser();
    	
    		try {
    			DataObject dataObj = parser.parse("FireAlarmSystemCVS.csv");
    			System.out.println(dataObj.toString());
        	
            //Load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
    	    KieSession kSession = kContainer.newKieSession("ksession-rules");
    	    
            //Instantiate the scanner and alarms
        	Scanner scanner = new Scanner(System.in);
            
        	//Add the DataObject to the KieSession
        	kSession.insert(dataObj);
        	
            //Fire the rules engine
            kSession.fireAllRules(); 
            
            //Prompts the user to fire all rules by typing 'Go'
            System.out.println("Enter 'Go' to test rules. Enter any other character to exit");
            
            //Continues to fire rules as the user enters 'Go'
            while (scanner.next().equals("Go"))
            {
            	//Update moves the data input to the next line in the CSV
            	parser.update(dataObj);
    			System.out.println(dataObj.toString());
            	
            	//Create a new session and add rules to it
            	kSession = kContainer.newKieSession("ksession-rules");  
            	kSession.insert(dataObj);
            	
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