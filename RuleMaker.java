package ruleContainer;

import java.util.Scanner;

import org.mvel2.sh.command.basic.Set;

import parser.DataObject;
import parser.DataObjectCollectionArrayList;

/**
 * A RuleMaker creates the inside of a .drl file. Everything except package name and imports are done here.
 *
 */
public class RuleMaker {

	Scanner scan;
	DataObjectCollectionArrayList theList;

	/**
	 * Constructor for RuleMaker
	 * @param scan Scanner used for reading input
	 */
	public RuleMaker(Scanner scan)
	{
		this.scan = scan;
	}
	
	public RuleMaker(Scanner scan, DataObjectCollectionArrayList myList)
	{
		this.scan = scan;
		theList = myList;
	}

	/**
	 * CreateRule creates the rule taking in name, conditions, and actions
	 * @return the partially created rule that will be combined with RuleFactory's rule
	 */
	public String createRule()
	{
		String rule = "";
		String temp = "";
		String logfile = "";

		System.out.println("Please enter the rule name");
		rule += "rule \"" + scan.nextLine() + "\"\n";
		
		System.out.println("Please enter logfile your rule will use. Select from list below, and type exactly as appears.");
		
		int howManyToPrint = theList.howManyInCollection();
		
		for(int i = 0; i < howManyToPrint; i++)
		{
			System.out.println(theList.getObjAtPosition(i).getName());
		}
		
		logfile = scan.nextLine();

		System.out.println("Please enter all objects you wish to use in correct format");
		System.out.println("Format: Name : ClassName()");
		System.out.println("Please enter 'done' when you are finished.");
		//Auto add dataObject : DataObject();

		rule += "\twhen\n";
		rule += "\t\tdataObjectCol : DataObjectCollection()\n";
		
		while(true)
		{
			temp = scan.nextLine();
			if(temp.equals("done"))
				break;
			else
				rule += "\t\t" + temp + "\n";
		}
		rule += "\t\tdataObject : DataObject() from dataObjectCol.getObject(\""+logfile+"\") \n";

		System.out.println("Now enter the conditions to evaluate. Select from the following list, type exactly as they appear.");
		
		for(int i = 0; i < howManyToPrint; i++)
		{
			DataObject theObject = theList.getObjAtPosition(i);
			
			for (String key : theObject.getMap().keySet() ) {
			    System.out.println( key );
			}
		}
		
		System.out.println();
		
		while(true)
		{
			String feild = "";
			String object = "";
			String op = "";
			String cond = "";
			System.out.println("Please enter the object you wish to evaluate."); //@TO-DO
			object = scan.nextLine();
			
			System.out.println("Please enter a field of the object to be evaluated."); //@TO-DO
			feild = scan.nextLine();
			
			System.out.println("Please enter the number representing the operation you wish to use.");
			System.out.println("1. Equals \n2. Less than \n3. Greater than");
			temp = scan.nextLine();
			
			boolean isNumber = false;
			switch(Integer.parseInt(temp))
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
			
			if(isNumber)
				rule += "\t\t eval("+object+".getFieldInt(\""+feild+"\")"+op+""+cond+")\n";
			else
				rule += "\t\t eval("+object+".getField(\""+feild+"\")"+op+"(\""+cond+"\"))\n";
			
			System.out.println("Enter 'done' if finished or press enter to create another condition.");
			temp = scan.nextLine();
			if(temp.equals("done"))
				break;
		}

		//while(true)
		//{
		//	temp = scan.nextLine();
		//	if(temp.equals("done"))
		//		break;
		//	else
		//		rule += "\t\t" + temp + "\n";
		//}


		System.out.println("Please enter the actions to perform");
		System.out.println("Please enter 'done' when you are finished.");

		rule += "\tthen\n";

		while(true)
		{
			temp = scan.nextLine();
			if(temp.equals("done"))
				break;
			else
				rule += "\t\t" + temp + "\n";
		}		

		rule += "end\n";

		return rule;


	}
}
