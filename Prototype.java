package driver;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import parser.*;
import ruleContainer.RuleManager;
import ruleContainer.KieManager;
import ruleContainer.RuleFactory;
import java.util.ArrayList;
import java.util.Iterator;



public class Prototype {
	ArrayList<Parser> parsers;
	//DataObjectCollection dataObjCol;
	DataObjectCollectionArrayList dataObjCol;
	KieSession kSession;

	public Prototype()
	{
		parsers = new ArrayList<Parser>();
		dataObjCol = new DataObjectCollectionArrayList();
	}

	public void createNewRules(RuleFactory rf,RuleManager ruleEditor)
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
			System.out.println();
			DataObject d;
			d = dataObjCol.getObject("FireAlarmSystem");
			if(d!=null)
			{
				System.out.println("Object gotten with name: " + d.getName());
				System.out.println("FAS Data: "+d.toString());
			}
			System.out.println();

			System.out.println();
			DataObject d2;
			d2 = dataObjCol.getObject("PoolLog");
			if(d2!=null)
			{
				System.out.println("Object gotten with name: " + d2.getName());
				System.out.println("PL Data: "+d2.toString());
			}
			System.out.println();

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


}
