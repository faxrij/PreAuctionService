package com.example.preauctionservice.services;

import com.example.preauctionservice.domain.Tray;
import com.example.preauctionservice.dto.TrayRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrayService {

    private long trayId;
    @Getter
    private List<Tray> trays = new ArrayList<>();

    public void createTray(TrayRequest trayRequest) {
        Tray tray = new Tray(trayId, LocalDateTime.now(), trayRequest.getFishName(), trayRequest.getKilogram(),
                trayRequest.getBasePrice(), trayRequest.getFishermanName());

        trays.add(tray);
        trayId++;
    }

    public void emptyTrays() {
        trays.clear();
        trayId = 0;
    }
}
