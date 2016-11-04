package driver;

import java.io.IOException;

import ruleContainer.RuleFactory;

public class RuleFactoryDriver 
{
	public static void main(String args[])
	{
		//you would have to change the directory to your own rules folder here.
		RuleFactory rf = new RuleFactory("testRule2", "src/main/resources/rules");
		
		try {
			rf.createRule();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
