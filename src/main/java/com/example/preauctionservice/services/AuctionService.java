package com.example.preauctionservice.services;

import com.example.preauctionservice.configs.CustomJsonSerializer;
import com.example.preauctionservice.domain.Tray;
import com.example.preauctionservice.dto.AuctionRequest;
import com.example.preauctionservice.events.TraysCreatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Log4j2
public class AuctionService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private long auctionId;
    private LocalDateTime lastAuctionCreatedDate;
    private final TrayService trayService;
    private LocalDateTime auction_ready_time;
    private LocalDateTime auction_start_time;

    public AuctionService(KafkaTemplate<String, Object> kafkaTemplate, TrayService trayService) {
        this.kafkaTemplate = kafkaTemplate;
        this.trayService = trayService;
        auctionId = 0;
    }

    public void startAuction() {
        List<Tray> trays = trayService.getTrays();

        TraysCreatedEvent event = new TraysCreatedEvent(auctionId, auction_ready_time, auction_start_time, trays);

        try {
            String jsonEvent = CustomJsonSerializer.serializeTraysCreatedEvent(event);
            kafkaTemplate.send("traysCreatedTopic", jsonEvent);
            System.out.println(jsonEvent);
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
            auctionId += 1;
            lastAuctionCreatedDate = now;
            auction_ready_time = auctionRequest.getReadyTime();
            auction_start_time = auctionRequest.getStartTime();
        }
    }
}
