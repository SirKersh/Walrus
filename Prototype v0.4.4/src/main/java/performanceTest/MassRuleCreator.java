package performanceTest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class MassRuleCreator 
{
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
