package ruleContainer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import parser.DataObjectCollectionArrayList;

/**
 * A RuleFactory creates a .drl file which could contain multiple rules.
 */
public class RuleFactory 
{
	private String ruleName;
	String directoryName;
	private BufferedWriter writer;
	private String ruleToBeWritten = "";
	private RuleMaker rm;
	private Scanner scan;
	private DataObjectCollectionArrayList myList;

	/**
	 * Constructor for RuleFactory
	 * @param ruleName
	 * @param directoryName
	 */
	public RuleFactory(String ruleName, String directoryName)
	{
		this.ruleName = ruleName;
		this.directoryName = directoryName;
		scan = new Scanner(System.in);
		rm = new RuleMaker(scan);
	}
	
	public RuleFactory(String ruleName, String directoryName, Scanner Scan)
	{
		this.ruleName = ruleName;
		this.directoryName = directoryName;
		scan = Scan;
		rm = new RuleMaker(scan);
	}
	
	public RuleFactory(String ruleName, String directoryName, Scanner Scan, DataObjectCollectionArrayList theList )
	{
		myList = theList;
		this.ruleName = ruleName;
		this.directoryName = directoryName;
		scan = Scan;
		rm = new RuleMaker(scan, theList);
	}

	/**
	 * Continually prompts user for input in order to build a .drl file
	 * CreateRule uses RuleMaker's createRule() inorder to build the duplicated part of a .drl file
	 * @throws IOException
	 */
	public String createRule() throws IOException
	{
		String temp = "";
		int counter = 0;

		writer = new BufferedWriter(new FileWriter(new File("src//main//resources//rules//" + ruleName + ".drl")));

		System.out.println("You are about to create a rule file. A rule file can have multiple rules inside of it.");
		System.out.println("When you are finished writing all your rules please type 'stop' to finish. You can NOT stop mid rule");

		System.out.println("Please enter the package name");
		ruleToBeWritten += "package " + scan.nextLine() + "\n\n";
		//Have it be always 'package rules'

		System.out.println("Please enter any objects you wish to import into the rule.");
		System.out.println("Please enter 'done' when you are finished.");
		//Auto add parser.DataObject and parser.DataObjectCollection
		ruleToBeWritten += "import parser.DataObject;\n";
		ruleToBeWritten += "import parser.DataObjectCollection;\n";

		while(true)
		{
			temp = scan.nextLine();
			if(temp.equals("done"))
				break;
			else
				ruleToBeWritten += "import " + temp + ";\n";
		}

		ruleToBeWritten += "\n";

		
		while(true)
		{	
			if(counter > 0)
			{
				System.out.println("Please enter stop to quit or press enter to make another rule");
				temp = scan.nextLine();
				if(temp.equals("stop"))
					break;
				else
				{
					ruleToBeWritten += rm.createRule();
					counter++;
				}
			}
			else
			{
				ruleToBeWritten += rm.createRule();
				counter++;
			}

		}

		writer.write(ruleToBeWritten);
		writer.flush();
		//scan.close();
		return ruleName + ".drl";
	}

}
