/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testsaxparser;

/**
 *
 * @author 11003_000
 */
public class Group {
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
