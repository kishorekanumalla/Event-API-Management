package com.api.event.gateway.pubsub;

import java.util.List;

import org.springframework.stereotype.Service;

public interface WeakConcurrentHashMapListener {
	public boolean notifyOnAdd(WeakConcurrentHashMap<String,List> map);
	public boolean applySink(WeakConcurrentHashMap<String,List> map);
	
}
