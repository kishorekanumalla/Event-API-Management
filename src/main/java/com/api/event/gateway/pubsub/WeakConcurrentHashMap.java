package com.api.event.gateway.pubsub;

import java.util.concurrent.ConcurrentHashMap;

public class WeakConcurrentHashMap<String, List> extends ConcurrentHashMap<String, List> {

	private static final long serialVersionUID = 1L;

	private WeakConcurrentHashMapListener listener;

	public void registerListener(WeakConcurrentHashMapListener listener) {
		this.listener = listener;
	}

	@Override
	public List put(String key, List value) {

		List returnVal = super.put(key, value);

		if (listener != null) {
			if (listener.notifyOnAdd((WeakConcurrentHashMap<java.lang.String, java.util.List>) this)) {
				this.clear();
			}
		}

		return returnVal;
	}

	public void sinkAllData() {

		if (listener != null) {
			if (listener.applySink((WeakConcurrentHashMap<java.lang.String, java.util.List>) this)) {
				this.clear();
			}
		}
	}

}
