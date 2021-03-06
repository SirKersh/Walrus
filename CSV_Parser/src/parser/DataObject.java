package parser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DataObject 
{	
	//HashMap simulating parameters
	private Map<String,Object> params;
	
	/**
	 * Constructor for DataObject
	 * @param parameters the parameters
	 */
	public DataObject(HashMap<String, Object> parameters)
	{
		this.params = parameters;
	}
	
	/**
	 * Update the hashmap of the DataObject
	 * @param hm the hashmap to be updated
	 */
	public void updateData(HashMap<String, Object> hm)
	{
		params = hm;
	}
	
	/**
	 * To String for DataObject
	 * @return the String
	 */
	public String toString()
	{
		String printString = "";
		Iterator<String> itty = params.keySet().iterator();
		
		while(itty.hasNext())
		{
			String key = itty.next();
			
			printString += key + ": " + params.get(key) + "\n";
		}
		
		return printString;
	}
}
