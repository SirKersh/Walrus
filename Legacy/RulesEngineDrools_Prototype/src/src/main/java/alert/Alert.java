package alert;

public class Alert 
{

	String alertMessage;
	
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
	
	public void alert()
	{
		System.out.println(alertMessage);
	}
	
	

}
