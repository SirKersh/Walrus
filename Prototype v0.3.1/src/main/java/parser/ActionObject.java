package parser;

import java.util.HashMap;

public class ActionObject extends DataObject
{
	private String derived = "";

	public ActionObject(HashMap<String, String> parameters, String derived) 
	{
		super(parameters);
		this.derived = derived;
	}

	public String getDerived() {
		return derived;
	}

	public void setDerived(String derived) {
		this.derived = derived;
	}
	
	

}
