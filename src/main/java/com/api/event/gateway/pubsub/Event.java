package com.api.event.gateway.pubsub;

import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;





public class Event {
    static {
        init();
    }

   public static Operation operation;

    public static ConcurrentHashMap<String, ConcurrentHashMap<Integer, WeakReference<Object>>> channels;

    static void init() {
        channels = new ConcurrentHashMap<>();
        operation = new Operation();
    }
}

