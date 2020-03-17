package com.api.event.gateway.model;

import java.util.List;

public class EventAPIErrorResponse {
	
	
	
    private List<String> details;
	
	public EventAPIErrorResponse(List<String> details) {
        super();
        this.details = details;
    }
 
    //General error message about nature of error
    private String message;
 
    
	
	


}
