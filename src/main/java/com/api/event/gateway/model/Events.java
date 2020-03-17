package com.api.event.gateway.model;


	
	public class Events {
	    private String type;

	    private String name;

	    private String message;

	    public void setType(String type){
	        this.type = type;
	    }
	    public String getType(){
	        return this.type;
	    }
	    public void setName(String name){
	        this.name = name;
	    }
	    public String getName(){
	        return this.name;
	    }
	    public void setMessage(String message){
	        this.message = message;
	    }
	    public String getMessage(){
	        return this.message;
	    }
	}



