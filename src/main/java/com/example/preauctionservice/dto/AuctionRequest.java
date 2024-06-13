package com.example.preauctionservice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class AuctionRequest {

    private LocalDateTime readyTime;
}
