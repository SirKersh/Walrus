package alert;

/**
 * The Alert class represents alerts being sent to the Command and Control room to update the operator of current statuses. 
 * Alerts can be replaced later on with actions that could fire when alerts are triggered.
 */
public class Alert 
{
	//The alert message 
	String alertMessage;
	
	/**
	 * Constructor for alert
	 */
	public Alert()
	{
		this.alertMessage = "";
	}

	/**
	 * Return the alert message
	 * @return the alert message
	 */
	public String getAlertMessage() 
	{
		return alertMessage;
	}

	/**
	 * Set the alert message
	 * @param alertMessage the alert message
	 */
	public void setAlertMessage(String alertMessage) 
	{
		this.alertMessage = alertMessage;
	}
	
	/**
	 * Fires the alert message sending it to the console
	 */
	public void alert()
	{
		System.out.println("Alert: " + alertMessage);
	}
	
	

}
