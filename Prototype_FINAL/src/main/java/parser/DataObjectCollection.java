package parser;

import java.util.ArrayList;

/**
 * DataObjectCollection is a list of all DataObjects that are being used in the rules engine. This keeps track of all of the dataObjects and allows
 * any rule to access a specific DataObject at any point in time.
 * 
 * @author Walrus
 *
 */
public class DataObjectCollection {
	
	ArrayList<DataObject> collection;
	
	/**
	 * Constructor for DataObjectCollection.
	 */
	public DataObjectCollection()
	{
		collection = new ArrayList<DataObject>();
	}
	
	/**
	 * Returns the size of the collection.
	 * @return the size of the collection
	 */
	public int size()
	{
		return collection.size();
	}
	
	/**
	 * Adds a DataObject to the collection.
	 * @param obj the DataObject to add to the collection
	 */
	public void add(DataObject obj)
	{
		collection.add(obj);
	}
	
	/**
	 * Removes a DataObject from the collection.
	 * @param obj the DataObject to remove from the collection
	 */
	public void remove(DataObject obj)
	{
		collection.remove(obj);
	}
	
	/**
	 * Returns the collection.
	 * @return the collection
	 */
	public ArrayList<DataObject> getCollection()
	{
		return collection;
	}
	
	/**
	 * Sets the collection to a new collection
	 * @param collection the collection to set
	 */
	public void setCollection(ArrayList<DataObject> collection)
	{
		this.collection = collection;
	}
	
	/**
	 * Returns the DataObject from the collection
	 * @param name the name of the DataObject
	 * @return the DataObject from the collection
	 */
	public DataObject getObject(String name)
	{
		for(int i = 0; i < collection.size(); i++)
		{
			if(collection.get(i).getName().equals(name))
				return collection.get(i);
		}
		
		return null;
	}
	
	/**
	 * Returns the DataObject at the given position
	 * @param i the index of the DataObject
	 * @return the DataObject at the given position
	 */
	public DataObject getObjAtPosition(int i)
	{
		return collection.get(i);
	}
	
}
