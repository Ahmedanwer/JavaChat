package server;

class Group {
	 private int id;
	  
	    private String groupName ;
	    
	     public int getId() {
	        return id;
	    }
	    public void setId(int id) {
	        this.id = id;
	    }
	     public String getGroupName() {
	        return groupName;
	    }
	    public void setGroupName(String groupName) {
	        this.groupName = groupName;
	    }
	     @Override
	    public String toString() {
	        return this.id + ":" + this.groupName  + "\n" ;
	    }

}
