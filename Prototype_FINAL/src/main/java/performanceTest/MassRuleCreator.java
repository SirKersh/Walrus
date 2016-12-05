package performanceTest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * The MassRuleCreator creates large amounts of .drl files specified by the user.
 * This class was used to test the performance and scalability of the engine.
 * 
 * @author Walrus
 *
 */
public class MassRuleCreator 
{
	/*
	 * The driver loops a specified amount of times (specified by the user in the variable counter) and creates a .drl file every time.
	 * Each .drl file receives a different rule name and a different random number for the temperature. 
	 */
	public static void main(String ars[]) throws IOException
	{
		BufferedWriter writer;
		String rule;
		int counter = 5000;
		Random rand = new Random();
		int randNum;

		while(counter > 0)
		{
			randNum = rand.nextInt(100);
			writer = new BufferedWriter(new FileWriter(new File("src//main//resources//massRuleFolder//" + counter + ".drl")));
			rule = "package rules\n\nimport parser.DataObject;\nimport parser.DataObjectCollectionArrayList;\n\nrule \"Pool Temp " + counter + "\"\n\twhen\n\t\tdataObjectCol : DataObjectCollectionArrayList()\n\t\tdataObject : DataObject(name == \"PoolLog\") from dataObjectCol.getCollection()"
					+ "\n\t\teval(dataObject != null && dataObject.hasField(\"Temperature\") && dataObject.getFieldInt(\"Temperature\") < " + randNum + ")\n\tthen\n\t\t//System.out.println(\"Water Too Cold!\" + " + randNum + ");\n\nend";
			writer.write(rule);
			writer.flush();
			counter--;
		}
	}
}
