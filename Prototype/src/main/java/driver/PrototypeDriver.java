package driver;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import parser.*;
import ruleCreator.RuleFactory;

public class PrototypeDriver {

	public static void main(String[] args) {
		Parser parser = new Parser();
		Scanner scanner = new Scanner(System.in);
		DataObject dataObj = null;
		try {
			// load up the knowledge base
			KieServices ks = KieServices.Factory.get();
			KieContainer kContainer = ks.getKieClasspathContainer();
			KieSession kSession = kContainer.newKieSession("ksession-rules");

			kSession.insert(dataObj);

			// Fire the rules engine
			kSession.fireAllRules();
			int option = 0;
			System.out.println("Welcome to the Rules Engine Prototype.");
			while (option != 5) {
				System.out.println("Please input the number of what you would like to do.");
				System.out.println("1. Create a new rule.");
				System.out.println("2. Select a Logfile.");
				System.out.println("3. Run through one line of the LogFile.");
				System.out.println("4. Run through entire LogFile.");
				System.out.println("5. Exit program.");

				String s = scanner.nextLine();
				option = Integer.parseInt(s);
				switch (option) {
				case 1:
					System.out.println("Please input a filename.");
					String filename = scanner.nextLine();
					RuleFactory rf = new RuleFactory(filename, "src/main/resources/rules",scanner);
					
					try {
						rf.createRule();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					break;
				case 2:
					dataObj = selectLogFile(scanner, parser);
					break;
				case 3:
					if (dataObj != null) {
						// create a new session and add rules to it
						ks = KieServices.Factory.get();
						kContainer = ks.getKieClasspathContainer();
						kSession = kContainer.newKieSession("ksession-rules");
						// kSession =
						// kContainer.newKieSession("ksession-dtables");
						kSession.insert(dataObj);

						// Fire the rules engine
						kSession.fireAllRules();

						// Trash the rules
						kSession.dispose();
						boolean x = parser.update(dataObj);
						if (x == true) // logfile is out of lines
						{
							dataObj = null;
						}
					} else
						System.out.println("No Logfile Selected.");
					break;
				case 4:
					if (dataObj != null) {
					while(dataObj!=null)
					{
						// create a new session and add rules to it
						ks = KieServices.Factory.get();
						kContainer = ks.getKieClasspathContainer();
						kSession = kContainer.newKieSession("ksession-rules");

						kSession.insert(dataObj);

						// Fire the rules engine
						kSession.fireAllRules();

						// Trash the rules
						kSession.dispose();
						boolean x = parser.update(dataObj);
						if (x == true) // logfile is out of lines
						{
							dataObj = null;
						}
					}
					}
					else
					{
						System.out.println("No Logfile Selected.");
					}
					break;
				case 5:
					//End Program
					break;
				default:
					System.out.println("Invalid Input.");
					break;
				}
				System.out.println();

				// System.out.println(dataObj.toString());

			}

			System.out.println("\nThanks for Testing...");

			// Close the scanner
			scanner.close();

		} catch (Throwable t) {
			t.printStackTrace();
		}

	}

	public static DataObject selectLogFile(Scanner scanner, Parser parser) throws IOException {
		System.out.println("Please input the csv filename");
		String csvFile = scanner.nextLine();
		while (!(new File(csvFile + ".csv").exists())) {
			System.out.println("No file found!");
			System.out.println("Please input the csv filename");
			csvFile = scanner.nextLine();
		}
		System.out.println("LogFile " + csvFile + ".csv has been Imported.");
		return parser.parse(csvFile + ".csv");
	}

	public static void runOnce(DataObject dataObj, Parser parser, KieSession kSession, KieContainer kContainer)
			throws IOException {

	}

}
