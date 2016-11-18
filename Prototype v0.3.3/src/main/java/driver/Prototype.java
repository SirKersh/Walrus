package driver;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import parser.*;
import ruleContainer.RuleManager;
import ruleContainer.KieManager;
import ruleContainer.RuleCreator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;



public class Prototype {
	ArrayList<Parser> parsers;
	//DataObjectCollection dataObjCol;
	DataObjectCollectionArrayList dataObjCol;
	DataObjectCollectionArrayList newData;
	KieSession kSession;

	public Prototype()
	{
		parsers = new ArrayList<Parser>();
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
		//boolean fileFound = selectLogFile(scanner, parser,dataObj);
		boolean fileFound = selectLogFile(scanner, parser);
		if(fileFound)
		{
			parsers.add(parser);
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

	private boolean selectLogFile(Scanner scanner, Parser parser)
			throws IOException {
		System.out.println("Please input the csv filename");
		String csvFile = scanner.nextLine();
		while (!(new File(csvFile + ".csv").exists())&&!(csvFile.equals("EXIT"))) {
			System.out.println("No file found!");
			System.out.println("Please input the csv filename or enter 'EXIT' to leave");
			csvFile = scanner.nextLine();

		}
		if(csvFile.equals("EXIT"))
		{
			System.out.println("No file has been imported.");
			return false;
		}
		else
		{
			System.out.println("LogFile " + csvFile + ".csv has been imported.");
			//dataObjCol.addObject(csvFile, (parser.parse(csvFile + ".csv")));
			dataObjCol.add(parser.parse(csvFile + ".csv"));
			return true;
		}
	}

	public void updateAll() throws IOException
	{

		if (dataObjCol.size() > 0) {
			while(dataObjCol.size()>0)
			{
				kSession = KieManager.kBase.newKieSession();
				kSession.insert(dataObjCol);
				kSession.fireAllRules();
				kSession.dispose();
				// Update the specific parser

				///// TELL ME WHAT THIS IS SUPPOSED TO DO PLS CAUSE THIS IS VERY IMPORTANT
				///// I DUNNO WHY YA LEFT IT TO JUST EXIST ALONE LIKE THIS
				Iterator<DataObject> it = dataObjCol.getCollection().iterator(); //GET ITERATOR FOR ALL OF THE DATA OBJECTS
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

			//kSession = kBase.newKieSession();
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
