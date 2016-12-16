package driver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import ruleContainer.RuleManager;
import ruleContainer.KieManager;
import ruleContainer.RuleCreator;
import ruleContainer.RuleTextEditor;

/**
 * The Driver class runs the Drools Rules Framework. It takes in input and will execute commands based on user input. 
 * Run this class to use the framework.
 * 
 * @author Walrus
 *
 */
public class Driver {

	public static void main(String[] args) 
	{
		//Instantiate all variables
		Prototype x = new Prototype();
		Scanner scanner = new Scanner(System.in);
		RuleManager rManager = new RuleManager();

		try {
			KieManager.loadAllRules();
		} catch (FileNotFoundException e2) {
			System.out.println("Unable to load drools files");
		}
		
		int option = 0;
		System.out.println("Welcome to the Rules Engine Prototype.");
		//Prompts the user via the menu for an action
		while (option != 11) {

			System.out.println("Please input the number of what you would like to do.");
			System.out.println("1. Create a new rule.");
			System.out.println("2. Select a Logfile.");
			System.out.println("3. Select a folder containing multiple logfiles");
			System.out.println("4. Add new data.");
			System.out.println("5. Run through one line of the LogFile.");
			System.out.println("6. Run through entire LogFile.");
			System.out.println("7. List all active rules.");
			System.out.println("8. List all inactive rules.");
			System.out.println("9. Toggle rules on/off.");
			System.out.println("10. Open Rules Editor");
			System.out.println("11. Exit program.");

			String s = scanner.nextLine();
			try{
				option = Integer.parseInt(s);
			} catch (NumberFormatException e){
				option = -1;
			}
			switch (option) {
			/*
			 * Case 1 will create a new drools rule (.drl file) specified by the user. See RuleCreator for more information.
			 * At least one log file must be entered in order to create a rule.
			 */
			case 1:
				if(x.checkForLogFile())
				{
					System.out.println("Please input a filename.");
					String filename = scanner.nextLine();
					RuleCreator rf = new RuleCreator(filename, "src/main/resources/rules", scanner, x.getDataObjCol(),x.getNewData());
					x.createNewRules(rf,rManager);
				}
				else
					System.out.println("No Logfiles in the system. Please choose option 2 or 3 to load log files.");
				break;
			/*
			 * Case 2 will import a singular log file into the engine. 
			 */
			case 2:
				try {
					x.importLogFile(scanner);
				} catch (IOException e) {
					System.out.println("Unable to locate LogFile.");
				}
				break;
			/*
			 * Case 3 will import a folder containing a drl file(s), importing all files.
			 */
			case 3:
				try {
					x.importLogFiles(scanner);
				} catch (IOException e) {
					System.out.println(e);
					System.out.println("Unable to locate LogFile.");
				}
				break;
			/*
			 * Case 4 will allow the user to create new data which can be used for rule chaining and in rule creation.
			 * New data that is used in rule creation must be created before Case 1 is called.
			 */
			case 4:
				try {
					x.importNewData(scanner);
				} catch (IOException e1) {
					System.out.println("Unable to locate LogFile.");
				}
				break;
			/*
			 * Case 5 fires a singular line from the CSV file. Firing a line from a CSV file simulates live data coming into the engine.
			 */
			case 5:
				try {
					x.updateOnce();
				} catch (IOException e) {
					System.out.println("The LogFile cannot be read.");
				}
				break;
			/*
			 * Case 6 fires all lines from the CSV file. Firing a line from a CSV file simulates live data coming into the engine.
			 */
			case 6:
				try {
					x.updateAll();
				} catch (IOException e) {
					System.out.println("The LogFile cannot be read.");
				}
				break;
			/*
			 * Case 7 displays all active rule files currently loaded into the rule engine.
			 */
			case 7:
				System.out.println(rManager.displayAllActiveRules());
				break;
			/*
			 * Case 8 displays all the inactive rule files currently NOT loaded into the rule engine.
			 */
			case 8:
				System.out.println(rManager.displayAllInActiveRules());
				break;
			/*
			 * Case 9 allows the user to toggle rule files on and off. A toggled on rule file will fire and a toggled off rule file will not.
			 */
			case 9:
				try {
					rManager.toggleRules(scanner);
				} catch (FileNotFoundException e) {
					System.out.println("The rule indicated cannot be found.");
				}
				break;
			/*
			 * Case 10 opens up a text editor to modify existing rules.
			 */
			case 10:
				new RuleTextEditor();
				break;
			/*
			 * Case 11 ends the program terminating the engine.
			 */
			case 11:
				System.out.println("End of Program");
				System.exit(0);
				break;
			default:
				System.out.println("Invalid Input.");
				break;
			}
			System.out.println();
		}
		scanner.close();


	}

}
