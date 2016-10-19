package driver;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.util.Scanner;
import parser.*;

public class ParserTest 
{

    public static final void main(String[] args) 
    {
    	Parser parser = new Parser();
    	
    		try {
    			DataObject dataObj = parser.parse("mysensor.csv");
    			//System.out.println(dataObj.toString());
        	
            // load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("ksession-dtables");
        	
            //Instantiate the scanner and alarms
        	Scanner scanner = new Scanner(System.in);
            
        	kSession.insert(dataObj);
        	
            //Fire the rules engine
            kSession.fireAllRules(); 
            
            //Prompts the user to fire all rules by typing 'Go'
            System.out.println("Enter 'Go' to test rules. Enter any other character to exit");
            
            //Continues to fire rules as the user enters 'Go'
            while (scanner.next().equals("Go"))
            {
            	parser.update(dataObj);
    			//System.out.println(dataObj.toString());
            	
            	//create a new session and add rules to it
            	kSession = kContainer.newKieSession("ksession-dtables");         
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