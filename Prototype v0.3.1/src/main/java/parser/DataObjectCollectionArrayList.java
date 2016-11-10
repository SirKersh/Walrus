package parser;

import java.util.ArrayList;

public class DataObjectCollectionArrayList {
	
	ArrayList<DataObject> collection;
	
	public DataObjectCollectionArrayList()
	{
		collection = new ArrayList<DataObject>();
	}
	
	public int size()
	{
		return collection.size();
	}
	
	public void add(DataObject obj)
	{
		collection.add(obj);
	}
	
	public void remove(DataObject obj)
	{
		collection.remove(obj);
	}
	
	public ArrayList<DataObject> getCollection()
	{
		return collection;
	}
	
	public void setCollection(ArrayList<DataObject> collection)
	{
		this.collection = collection;
	}
	
	public DataObject getObject(String name)
	{
		for(int i = 0; i < collection.size(); i++)
		{
			if(collection.get(i).getName().equals(name))
				return collection.get(i);
		}
		
		return null;
	}

}
