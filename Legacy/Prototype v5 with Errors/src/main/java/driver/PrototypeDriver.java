package driver;

import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import parser.*;
import ruleContainer.RuleEditor;
import ruleContainer.RuleFactory;
import ruleContainer.RuleTextEditor;

import java.util.ArrayList;

public class PrototypeDriver {
	// load up the knowledge base
	public static KieServices ks;
	static KieContainer kContainer;
	static KieSession kSession;
	public static KieFileSystem kfs;
	static KieBase kBase;
/*	static ArrayList<String> activeRules = new ArrayList<String>();
	static ArrayList<String> inActiveRules = new ArrayList<String>();*/
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		ArrayList<DataObject> dataObj = new ArrayList<DataObject>();
		ArrayList<Parser> parsers = new ArrayList<Parser>();
		RuleEditor ruleEditor = new RuleEditor(kfs,ks);
		try {
			ks = KieServices.Factory.get();
			kContainer = ks.getKieClasspathContainer();
			kfs = ks.newKieFileSystem();
			
			//loads all the rules into the system then updates the kfs.
			ruleEditor.loadAllRules();
			rebuildKFS();
			
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
					System.out.println("Please input a filename.");
					String filename = scanner.nextLine();
					RuleFactory rf = new RuleFactory(filename, "src/main/resources/rules", scanner);

					try {
						String fileName;
						fileName = rf.createRule();
						
						ruleEditor.addRule(fileName);
						rebuildKFS();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					break;
				case 2:
					// Create new parser for the Log File
					Parser parser = new Parser();
					parsers.add(parser);
					selectLogFile(scanner, parser, dataObj);
					break;
				case 3:
					if (dataObj.size() > 0) {
						for (int i = 0; i < dataObj.size(); i++) {
							// create a new session and add rules to it
							kSession = kBase.newKieSession();
							kSession.insert(dataObj.get(i));

							// Fire the rules engine
							kSession.fireAllRules();

							// Trash the rules
							kSession.dispose();
							
							// Update the specific parser
							boolean x = parsers.get(i).update(dataObj.get(i));
							
							// If logfile is out of lines
							if (x == true) {
								dataObj.remove(i);
								parsers.remove(i);
								System.out.println("Remove");
							}
						}
					} else
						System.out.println("No Logfile Selected.");
					break;
				case 4:
					if (dataObj.size() > 0) {
						while (dataObj.size() > 0) {
							for (int i = 0; i < dataObj.size(); i++) {
								// create a new session and add rules to it
								kSession = kBase.newKieSession();
								kSession.insert(dataObj.get(i));

								// Fire the rules engine
								kSession.fireAllRules();
								// Trash the rules
								kSession.dispose();
								// Update the specific parser
								boolean x = parsers.get(i).update(dataObj.get(i));
								// If logfile is out of lines
								if (x == true) {
									dataObj.remove(i);
									parsers.remove(i);
								}
							}
						}
					} else {
						System.out.println("No Logfile Selected.");
					}
					break;
				case 5:
					System.out.println(ruleEditor.displayAllActiveRules());
					break;
				case 6:
					System.out.println(ruleEditor.displayAllInActiveRules());
					break;
				case 7:
					ruleEditor.toggleRules(scanner);
					rebuildKFS();
					break;
				case 8:
					new RuleTextEditor(new Prototype());
					
				case 9:
					// End Program
					break;
				
				default:
					System.out.println("Invalid Input.");
					break;
				}
				System.out.println();
			}

			System.out.println("\nThanks for Testing...");

			// Close the scanner
			scanner.close();

		} catch (Throwable t) {
			t.printStackTrace();
		}

	}

	public static void selectLogFile(Scanner scanner, Parser parser, ArrayList<DataObject> dataObj) throws IOException {
		System.out.println("Please input the csv filename");
		String csvFile = scanner.nextLine();
		while (!(new File(csvFile + ".csv").exists())) {
			System.out.println("No file found!");
			System.out.println("Please input the csv filename");
			csvFile = scanner.nextLine();
		}
		System.out.println("LogFile " + csvFile + ".csv has been Imported.");

		dataObj.add(parser.parse(csvFile + ".csv"));
	}
	
	static public void rebuildKFS(){
		KieBuilder kieBuilder = ks.newKieBuilder(kfs).buildAll();
		
		Results results = kieBuilder.getResults();
		if (results.hasMessages(Message.Level.ERROR)) {
			System.out.println(results.getMessages());
			throw new IllegalStateException("### errors ###");
		}
		
		KieContainer kContainer = ks.newKieContainer(ks.getRepository().getDefaultReleaseId());

		KieBaseConfiguration config = ks.newKieBaseConfiguration();
	    config.setOption(EventProcessingOption.STREAM);

	    kBase = kContainer.newKieBase( config );
	}
}
