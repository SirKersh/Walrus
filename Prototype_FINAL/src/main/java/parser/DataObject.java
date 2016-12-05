package parser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A DataObject simulates a generic template for any kind of raw data. We use a HashMap of String key value pairs to store the data.
 * The key is the field from the CSV and the value is the actual data. The framework uses DataObjects to represent live data.
 * 
 * @author Walrus
 *
 */
public class DataObject 
{	
	//HashMap simulating parameters
	private Map<String,String> params;
	private String name;
	private Parser parser;
	
	/**
	 * Constructor for DataObject
	 * @param parameters the parameters
	 */
	public DataObject(HashMap<String, String> parameters, String name, Parser parser)
	{
		this.params = parameters;
		this.name = name;
		this.parser = parser;
	}
	
	/**
	 * Returns the parser contained in the DataObject.
	 * @return the parser contained in the DataObject
	 */
	public Parser getParser()
	{
		return parser;
	}
	
	/**
	 * Returns the name of the DataObject.
	 * @return the name of the DataObject
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the DataObject.
	 * @param name the name of the DataObject
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the data stored in the HashMap at the given key.
	 * @param key the key for the given data
	 * @return the data stored in the HashMap at the given key
	 */
	public Object getField(String key)
	{
		return params.get(key);
	}
	
	/**
	 * Returns if the DataObject has the given field.
	 * @param key the key for the given data
	 * @return true if the DataObject parameters contains the given field
	 */
	public boolean hasField(String key)
	{
		 if(params.get(key)!=null)
			 return true;
		 return false;
					
	}
	
	/**
	 * If known that the data contained in the DataObject field is an int, use this method instead. 
	 * This is used for comparison purposes in the rules.
	 * @param key they key for the given data
	 * @return the data stored in the HashMap at the given key
	 */
	public int getFieldInt(String key)
	{
		return Integer.parseInt(params.get(key));
	}
	
	/**
	 * Update the HashMap of the DataObject
	 * @param hm the HashMap to be updated
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
	
	/**
	 * Returns the HashMap in the DataObject
	 * @return the HashMap in the DataObject
	 */
	public Map<String, String> getMap()
	{
		return params;
	}
}
