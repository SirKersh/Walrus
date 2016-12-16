package driver;

import org.kie.api.runtime.KieSession;
import parser.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import ruleContainer.RuleManager;
import ruleContainer.KieManager;
import ruleContainer.RuleCreator;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Prototype is the power house of the Drools Rules Framework. It contains a majority of the functionality that the Driver uses.
 * It contains the:
 * - import functionality
 * - update functionality
 * 
 * @author Walrus
 *
 */
public class Prototype {
	DataObjectCollection dataObjCol;
	DataObjectCollection newData;
	KieSession kSession;
	boolean continueFiring = false;

	/**
	 * Constructor for Prototype
	 */
	public Prototype()
	{
		dataObjCol = new DataObjectCollection();
		newData = new DataObjectCollection();

	}

	/**
	 * Creates a new .drl rule file and adds it to the RuleManager.
	 * @param rf RuleCreator used for creating a rule
	 * @param ruleEditor RuleManager used for creating a rule
	 */
	public void createNewRules(RuleCreator rf,RuleManager ruleEditor)
	{
		try {
			String fileName;
			fileName = rf.createRule();
			ruleEditor.addRule(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Imports a singular log file and adds the created DataObject to the collection of DataObjects.
	 * @param scanner The scanner used by the Driver.
	 * @throws IOException
	 */
	public void importLogFile(Scanner scanner) throws IOException
	{
		Parser parser = new Parser();
		System.out.println("Please input the csv filename");
		String csvFile = scanner.nextLine();

		while (!(new File("Logs//" + csvFile + ".csv").exists())&&!(csvFile.equals("EXIT"))) {
			System.out.println("No file found!");
			System.out.println("Please input the csv filename or enter 'EXIT' to leave");
			csvFile = scanner.nextLine();

		}
		if(csvFile.equals("EXIT"))
		{
			System.out.println("No file has been imported.");

		}
		else if(dataObjCol.getObject(csvFile) != null)
		{
			System.out.println("The system already contains the logfile " + csvFile + ".csv");
		}
		else
		{
			dataObjCol.add(parser.parse(csvFile + ".csv"));
			System.out.println("LogFile " + csvFile + ".csv has been imported.");
		}
	}

	/**
	 * Imports a folder of log files and adds the created DataObjects to the collection of DataObjects.
	 * @param scanner The scanner used by the Driver.
	 * @throws IOException
	 */
	public void importLogFiles(Scanner scanner) throws IOException
	{
		System.out.println("Please input the folder filename");
		String folderName = scanner.nextLine();


		while (!(new File(folderName).exists())&&!(folderName.equals("EXIT"))) {
			System.out.println("No file found!");
			System.out.println("Please input the folder name or enter 'EXIT' to leave");
			folderName = scanner.nextLine();
		}
		if(folderName.equals("EXIT"))
		{
			System.out.println("No file has been imported.");
		}
		else
		{
			for (final File fileEntry : new File(folderName).listFiles()) {
				if(dataObjCol.getObject(fileEntry.getName().substring(0, fileEntry.getName().length() - 4)) != null)
				{
					System.out.println("The system already contains the logfile " + fileEntry.getName());
				}
				else
				{
					Parser parser = new Parser();
					System.out.println("LogFile " + fileEntry.getName() + " has been imported.");
					dataObjCol.add(parser.parse(fileEntry.getName()));
				}
			}

		}
	}

	/**
	 * Creates new Data as a DataObject and adds it to the collection of new Data DataObjects.
	 * @param scanner The scanner used by the Driver.
	 * @throws IOException
	 */
	public void importNewData(Scanner scanner) throws IOException
	{
		DataObject x;
		HashMap<String, String> hm = new HashMap<String,String>();
		int no = -1;

		System.out.println("Input a file name of the new Data.");
		String name = scanner.nextLine();
		while(no == -1){
			System.out.println("How many fields do you want?");

			try{
				no = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e){
				System.out.println("Please enter a valid integer.");
				no = -1;
			}
		}
		for(int i = 0;i<no;i++)
		{
			System.out.println("Please name the field");
			String feild = scanner.nextLine();	
			hm.put(feild, "");
		}

		x = new DataObject(hm, name, null);
		newData.add(x);
	}

	/**
	 * Runs through ALL log files currently imported into the engine. updateAll finishes execution when all files have had all log lines read.
	 * @throws IOException
	 */
	public void updateAll() throws IOException
	{
		if (dataObjCol.size() > 0) {
			while(dataObjCol.size()>0)
			{
				System.out.println("Fire Rules: ");

				/*
				 * Create a new KieSession and insert the collection of DataObjects into it.
				 * All rules will fire based on these DataObjects.
				 * Once rules have fired the rules will be disposed.
				 */
				kSession = KieManager.kBase.newKieSession();
				kSession.insert(dataObjCol);
				kSession.insert(this);
				kSession.fireAllRules();
				kSession.dispose();

				try {
					Thread.sleep(1000);
					System.out.println("\n");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				// Update the specific parser
				Iterator<DataObject> it = dataObjCol.getCollection().iterator();
				while(it.hasNext())
				{
					DataObject obj = it.next();
					if(!(obj instanceof ActionObject))
					{
						boolean x = obj.getParser().update(obj);
						if (x == true)
							it.remove();	
					}
					else
					{
						it.remove();
					}

				}
			} 
		}else
			System.out.println("No Logfile Selected.");
	}

	/**
	 * Runs through ALL log files currently imported into the engine. updateOnce finishes execution when all files have had one log lines read.
	 * If one log file runs out of lines before another, the other log file will continue to update and the log that ran out will expire.
	 * @throws IOException
	 */
	public void updateOnce() throws IOException
	{
		continueFiring = false;
		if (dataObjCol.size() > 0) {

			/*
			 * Create a new KieSession and insert the collection of DataObjects into it.
			 * All rules will fire based on these DataObjects.
			 * Once rules have fired the rules will be disposed.
			 */
			kSession = KieManager.kBase.newKieSession();

			kSession.insert(dataObjCol);
			kSession.insert(this);

			kSession.fireAllRules();

			// Trash the rules
			kSession.dispose();

			// Update the specific parser
			Iterator<DataObject> it = dataObjCol.getCollection().iterator();
			while(it.hasNext())
			{
				DataObject obj = it.next();
				if(!(obj instanceof ActionObject))
				{
					boolean x = obj.getParser().update(obj);
					if (x == true)
						it.remove();	
				}
				else
				{
					it.remove();
				}

			}

		} else
			System.out.println("No Logfile Selected.");
	}


	/**
	 * Checks to make sure that at least one log file has been imported into the system.
	 * @return true if there is at least one log file in the system.
	 */
	public boolean checkForLogFile()
	{
		return (dataObjCol.size()>0);
	}

	/**
	 * Returns the DataObjectCollectionArrayList of DataObjects.
	 * @return the DataObjectCollectionArrayList of DataObjects
	 */
	public DataObjectCollection getDataObjCol()
	{
		return dataObjCol;
	}

	/**
	 * Checks to make sure that at least one new Data file has been imported into the system.
	 * @return true if there is at least one new Data file in the system.
	 */
	public boolean checkNewData()
	{
		return (newData.size()>0);
	}

	/**
	 * Returns the DataObjectCollectionArrayList of newData.
	 * @return the DataObjectCollectionArrayList of newData
	 */
	public DataObjectCollection getNewData()
	{
		return newData;
	}

	/**
	 * Inserts an Object into the KieSession.
	 * @param obj the object being inserted into the KieSession
	 */
	public void insertToKie(Object obj)
	{
		kSession.insert(obj);
	}

}
