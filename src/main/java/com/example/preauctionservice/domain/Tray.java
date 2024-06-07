package com.example.preauctionservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Tray {

    private long tray_id;
    private LocalDateTime tray_entry_time;
    private String fish_type;
    private double weight;
    private double base_price;
    private String fisherman_name;
}
