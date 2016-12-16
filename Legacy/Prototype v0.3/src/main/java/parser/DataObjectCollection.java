package parser;

import java.util.*;

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
	
	public DataObject getObject(String key,DataObject dataObj)
	{
		return collection.put(key, dataObj);
	}
}
