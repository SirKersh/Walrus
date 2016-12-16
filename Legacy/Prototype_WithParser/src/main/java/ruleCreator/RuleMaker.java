package ruleCreator;

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
		System.out.println("Please enter 'done' when you are finished.");

		rule += "\twhen\n";

		while(true)
		{
			temp = scan.nextLine();
			if(temp.equals("done"))
				break;
			else
				rule += "\t\t" + temp + "\n";
		}

		System.out.println("Please enter the conditions to evaluate");
		System.out.println("Please enter 'done' when you are finished.");

		while(true)
		{
			temp = scan.nextLine();
			if(temp.equals("done"))
				break;
			else
				rule += "\t\t" + temp + "\n";
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
