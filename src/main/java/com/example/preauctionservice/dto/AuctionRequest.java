package com.example.preauctionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AuctionRequest {

    @NonNull
    private LocalDateTime readyTime;
    @NonNull
    private LocalDateTime startTime;
}
