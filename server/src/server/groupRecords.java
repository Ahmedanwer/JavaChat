package server;

import java.util.ArrayList;

class groupRecords {
	class groupRecord{
		 private int groupID;
	     private  int userID;
		public groupRecord(int groupID,int userID){
			this.groupID=groupID;
			this.userID=userID;
		}
		public int getGroupID(){
			return groupID;
		}
		public  int getUserID(){
			return userID;
		}
		
		public void setUserID(int ID)
		{
			userID=ID;
		}
		public void setGroupID(int ID)
		{
			groupID=ID;
		}
		@Override
	    public String toString() {
	    	
	        return this.getUserID()+ ":"+ this.getGroupID()+ "\n" ;
	    }
	}
	
	ArrayList<groupRecord> Records;
	
	 public groupRecords(){
		 Records=new  ArrayList<groupRecord>();
	 }
	 
	 
	 public void addRecord(int groupID,int userID){
		 Records.add(new groupRecord(groupID,userID));
	 }
	 public ArrayList<Integer> getGroupUsers(int groupID){
		 
		 ArrayList<Integer> GroupUsers=new ArrayList();
		 
		 
		 for(int i=0; i<Records.size();i++)
		 {
			 if(Records.get(i).getGroupID() == groupID )
			 {
				 GroupUsers.add(Records.get(i).getUserID());
			 }
			 
			 
			
				 
		 }
		 return GroupUsers;
		 
	 }
    public ArrayList<Integer> getUserGroups(int userId){
    	ArrayList<Integer> userGroups=new ArrayList();

    	for(int i=0; i<Records.size();i++)
		 {
			 if(Records.get(i).getUserID() == userId)
			 {
				 userGroups.add(Records.get(i).getUserID());
			 }
			 
			 
			
				 
		 }
		 return userGroups;
    	
    }
    


}
