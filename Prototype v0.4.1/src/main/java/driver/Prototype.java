package driver;

import org.kie.api.runtime.KieSession;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import parser.*;
import ruleContainer.RuleManager;
import ruleContainer.KieManager;
import ruleContainer.RuleCreator;
import java.util.HashMap;
import java.util.Iterator;



public class Prototype {
	DataObjectCollectionArrayList dataObjCol;
	DataObjectCollectionArrayList newData;
	KieSession kSession;

	public Prototype()
	{
		dataObjCol = new DataObjectCollectionArrayList();
		newData = new DataObjectCollectionArrayList();
	}

	public void createNewRules(RuleCreator rf,RuleManager ruleEditor)
	{
		try {
			String fileName;
			fileName = rf.createRule();
			ruleEditor.addRule(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void importLogFile(Scanner scanner) throws IOException
	{
		Parser parser = new Parser();
		System.out.println("Please input the csv filename");
		String csvFile = scanner.nextLine();

		while (!(new File("Logs//" + csvFile + ".csv").exists()) && !(csvFile.equals("EXIT"))) 
		{
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

	public void updateAll() throws IOException
	{

		if (dataObjCol.size() > 0) {
			while(dataObjCol.size()>0)
			{
				System.out.println("Fire Rules: ");
				kSession = KieManager.kBase.newKieSession();
				kSession.insert(dataObjCol);
				kSession.fireAllRules();
				kSession.dispose();

				try {
					Thread.sleep(1000);
					System.out.println("\n");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
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

	public void updateOnce() throws IOException
	{
		if (dataObjCol.size() > 0) {

			kSession = KieManager.kBase.newKieSession();
			// For testing purposes only

			kSession.insert(dataObjCol);

			// Fire the rules engine
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


	public boolean checkForLogFile()
	{
		return (dataObjCol.size()>0);
	}

	public DataObjectCollectionArrayList getDataObjCol()
	{
		return dataObjCol;
	}

	public boolean checkNewData()
	{
		return (newData.size()>0);
	}

	public DataObjectCollectionArrayList getNewData()
	{
		return newData;
	}

}
