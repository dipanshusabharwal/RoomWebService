package main.java;

import java.util.Enumeration;
import java.util.Hashtable;

public class ManageSessions {

	   private static ManageSessions singletonObject = new ManageSessions();

	   private ManageSessions(){}

	   public static ManageSessions getInstance(){
	      return singletonObject;
	   }

	    Hashtable<String, String> roomSessionDB = new Hashtable<String, String>();
	    Enumeration<String> roomsAndSessions;
	    String roomSessionData;
	    
	    public String getSessionData(String roomDetails)
	    {
	    	return roomSessionDB.get(roomDetails);
	    }
	    
	    public void updateSessionData(String roomDetails, String SESSION_ID){
	    	roomSessionDB.put(roomDetails, SESSION_ID);
	    }
	    
	    public void showAllSessionData(){
	    	
	    	roomsAndSessions = roomSessionDB.keys();
	    	
		    while(roomsAndSessions.hasMoreElements()) {
			    roomSessionData = (String) roomsAndSessions.nextElement();
			    System.out.println(roomSessionData + ": " +
			    roomSessionDB.get(roomSessionData));
		    }
	    }
	    
	    public String returnAllRoomData(){
	    	
	    	roomsAndSessions = roomSessionDB.keys();
	    	
	    	String allRoomSessionData = "";
	    	
		    while(roomsAndSessions.hasMoreElements()) {
		    	roomSessionData = (String) roomsAndSessions.nextElement();
		    	allRoomSessionData = allRoomSessionData.concat(roomSessionData);
			    allRoomSessionData = allRoomSessionData.concat(" : ");
			    allRoomSessionData = allRoomSessionData.concat(roomSessionDB.get(roomSessionData));
			    allRoomSessionData = allRoomSessionData.concat("  ");
		    }
		    
		    System.out.println("All room data :"+roomSessionData);
	    	return allRoomSessionData;
	    }
	    
	    public void clearRooms(){
	    	roomSessionDB.clear();
	    }
	}
