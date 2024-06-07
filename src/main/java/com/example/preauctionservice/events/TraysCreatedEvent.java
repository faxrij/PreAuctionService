package com.example.preauctionservice.events;

import com.example.preauctionservice.domain.Tray;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraysCreatedEvent {

    private Long auction_id;
    private LocalDateTime auction_ready_time;
    private LocalDateTime auction_start_time;
    private List<Tray> trays;
}
