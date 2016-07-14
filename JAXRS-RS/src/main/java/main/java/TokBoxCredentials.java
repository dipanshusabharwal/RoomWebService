package main.java;

import com.opentok.MediaMode;
import com.opentok.OpenTok;
import com.opentok.Session;
import com.opentok.SessionProperties;
import com.opentok.exception.OpenTokException;

import java.io.IOException;
import java.io.StringWriter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;

@Path("/sessioncredentials")
public class TokBoxCredentials {
	
	final int API_KEY = 1234;
	final String API_SECRET = "abcd";
	String SESSION_ID, TOKEN, jsonOutput, roomDetails = null;
	
	ManageSessions hashTableObject = ManageSessions.getInstance(); 
	
	JSONObject jObject = new JSONObject();
	StringWriter out = new StringWriter();

	
	@GET
	@Path("/create")
	@Produces("application/json")
	public Response returnSessionAndToken(@QueryParam("RoomDetails") String roomData) throws OpenTokException, IOException {	
    	try{  		
    		roomDetails = roomData.toString();
    		
    		OpenTok otObject = new OpenTok(API_KEY, API_SECRET);
    		
    		SESSION_ID = hashTableObject.getSessionData(roomDetails);
    		
    		if(SESSION_ID==null){	
    			
    			System.out.println("Creating session for the first time");
    			
    			SessionProperties sessionProperties = new SessionProperties.Builder()
    					  .mediaMode(MediaMode.ROUTED)
    					  .build();
    			Session session = otObject.createSession(sessionProperties);
    			SESSION_ID = session.getSessionId();
    			
    			hashTableObject.updateSessionData(roomDetails, SESSION_ID);
    		    
    			hashTableObject.showAllSessionData();	
    		    
    			TOKEN = otObject.generateToken(SESSION_ID);
    			 		
        		jObject.put("Token", TOKEN);
         		jObject.put("SessionID", SESSION_ID);
         	    jObject.writeJSONString(out); 	      
         	    jsonOutput = out.toString();  
         	    System.out.println(jsonOutput);	 
    		}    		
    		else
    		{
    			System.out.println("Fetching session from hash table");
    			
    			hashTableObject.showAllSessionData();	
    			
    			TOKEN = otObject.generateToken(SESSION_ID);
    			    		
        		jObject.put("Token", TOKEN);
         		jObject.put("SessionID", SESSION_ID);
         	    
         	    jObject.writeJSONString(out); 	      
         	    jsonOutput = out.toString();  
         	    System.out.println(jsonOutput);	 
    		}
    	    return Response.status(200).entity(jsonOutput).build();
    	}    	
    	catch(OpenTokException e){
			e.printStackTrace();
			return Response.status(404).entity("Could not generate token").build();    		
    	}
    }
	
	@GET
	@Path("/showallrooms")
	@Produces("application/json")
	public Response returnRoomAndSession() throws IOException{
		
		System.out.println("Fetching all room data from hash table");
		
		String allRoomsData = hashTableObject.returnAllRoomData();	
		
		jObject.put("Room Name : SessionID",allRoomsData);
 	    StringWriter out = new StringWriter();
 	    jObject.writeJSONString(out); 	      
 	    jsonOutput = out.toString();  
 	    System.out.println(jsonOutput);	
		
		return Response.status(200).entity(jsonOutput).build();
	}
	
	@GET
	@Path("/deleterooms")
	public void deleteRooms() throws IOException{
		
		System.out.println("Deleting all room data from hash table");
		
		hashTableObject.clearRooms();
	}
}
