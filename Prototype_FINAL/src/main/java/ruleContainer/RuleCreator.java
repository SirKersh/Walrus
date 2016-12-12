package ruleContainer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.util.Scanner;

import parser.DataObject;
import parser.DataObjectCollection;

/**
 * RuleCreator walks the user through the steps of creating a new .drl file and then creates it.
 * 
 * @author Walrus
 *
 */
public class RuleCreator 
{
	private String ruleName;
	String directoryName;
	private BufferedWriter writer;
	private String ruleToBeWritten = "";
	private Scanner scan;
	private DataObjectCollection list;
	private DataObjectCollection newData;

	/**
	 * Constructor for RuleCreator 
	 * @param ruleName the name of the rule
	 * @param directoryName the location to save the rule
	 */
	public RuleCreator(String ruleName, String directoryName) {
		this.ruleName = ruleName;
		this.directoryName = directoryName;
		scan = new Scanner(System.in);
	}

	/**
	 * Constructor for RuleCreator
	 * @param ruleName the name of the rule
	 * @param directoryName the location to save the rule
	 * @param Scan the Scanner from what called the constructor
	 */
	public RuleCreator(String ruleName, String directoryName, Scanner Scan) {
		this.ruleName = ruleName;
		this.directoryName = directoryName;
		scan = Scan;
	}

	/**
	 * Constructor for RuleCreator
	 * @param ruleName the name of the rule
	 * @param directoryName the location to save the rule
	 * @param Scan the Scanner from what called the constructor
	 * @param theList the list of DataObjects and their logs
	 * @param NewData the list of newData created by the user
	 */
	public RuleCreator(String ruleName, String directoryName, Scanner Scan, DataObjectCollection theList,DataObjectCollection NewData) {
		list = theList;
		this.ruleName = ruleName;
		this.directoryName = directoryName;
		scan = Scan;
		newData = NewData;
	}

	/**
	 * Continually prompts user for input in order to build a .drl file
	 * CreateRule uses RuleMaker's createInnerRule() in order to build the duplicated
	 * part (rule block) of a .drl file
	 * 
	 * @throws IOException
	 */
	public String createRule() throws IOException 
	{
		String temp = "";
		int counter = 0;

		writer = new BufferedWriter(new FileWriter(new File("src//main//resources//rules//" + ruleName + ".drl")));

		System.out.println("You are about to create a rule file. A rule file can have multiple rules inside of it.");
		System.out.println(
				"When you are finished writing all your rules please type 'stop' to finish. You can NOT stop mid rule");

		System.out.println("Please enter the package name");
		ruleToBeWritten += "package " + scan.nextLine() + "\n\n";

		System.out.println("Please enter any objects you wish to import into the rule.");
		System.out.println("Please enter 'done' when you are finished.");

		// Auto add parser.DataObject and parser.DataObjectCollection
		ruleToBeWritten += "import parser.DataObject;\n";
		ruleToBeWritten += "import parser.ActionObject;\n";
		ruleToBeWritten += "import parser.DataObjectCollection;\n";
		ruleToBeWritten += "import java.util.HashMap;\n";

		while (true)
		{
			temp = scan.nextLine();
			if (temp.equals("done"))
				break;
			else
				ruleToBeWritten += "import " + temp + ";\n";
		}

		ruleToBeWritten += "\n";

		while (true)
		{
			if (counter > 0) 
			{
				System.out.println("Please enter stop to quit or press enter to make another rule");
				temp = scan.nextLine();
				if (temp.equals("stop"))
					break;
				else
				{
					ruleToBeWritten += createInnerRule();
					counter++;
				}
			} 
			else 
			{
				ruleToBeWritten += createInnerRule();
				counter++;
			}

		}

		//System.out.println("Ok it is getting here");
		writer.write(ruleToBeWritten);
		//System.out.println("Ok it is also getting here");
		writer.flush();
		return ruleName + ".drl";
	}

	/**
	 * CreateInnerRule creates the rule taking in name, conditions, and actions
	 * @return the partially created rule that will be combined with the rest of the rule
	 */
	public String createInnerRule() 
	{
		String rule = "";
		String temp = "";
		String logfile = "";
		String feild = "";
		String object = "";
		String op = "";
		String cond = "";
		String type = "";
		boolean isAction = false;
		int no = 1;
		boolean additionalCondition = false; //mod4b

		System.out.println("Please enter the rule name");
		rule += "rule \"" + scan.nextLine() + "\"\n"; //good

		System.out.println("Is your rule going to use new data? (y/n)");
		if(scan.nextLine().equals("y"))
		{
			rule += "salience -1\n";
		}

		rule += "\twhen\n";
		rule += "\t\tdataObjectCol : DataObjectCollection()\n";

		while (true) {
			System.out.println("Would you like to use data from: \n1. Logfiles \n2. new Data");
			int choice = Integer.parseInt(scan.nextLine());
			if(choice==2&&newData.size()==0)
			{
				System.out.println("No new Data in the system. Automatically choosing option 1.");
				choice = 1;
			}
			else if(choice==1&&list.size()==0)
			{
				System.out.println("No logfiles in the system. Automatically choosing option 2.");
				choice = 2;
			} //good
			switch(choice)
			{
			case 1:
				System.out.println(
						"Please enter logfile your rule will use. Select from list below, and type exactly as appears.");

				int howManyToPrint = list.size();

				for (int i = 0; i < howManyToPrint; i++) 
				{
					System.out.println(list.getObjAtPosition(i).getName());
				}
				type = "DataObject";
				isAction = false;
				break;

			case 2:
				System.out.println(
						"Please enter the data your rule will use. Select from list below, and type exactly as appears.");

				int size = newData.size();

				for (int i = 0; i < size; i++) 
				{
					System.out.println(newData.getObjAtPosition(i).getName());
				}
				type = "ActionObject";
				isAction = true;
				break;

			default:
				break;
			}


			logfile = scan.nextLine();

//			if(additionalCondition == false) //mod4b
//			{ //mod4b
//				System.out.println("Please enter all objects you wish to use in correct format");
//				System.out.println("Follow this format (after ->)->Name : ClassName()");
//				System.out.println("Please enter 'done' when you are finished.");
//
//				while (true) 
//				{
//					temp = scan.nextLine();
//					if (temp.equals("done"))
//						break;
//					else
//						rule += "\t\t" + temp + "\n";
//				}
//			} //mod4b
			System.out.println("Please enter the name of the object you wish to evaluate."); // @TO-DO
			object = scan.nextLine(); //mod4b used to be after scan.nextLine(): + no
			//rule += "\t\t"+object + ": ActionObject(); \n"; 

			if(choice == 1)
			{
				rule += "\t\t"+object+" : "+type+"(name == \"" + logfile + "\") from dataObjectCol.getCollection() \n";
			}
			else
			{
				String letsPrintThis = object;
				letsPrintThis += " : ";
				//System.out.println("Please enter the class of the object named: " + object + ". CaSe SeNsItIvE!");
				System.out.println("the class of the object: " + object + " is being assigned ActionObject");
				letsPrintThis += "ActionObject()";
				rule += "\t\t" + letsPrintThis + "\n";
			}

			System.out.println();

			System.out.println(
					"Please enter a field of the object to be evaluated. Select from the following list, type exactly as they appear.");

			DataObject theObject;
			if(isAction)
				theObject = newData.getObject(logfile);
			else
				theObject = list.getObject(logfile);



			for (String key : theObject.getMap().keySet()) 
			{
				System.out.println(key);
			}
			feild = scan.nextLine();

			System.out.println("Please enter the number representing the operation you wish to use.");
			System.out.println("1. Equals \n2. Less than \n3. Greater than");
			temp = scan.nextLine();

			boolean isNumber = false;
			switch (Integer.parseInt(temp)) 
			{
			case 1:
				op = ".equals";
				break;
			case 2:
				op = "<";
				isNumber = true; 
				break;
			case 3:
				op = ">";
				isNumber = true;
				break;
			}

			System.out.println("Please enter the condition your feild will check.");
			cond = scan.nextLine();

			if (isNumber)
				rule += "\t\t eval("+object +" != null&&" + object + ".getFieldInt(\"" + feild + "\")" + op + "" + cond
				+ ")\n";
			else
				rule += "\t\t eval("+object+" != null&&" + object + ".getField(\"" + feild + "\")" + op + "(\"" + cond
				+ "\"))\n";

			System.out.println("Enter 'done' if finished or press enter to create another condition.");
			temp = scan.nextLine();
			no++;
			if (temp.equals("done"))
			{
				break;
			}
			additionalCondition = true;//mod4b
		}

		System.out.println("Now you will input the actions you wish to perform");

		rule += "\tthen\n";

		int hmNo = 1;
		while (true) {
			System.out.println("What action would you like to do?\n1.Print out words\n2. Create a object that will be used next run.");
			int choice  = Integer.parseInt(scan.nextLine());
			if(choice==2&&newData.size()==0){
				System.out.println("No new Data in the system. Automatically choosing option 1.");
				choice = 1;
			}
			switch (choice) {
			case 1:
				System.out.println("What would you like to print out?");
				temp = scan.nextLine();
				rule += "\t\t System.out.println(\"" + temp + "\");\n";
				break;



			case 2:
				rule += "\t\tHashMap<String, String> hm"+hmNo+" = new HashMap<String,String>();\n";
				System.out.println("Please enter the data your rule will create. Select from list below, and type exactly as appears.");
				int size = newData.size();
				for (int i = 0; i < size; i++) 
				{
					System.out.println(newData.getObjAtPosition(i).getName());
				}
				logfile = scan.nextLine();

				System.out.println("Please enter the field your rule will create. Select from list below, and type exactly as appears.");		
				DataObject theObject = newData.getObject(logfile);
				for (String key : theObject.getMap().keySet()) 
				{
					System.out.println(key);
				}
				feild = scan.nextLine();
				System.out.println("Please enter the current value of the feild you just selected.");		
				String value = scan.nextLine();

				rule += "\t\t hm"+hmNo+".put(\""  +feild+  "\", \""+value+"\");\n";

				rule += "\t\t ActionObject dobj = new ActionObject(hm"+hmNo+", \"\", \""+logfile+"\");\n";
				//rule += "\t\t dataObjectCol.add(dobj);\n";
				rule += "\t\t insert(dobj);\n";
				rule += "\t\t System.out.println(\"Action Object Created.\");\n";

				break;
			}


			hmNo++;
			System.out.println("Type \"done\" if you are finished with the actions.");
			temp = scan.nextLine();
			if (temp.equals("done"))
				break;
		}

		rule += "end\n";

		return rule;

	}
}
