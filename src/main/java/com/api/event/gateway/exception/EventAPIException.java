package com.api.event.gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class EventAPIException extends RuntimeException {
   public EventAPIException(String exception) {
	   super(exception);
   }
   public EventAPIException() {
	   super();
   }
}
