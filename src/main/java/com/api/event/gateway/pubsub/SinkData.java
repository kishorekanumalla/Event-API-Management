package com.api.event.gateway.pubsub;

public class SinkData {
	
	
	
	private String clientName;
	private String eventType;
	private String message;
	
	/**
	 * @return the clientName
	 */
	public String getClientName() {
		return clientName;
	}
	/**
	 * @param clientName the clientName to set
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	/**
	 * @return the eventType
	 */
	public String getEventType() {
		return eventType;
	}
	/**
	 * @param eventType the eventType to set
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "Event Type: " + this.eventType + " ; " + "Client Name : " + this.clientName + "; " + "Message : " + this.message ;
	}
	

}
