package com.example.preauctionservice.services;

import com.example.preauctionservice.configs.CustomJsonSerializer;
import com.example.preauctionservice.domain.Tray;
import com.example.preauctionservice.dto.AuctionRequest;
import com.example.preauctionservice.events.AuctionReadyEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Log4j2
public class AuctionService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private String auctionId;
    private LocalDateTime lastAuctionCreatedDate;
    private final TrayService trayService;
    private LocalDateTime auction_ready_time;
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int RANDOM_PART_LENGTH = 7;
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    public AuctionService(KafkaTemplate<String, Object> kafkaTemplate, TrayService trayService) {
        this.kafkaTemplate = kafkaTemplate;
        this.trayService = trayService;
        this.auctionId = generateUniqueAuctionId();
    }

    public void startAuction() {
        List<Tray> trays = trayService.getTrays();

        AuctionReadyEvent event = new AuctionReadyEvent(auctionId, auction_ready_time, trays);

        try {
            String jsonEvent = CustomJsonSerializer.serializeTraysCreatedEvent(event);
            kafkaTemplate.send("auction_ready_event", jsonEvent);
            trayService.emptyTrays();
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

    public void createAuction(AuctionRequest auctionRequest) throws Exception {
        LocalDateTime now = LocalDateTime.now();

        if (auctionRequest.getStartTime().isBefore(now) || auctionRequest.getReadyTime().isBefore(now)) {
            throw new Exception();
        }

        if (lastAuctionCreatedDate == null || Duration.between(lastAuctionCreatedDate, now).toHours() >= 23) {
            auctionId = generateUniqueAuctionId();
            lastAuctionCreatedDate = now;
            auction_ready_time = auctionRequest.getReadyTime();
        }
    }

    private String generateRandomString() {
        SecureRandom random = new SecureRandom();
        StringBuilder builder = new StringBuilder(RANDOM_PART_LENGTH);
        for (int i = 0; i < RANDOM_PART_LENGTH; i++) {
            int character = random.nextInt(ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    private String generateUniqueAuctionId() {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
        String randomPart = generateRandomString();
        return timestamp + randomPart;
    }

}
