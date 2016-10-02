package misc;

public class Message {

    public static final int HELLO = 0;        
    public static final int WHEREAMI = 1;
    public static final int GOODBYE = 2;

    private String message;
    private int status;
    
    public Message(){
    	this.message = "";
    	this.status = -1;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
