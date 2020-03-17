package com.api.event.gateway.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.event.gateway.common.Utils;
import com.api.event.gateway.exception.EventAPIException;
import com.api.event.gateway.model.EventAPIRequest;
import com.api.event.gateway.model.EventAPIResponse;
import com.api.event.gateway.pubsub.Event;
import com.api.event.gateway.pubsub.Subscriber;
import com.api.event.gateway.service.EventAPIService;

@RestController
@RequestMapping("/event-api")
public class EventAPIController {
	
	

	private List<EventAPIRequest> eventAPIRequest;

	@Autowired
	EventAPIService eventAPIService;

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> publishEvents(@Valid @RequestBody EventAPIRequest eventAPIMessage) {

		EventAPIResponse eventAPIResponse;
		try {
			Boolean isRequestAllowed = eventAPIService.requestLimiterCheck();
			eventAPIResponse = new EventAPIResponse();

			if (isRequestAllowed) {
				eventAPIService.publishService(eventAPIMessage);
			} else {
				throw new EventAPIException("Maximun Request Quota Reached in the TimeFrame .... Plesae try again");
			}
		} catch (EventAPIException e) {
			throw new EventAPIException(e.getMessage()); 
		}
		return new ResponseEntity<>(eventAPIResponse, HttpStatus.OK);
	}

}
