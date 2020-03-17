package com.api.event.gateway.pubsub;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Subscriber {

	String type;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public static WeakConcurrentHashMap<String, List<SinkData>> sinkaDataMap = new WeakConcurrentHashMap<>();

	public Subscriber(String type) {

		this.type = type;
	}

	public Subscriber() {
		sinkaDataMap.registerListener(new SinkListener());
	}

	@OnMessage
	private void onMessage(String channelName, ClientMessage message) {

		List<SinkData> clientList = new ArrayList<SinkData>();

		if (!sinkaDataMap.containsKey(this.type)) {
			clientList.add(prepareSinkData(message));
		} else {
			clientList = sinkaDataMap.get(this.type);
			clientList.add(prepareSinkData(message));
		}

		sinkaDataMap.put(this.type, clientList);

	}
	
	@ApplySink
	private void applySink() {

		sinkaDataMap.sinkAllData();

	}

	@GetMessage
	private void getMessage(String channelName, ClientMessage message) {
		// logger.info("<<<<<<<<<<<<<<<<< For Channel Type >>>>>>>>>>>>>>>>>> " +
		// channelName + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		for (String eventType : sinkaDataMap.keySet()) {
			if (this.type.equalsIgnoreCase(eventType)) {
				List<SinkData> clientData = sinkaDataMap.get(this.type);
				for (SinkData cData : clientData) {
					// logger.info("<< Client Name >> " + cData.getClientName());
					// logger.info("<< Message >> " + cData.getMessage());
				}

			}

		}

		// logger.info("\n<<<<<<<<<<<<<<<<<<<<<<<<<<< End of Channel
		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	public  void cleanSinkDataMap() {
		sinkaDataMap.clear();
	}

	private SinkData prepareSinkData(ClientMessage message) {
		// logger.info("Prepare Client Data{}");

		SinkData sinkData = new SinkData();
		sinkData.setEventType(this.type);
		sinkData.setClientName(message.client);
		sinkData.setMessage(message.message);

		return sinkData;
	}

}
