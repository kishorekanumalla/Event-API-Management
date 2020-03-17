package com.api.event.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.api.event.gateway.common.Utils;
import com.api.event.gateway.pubsub.Event;
import com.api.event.gateway.pubsub.Subscriber;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class EventAPIMainApplication {

	public static void main(String[] args) {

		// Initialize Channels and Subscribers for the Client Events.
		Subscriber eType1Sub = new Subscriber(Utils.Subscribers.ETYPE_1_SUB.toString());
		Subscriber eType2Sub = new Subscriber(Utils.Subscribers.ETYPE_2_SUB.toString());
		Subscriber eType3Sub = new Subscriber(Utils.Subscribers.ETYPE_3_SUB.toString());
		Subscriber eType4Sub = new Subscriber(Utils.Subscribers.ETYPE_4_SUB.toString());

		Event.operation.subscribe(Utils.Subscribers.ETYPE_1_SUB.channel.toString(), eType1Sub);
		Event.operation.subscribe(Utils.Subscribers.ETYPE_2_SUB.channel.toString(), eType2Sub);
		Event.operation.subscribe(Utils.Subscribers.ETYPE_3_SUB.channel.toString(), eType3Sub);
		Event.operation.subscribe(Utils.Subscribers.ETYPE_4_SUB.channel.toString(), eType4Sub);

		SpringApplication.run(EventAPIMainApplication.class, args);

	}

	@Bean
	public Docket eventApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("eventApi").select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.regex("/event-api.*")).build();
	}
	@Bean
	public Docket eventMetricsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("eventMetricsApi").select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.regex("/auth.*")).build();
	}

}
