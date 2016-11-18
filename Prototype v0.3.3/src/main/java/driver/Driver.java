package driver;

import java.util.Scanner;
import ruleContainer.RuleManager;
import ruleContainer.KieManager;
import ruleContainer.RuleCreator;
import ruleContainer.RuleTextEditor;

public class Driver {

	public static void main(String[] args) {
		Prototype x = new Prototype();
		Scanner scanner = new Scanner(System.in);
		RuleManager rManager = new RuleManager();
		boolean didCallTwo = false;
		try {
			KieManager.loadAllRules();
			
			int option = 0;
			System.out.println("Welcome to the Rules Engine Prototype.");
			while (option != 10) {

				System.out.println("Please input the number of what you would like to do.");
				System.out.println("1. Create a new rule.");
				System.out.println("2. Select a Logfile.");
				System.out.println("3. Add new data.");
				System.out.println("4. Run through one line of the LogFile.");
				System.out.println("5. Run through entire LogFile.");
				System.out.println("6. List all active rules.");
				System.out.println("7. List all inactive rules.");
				System.out.println("8. Toggle rules on/off.");
				System.out.println("9. Open Rules Editor");
				System.out.println("10. Exit program.");

				String s = scanner.nextLine();
				option = Integer.parseInt(s);
				switch (option) {
				case 1:
					if(x.checkForLogFile())
					{
						System.out.println("Please input a filename.");
						String filename = scanner.nextLine();
						RuleCreator rf = new RuleCreator(filename, "src/main/resources/rules", scanner, x.getDataObjCol(),x.getNewData());
						x.createNewRules(rf,rManager);
					}
					else
						System.out.println("No Logfiles or Data in the system. Please choose option 2 to load log files.");
					break;
				case 2:
					x.importLogFile(scanner);
					break;
				case 3:
					x.importNewData(scanner);
					break;
				case 4:
					x.updateOnce();
					break;
				case 5:
					x.updateAll();
					break;
				case 6:
					System.out.println(rManager.displayAllActiveRules());
					break;
				case 7:
					System.out.println(rManager.displayAllInActiveRules());
					break;
				case 8:
					rManager.toggleRules(scanner);
					break;
				case 9:
					new RuleTextEditor();
					break;
				case 10:
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
