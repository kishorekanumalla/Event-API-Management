package com.api.event.gateway.pubsub;

public class ClientMessage extends Post {
    String message;
    String client;

    public ClientMessage(String client,String message) {
        super(client, message);
        this.message = message;
        this.client = client;
    }
}
