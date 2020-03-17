package com.api.event.gateway.common;

public class Utils {

	public enum ChannelEvents {

		TYPE1("CTYPE_1"), TYPE2("CTYPE_2"), TYPE3("CTYPE_3"), TYPE4("CTYPE_4");

		private String channel;

		private ChannelEvents(String channel) {
			this.channel = channel;
		}

	}
	public enum Subscribers {

		ETYPE_1_SUB("CTYPE_1"), ETYPE_2_SUB("CTYPE_2"), ETYPE_3_SUB("CTYPE_3"), ETYPE_4_SUB("CTYPE_4");

		public String channel;

		private Subscribers(String channel) {
			this.channel = channel;
		}

	}

	public static String getChannelType(String eventType) {

		String channel = "";

		switch (eventType) {
		case "TYPE1":
			channel = ChannelEvents.TYPE1.channel;
			break;
		case "TYPE2":
			channel = ChannelEvents.TYPE2.channel;
			break;
		case "TYPE3":
			channel = ChannelEvents.TYPE3.channel;
			break;
		case "TYPE4":
			channel = ChannelEvents.TYPE4.channel;
			break;
		default:
			channel = ChannelEvents.TYPE1.channel;
			break;
		}

		return channel;
	}

}
