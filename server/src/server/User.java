
package server;

class User {
	 //XML attribute id
    private int id;
  
    private String username ;
    
    private String password;
    private int status;
    private String IP;
    public User(){}
    public User(int id, String username, String password, int status, String iP) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.status = status;
		IP = iP;
	}
    public User(int id, String username, int status) {
  		super();
  		this.id = id;
  		this.username = username;	
  		this.status = status;
  	
  	}
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
     public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
        
        
    }
    public String getIP() {
        return IP;
    }
    public void setIP(String IP) {
        this.IP = IP;
    }
 
    @Override
    public String toString() {
        return this.id + ":" + this.username +  ":" +this.password +  ":" +this.status + "\n" ;
    }

}
