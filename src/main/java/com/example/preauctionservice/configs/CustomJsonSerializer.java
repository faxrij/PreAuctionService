package com.example.preauctionservice.configs;

import com.example.preauctionservice.events.AuctionReadyEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class CustomJsonSerializer {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    public static String serializeTraysCreatedEvent(AuctionReadyEvent event) throws JsonProcessingException {
        return objectMapper.writeValueAsString(event);
    }
}
