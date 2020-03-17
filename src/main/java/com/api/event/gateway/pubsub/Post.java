package com.api.event.gateway.pubsub;

abstract class Post {
    String message;
    String client;

    Post(String client, String message) {
        this.message = message;
        this.client = client;
    }
}
