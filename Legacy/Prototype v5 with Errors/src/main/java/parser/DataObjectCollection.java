package parser;

import java.util.*;
import java.util.Map.Entry;

public class DataObjectCollection {
	HashMap<String, DataObject> collection; //Key,DataObject
	
	public DataObjectCollection()
	{
		collection = new HashMap<String, DataObject>();
	}
	public DataObject getObject(String key)
	{
		return collection.get(key);
	}
	public HashMap<String, DataObject> getHash()
	{
		return collection;
	}
	public void removeObject(String key)
	{
		collection.remove(key);
	}
	public void removeObject(DataObject obj)
	{
		for (Entry<String, DataObject> entry : collection.entrySet()) {
		    String key = entry.getKey();
		    Object value = entry.getValue();
		    if(value.equals(obj))
		    {
		    	collection.remove(key);
		    	break;
		    }
		}
	}
	
	public String getKey(DataObject obj)
	{
		for (Entry<String, DataObject> entry : collection.entrySet()) {
		    String key = entry.getKey();
		    Object value = entry.getValue();
		    if(value.equals(obj))
		    {
		    	return key;
		    }
		}
		return "";
	}
	
	public DataObject addObject(String key,DataObject dataObj)
	{
		return collection.put(key, dataObj);
	}
	
	public int Size()
	{
		return collection.size();
	}
}
