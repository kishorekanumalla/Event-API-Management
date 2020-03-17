package com.api.event.gateway.pubsub;

import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



public class Operation extends Event
{
    public void subscribe(String channelName, Object subscriber) {
        if (!channels.containsKey(channelName)) {
            channels.put(channelName, new ConcurrentHashMap<>());
        }
        
        channels.get(channelName).put(subscriber.hashCode(), new WeakReference<>(subscriber));
    }

    public void publish(String channelName, Post message) {
        for(Map.Entry<Integer, WeakReference<Object>> subs : channels.get(channelName).entrySet()) {
            WeakReference<Object> subscriberRef = subs.getValue();

            Object subscriberObj = subscriberRef.get();

            for (final Method method : subscriberObj.getClass().getDeclaredMethods()) {
                Annotation annotation = method.getAnnotation(OnMessage.class);
                if (annotation != null) {
                    deliverMessage(subscriberObj, method, message, channelName);
                }
                 
            }
			/*
			 * for (final Method method : subscriberObj.getClass().getDeclaredMethods()) {
			 * Annotation annotation = method.getAnnotation(GetMessage.class); if
			 * (annotation != null) { deliverMessage(subscriberObj, method, message,
			 * channelName); }
			 * 
			 * }
			 */
        }
    }
    public void publishAll() {
    	Subscriber subscriber = new Subscriber();
    	for (final Method method : subscriber.getClass().getDeclaredMethods()) {
            Annotation annotation = method.getAnnotation(ApplySink.class);
            if (annotation != null) {
                deliverMessage(subscriber, method);
            }
             
        }
    }
    

    <T, P extends Post> boolean deliverMessage(T subscriber, Method method, Post message, String channelName) {
        try {
            boolean methodFound = false;
            for (final Class paramClass : method.getParameterTypes()) {
                if (paramClass.equals(message.getClass())) {
                    methodFound = true;
                    break;
                }
            }
            if (methodFound) {
                method.setAccessible(true);
                method.invoke(subscriber, channelName, message);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    <T, P extends Post> boolean deliverMessage(T subscriber, Method method) {
        try {
            boolean methodFound = false;
            method.setAccessible(true);
            method.invoke(subscriber);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
