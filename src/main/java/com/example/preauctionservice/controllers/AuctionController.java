package com.example.preauctionservice.controllers;

import com.example.preauctionservice.dto.AuctionRequest;
import com.example.preauctionservice.services.AuctionService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auction")
@RequiredArgsConstructor
public class AuctionController {

    private final AuctionService auctionService;

    @PostMapping
    public void createAuction(@RequestBody AuctionRequest auctionRequest) throws BadRequestException {
        auctionService.createAuction(auctionRequest);
    }

    @PostMapping("/start")
    public void startAuction() {
        auctionService.startAuction();
    }
}
