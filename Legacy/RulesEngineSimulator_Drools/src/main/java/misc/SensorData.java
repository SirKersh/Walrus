package misc;

public class SensorData {
	
	public static final boolean ALARM_ON = true;
	public static final boolean ALARM_OFF = false;
	
	private int status;
	private int pings;
	
	public SensorData(){
		this.status = 0;
		this.pings = 0;
	}
	
	public void setStatus(int s){
		this.status = s;
	}
	
	public int getStatus(){
		return status;
	}
	
	public void setPings(int p){
		this.pings = p;
	}    	
	
	public int getPings(){
		return pings;
	}   	
}
