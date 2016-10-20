package driver;

import java.io.IOException;

import ruleCreator.RuleFactory;

public class RuleFactoryDriver 
{
	public static void main(String args[])
	{
		//you would have to change the directory to your own rules folder here.
		RuleFactory rf = new RuleFactory("testRule1", "C:\\Users\\Digital Storm\\Documents\\Software Engineering Mars\\Walrus\\Prototype_WithParser\\src\\main\\resources\\rules");
		
		try {
			rf.createRule();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
