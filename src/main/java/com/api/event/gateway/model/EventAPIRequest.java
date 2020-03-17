package com.api.event.gateway.model;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EventAPIRequest {

	@JsonProperty("requestor_type")
	@NotEmpty(message = "Please provide a requestor type")
	private String requestorType;
	@JsonProperty("requestor_name")
	@NotEmpty(message = "Please provide a requestor name")
	private String requestorName;
	@JsonProperty("api_key")
	@NotEmpty(message = "Please provide a api key")
	private String apiKey;
	@JsonProperty("events")
	private List<Events> events;

	/**
	 * @return the requestorType
	 */
	public String getRequestorType() {
		return requestorType;
	}

	/**
	 * @param requestorType the requestorType to set
	 */
	public void setRequestorType(String requestorType) {
		this.requestorType = requestorType;
	}

	/**
	 * @return the requestorName
	 */
	public String getRequestorName() {
		return requestorName;
	}

	/**
	 * @param requestorName the requestorName to set
	 */
	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}

	/**
	 * @return the apiKey
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * @param apiKey the apiKey to set
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	/**
	 * @return the events
	 */
	public List<Events> getEvents() {
		return events;
	}

	/**
	 * @param events the events to set
	 */
	public void setEvents(List<Events> events) {
		this.events = events;
	}

}
