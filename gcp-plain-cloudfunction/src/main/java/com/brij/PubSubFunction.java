package com.brij;

import com.google.cloud.functions.CloudEventsFunction;

import com.google.events.cloud.pubsub.v1.MessagePublishedData;
import com.google.gson.Gson;
import io.cloudevents.CloudEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class PubSubFunction implements CloudEventsFunction {
    private static final Logger logger = LoggerFactory.getLogger(PubSubFunction.class);

    @Override
    public void accept(CloudEvent event) {

        // Extract Cloud Event data and convert to PubSubBody
        String cloudEventData = new String(event.getData().toBytes());
        Gson gson = new Gson();
        MessagePublishedData pubsubMessage = gson.fromJson(cloudEventData, MessagePublishedData.class);
        // Retrieve and decode PubSub message data
        String encodedData = pubsubMessage.getMessage().getData();
        String decodedData = new String(Base64.getDecoder()
                .decode(encodedData), StandardCharsets.UTF_8);
        logger.info("message {} ", decodedData);
    }

}
