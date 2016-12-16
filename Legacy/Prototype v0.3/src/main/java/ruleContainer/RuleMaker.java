package ruleContainer;

import java.util.Scanner;

/**
 * A RuleMaker creates the inside of a .drl file. Everything except package name and imports are done here.
 *
 */
public class RuleMaker {

	Scanner scan;

	/**
	 * Constructor for RuleMaker
	 * @param scan Scanner used for reading input
	 */
	public RuleMaker(Scanner scan){

		this.scan = scan;
	}

	/**
	 * CreateRule creates the rule taking in name, conditions, and actions
	 * @return the partially created rule that will be combined with RuleFactory's rule
	 */
	public String createRule()
	{
		String rule = "";
		String temp = "";

		System.out.println("Please enter the rule name");
		rule += "rule \"" + scan.nextLine() + "\"\n";

		System.out.println("Please enter all objects you wish to use in correct format");
		System.out.println("Format: Name : ClassName()");
		System.out.println("Please enter 'done' when you are finished.");
		//Auto add dataObject : DataObject();

		rule += "\twhen\n";

		while(true)
		{
			temp = scan.nextLine();
			if(temp.equals("done"))
				break;
			else
				rule += "\t\t" + temp + "\n";
		}

		System.out.println("You're now going to enter the conditions to evaluate");
		System.out.println();
		
		while(true)
		{
			String feild = "";
			String object = "";
			String op = "";
			String cond = "";
			System.out.println("Please enter the object you wish to evaluate.");
			object = scan.nextLine();
			
			System.out.println("Please enter a field of the object to be evaluated.");
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
				rule += "\t\t eval("+object+".getField(\""+feild+"\")"+op+""+cond+")\n";
			else
				rule += "\t\t eval("+object+".getField(\""+feild+"\")"+op+"(\""+cond+"\"))\n";
			
			System.out.println("Enter 'done' if finished or press enter to create another condition.");
			temp = scan.nextLine();
			if(temp.equals("done"))
				break;
		}

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
