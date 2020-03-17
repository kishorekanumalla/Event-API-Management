package com.api.event.gateway.model;

public class EventAPIResponse {
	
	private String message ="Request Successfully Processed !!";
	private String success= "true";
	private String code ="200";
	
	
	
	
	
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
	/**
	 * @return the success
	 */
	public String getSuccess() {
		return success;
	}
	/**
	 * @param success the success to set
	 */
	public void setSuccess(String success) {
		this.success = success;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

}
