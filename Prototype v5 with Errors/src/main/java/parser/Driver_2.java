package parser;
import java.io.IOException;

/**
 * 
 * Driver test for DataObject and Parser
 *
 */

public class Driver_2 
{
	public static void main(String args[])
	{
		Parser parser = new Parser();
		try {
			DataObject dataObj = parser.parse("fireincident.csv");
			System.out.println(dataObj.toString());
			//parser.update(dataObj);
			//System.out.println(dataObj.toString());
			//parser.update(dataObj);
			//System.out.println(dataObj.toString());

		} catch (IOException e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
}
