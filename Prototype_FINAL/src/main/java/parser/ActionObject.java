package parser;

import java.util.HashMap;

/**
 * ActionObjects are dynamic DataObjects. ActionObjects are created during rule execution to help execute chain ruling.
 * ActionObjects extend DataObjects adding the field derived, this allows the user to trace a rule chain back to its origins.
 * 
 * @author Walrus
 *
 */
public class ActionObject extends DataObject
{
	private String derived = "";

	/**
	 * Constructor for ActionObject
	 * @param parameters the HashMap of field, value pairs
	 * @param derived the rule(s) that caused this object to be created
	 * @param name the name of the ActionObject
	 */
	public ActionObject(HashMap<String, String> parameters, String derived, String name) 
	{
		super(parameters, name, null);
		this.derived = derived;
	}

	/**
	 * Returns the derived string.
	 * @return the derived string
	 */
	public String getDerived() {
		return derived;
	}

	/**
	 * Sets the derived String
	 * @param derived where the object originated from.
	 */
	public void setDerived(String derived) {
		this.derived = derived;
	}
	
	

}
