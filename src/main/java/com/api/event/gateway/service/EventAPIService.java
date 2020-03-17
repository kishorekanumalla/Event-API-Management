package com.api.event.gateway.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.api.event.gateway.common.Utils;
import com.api.event.gateway.exception.EventAPIException;
import com.api.event.gateway.metric.IMetricService;
import com.api.event.gateway.model.EventAPIRequest;
import com.api.event.gateway.model.Events;
import com.api.event.gateway.pubsub.ClientMessage;
import com.api.event.gateway.pubsub.Event;

@Service
public class EventAPIService {

	@Autowired
	private IMetricService metricService;

	@Value("${sink.data.request.time.in.millisecs}")
	private Long sinkDataReqTimeInMilliSecs;

	@Value("${max.requests.allowed}")
	private Integer maxReqAllowed;

	@Value("${max.requests.allowed.time.in.minutes}")
	private Integer maxReqAllowedTimeInMinutes;
	
	@Value("${sink.data.polling}")
	private Boolean isSinkPollingOn;
	
	

	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";

	public void publishService(EventAPIRequest eventAPIRequest) {

		try {
			for (Events events : eventAPIRequest.getEvents()) {

				ClientMessage message = new ClientMessage(eventAPIRequest.getRequestorName(), events.getMessage());
				Event.operation.publish(Utils.getChannelType(events.getType()), message);

			}
			if (isSinkPollingOn) {
			    SinkListenerJob();
			}
		} catch (EventAPIException e) {
			throw new EventAPIException("Error while processing the request in publish service !!");
		}
	}

	public Boolean requestLimiterCheck() {

		Map<String, Integer> requestMetricsMap = metricService.getCurrentRequestMetrics();

		String apiRequestStartTime = "";
		String apiRequestCurrentTime = "";

		Integer apiRequestCount = 0;
		Integer apiRequestCurrentCount = 0;
		Integer totalRequestCount = 0;

		boolean allowRequests = true;
		try {
			if (!requestMetricsMap.isEmpty()) {

				int i = 0;

				for (String timeKey : requestMetricsMap.keySet()) {
					i++;
					// Current Latest API Request Details
					if (i == requestMetricsMap.size()) {
						apiRequestCurrentTime = timeKey;
						apiRequestCurrentCount = requestMetricsMap.get(timeKey);
					}
					// Immediate Latest API Request Details
					if (i > 1 && i == requestMetricsMap.size() - 1) {
						apiRequestStartTime = timeKey;
						apiRequestCount = requestMetricsMap.get(timeKey);
					}
				}
			}


				totalRequestCount = apiRequestCurrentCount + apiRequestCount;
				// @TODO change to minutes
				if (apiRequestStartTime != "" && apiRequestCurrentTime != "") {
					SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
					Date d1 = formatter.parse(apiRequestStartTime);
					Date d2 = formatter.parse(apiRequestCurrentTime);

					long diff = d2.getTime() - d1.getTime();
					long diffMinutes = diff / (60 * 1000) % 60;
					long diffSeconds = diff / 1000 % 60;

					if (totalRequestCount >= maxReqAllowed && diffMinutes <= maxReqAllowedTimeInMinutes) {
						allowRequests = false;
					}
				} else if (totalRequestCount >= maxReqAllowed) {
					allowRequests = false;
				}
		} catch (EventAPIException | ParseException e) {
			throw new EventAPIException("Error while processing the request in RateLimiter check  !!");
		}
		return allowRequests;
	}

	private void SinkListenerJob() {
		long time = System.currentTimeMillis();

		Runnable update = new Runnable() {
			public void run() {
				Event.operation.publishAll();
			}
		};

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				update.run();
			}
		}, time % sinkDataReqTimeInMilliSecs, sinkDataReqTimeInMilliSecs);
	}

}
