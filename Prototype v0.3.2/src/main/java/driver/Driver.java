package driver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import ruleContainer.RuleManager;
import ruleContainer.KieManager;
import ruleContainer.RuleFactory;
import ruleContainer.RuleTextEditor;

public class Driver {

	public static void main(String[] args) {
		Prototype x = new Prototype();
		Scanner scanner = new Scanner(System.in);
		RuleManager rManager = new RuleManager();
		
			try {
				KieManager.loadAllRules();
			} catch (FileNotFoundException e) {
				System.out.println("Unable to load drools files.");
			}
			
			int option = 0;
			System.out.println("Welcome to the Rules Engine Prototype.");
			while (option != 9) {

				System.out.println("Please input the number of what you would like to do.");
				System.out.println("1. Create a new rule.");
				System.out.println("2. Select a Logfile.");
				System.out.println("3. Run through one line of the LogFile.");
				System.out.println("4. Run through entire LogFile.");
				System.out.println("5. List all active rules.");
				System.out.println("6. List all inactive rules.");
				System.out.println("7. Toggle rules on/off.");
				System.out.println("8. Open Rules Editor");
				System.out.println("9. Exit program.");

				String s = scanner.nextLine();
				try{
					option = Integer.parseInt(s);
				} catch (NumberFormatException e){
					option = -1;
				}
				switch (option) {
				case 1:
					if(x.checkForLogFile())
					{
						System.out.println("Please input a filename.");
						String filename = scanner.nextLine();
						RuleFactory rf = new RuleFactory(filename, "src/main/resources/rules", scanner);
						x.createNewRules(rf,rManager);
					}
					else
						System.out.println("No Logfiles in the system.");
					break;
				case 2:
					try {
						x.importLogFile(scanner);
					} catch (IOException e) {
						System.out.println("Unable to locate LogFile.");
					}
					break;
				case 3:
					try {
						x.updateOnce();
					} catch (IOException e) {
						System.out.println("The LogFile cannot be read.");
					}
					break;
				case 4:
					try {
						x.updateAll();
					} catch (IOException e) {
						System.out.println("The LogFile cannot be read.");
					}
					break;
				case 5:
					System.out.println(rManager.displayAllActiveRules());
					break;
				case 6:
					System.out.println(rManager.displayAllInActiveRules());
					break;
				case 7:
					try {
						rManager.toggleRules(scanner);
					} catch (FileNotFoundException e) {
						System.out.println("The rule indicated cannot be found.");
					}
					break;
				case 8:
					new RuleTextEditor();
					break;
				case 9:
					System.out.println("End of Program");
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
