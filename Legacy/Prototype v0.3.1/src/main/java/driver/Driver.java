package driver;

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
				option = Integer.parseInt(s);
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
					x.importLogFile(scanner);
					break;
				case 3:
					x.updateOnce();
					break;
				case 4:
					x.updateAll();
					break;
				case 5:
					System.out.println(rManager.displayAllActiveRules());
					break;
				case 6:
					System.out.println(rManager.displayAllInActiveRules());
					break;
				case 7:
					rManager.toggleRules(scanner);
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

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

}
