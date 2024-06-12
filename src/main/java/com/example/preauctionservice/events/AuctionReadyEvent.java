package com.example.preauctionservice.events;

import com.example.preauctionservice.domain.Tray;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AuctionReadyEvent {

    private String auction_id;
    private LocalDateTime auction_ready_time;
    private List<Tray> trays;
    private String auction_status;

    public AuctionReadyEvent(String auctionId, LocalDateTime auctionReadyTime, List<Tray> trays) {
        auction_id = auctionId;
        auction_ready_time = auctionReadyTime;
        this.trays = trays;
        auction_status = "ready";
    }
}
