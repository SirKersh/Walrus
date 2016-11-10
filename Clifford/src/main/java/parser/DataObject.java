package parser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DataObject 
{	
	//HashMap simulating parameters
	private Map<String,String> params;
	public String name;
	
	/**
	 * Constructor for DataObject
	 * @param parameters the parameters
	 */
	public DataObject(HashMap<String, String> parameters, String name)
	{
		this.params = parameters;
		this.name = name;
		System.out.println("Name: " + name);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getField(String key)
	{
		return params.get(key);
	}
	
	public boolean hasField(String key)
	{
		 if(params.get(key)!=null)
			 return true;
		 return false;
					
	}
	
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
