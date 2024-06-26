package com.example.preauctionservice.controllers;

import com.example.preauctionservice.domain.Tray;
import com.example.preauctionservice.dto.TrayRequest;
import com.example.preauctionservice.services.TrayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tray")
@RequiredArgsConstructor
@CrossOrigin
public class TrayController {

    private final TrayService trayService;

    @PostMapping
    public void createTray(@RequestBody TrayRequest trayRequest) {
        trayService.createTray(trayRequest);
    }

    @GetMapping
    public List<Tray> getTrays() {
        return trayService.getTrays();
    }
}
