package parser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DataObject 
{	
	//HashMap simulating parameters
	private Map<String,String> params;
	
	/**
	 * Constructor for DataObject
	 * @param parameters the parameters
	 */
	public DataObject(HashMap<String, String> parameters)
	{
		this.params = parameters;
	}
	
	/**
	 * Returns the value that maps to the key
	 * @param key the key for the value
	 * @return the value
	 */
	public Object getField(String key)
	{
		return params.get(key);
	}
	
	/**
	 * Checks to see if the DataObject hashmap contains the field
	 * @param key the key to the field
	 * @return true if the hashmap contains the field
	 */
	public boolean hasField(String key)
	{
		 if(params.get(key)!=null)
			 return true;
		 return false;
					
	}
	
	/**
	 * Returns the value that maps to the key casted as an Integer
	 * @param key the key for the value
	 * @return the integer value
	 */
	public int getFieldInt(String key)
	{
		return Integer.parseInt(params.get(key));
	}
	
	/**
	 * Update the hashmap of the DataObject
	 * @param hm the hashmap to be updated
	 */
	public void updateData(HashMap<String, String> hm)
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
