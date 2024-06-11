package com.example.preauctionservice.services;

import com.example.preauctionservice.domain.Tray;
import com.example.preauctionservice.dto.TrayRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrayService {

    private String trayId;
    @Getter
    private List<Tray> trays = new ArrayList<>();

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int RANDOM_PART_LENGTH = 7;
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");


    public void createTray(TrayRequest trayRequest) {
        trayId = generateUniqueTrayId();
        Tray tray = new Tray(trayId, LocalDateTime.now(), trayRequest.getFishName(), trayRequest.getKilogram(),
                trayRequest.getBasePrice(), trayRequest.getFishermanName());

        trays.add(tray);
    }

    public void emptyTrays() {
        trays.clear();
        trayId = null;
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

    private String generateUniqueTrayId() {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
        String randomPart = generateRandomString();
        return timestamp + randomPart;
    }
}
