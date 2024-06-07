package com.example.preauctionservice.controllers;

import com.example.preauctionservice.dto.TrayRequest;
import com.example.preauctionservice.services.TrayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tray")
@RequiredArgsConstructor
public class TrayController {

    private final TrayService trayService;

    @PostMapping
    public void createTray(@RequestBody TrayRequest trayRequest) {
        trayService.createTray(trayRequest);
    }
}
