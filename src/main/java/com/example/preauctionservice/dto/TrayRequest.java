package com.example.preauctionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class TrayRequest {

    @NonNull
    private String fishermanName;
    @NonNull
    private Double basePrice;
    @NonNull
    private String fishName;
    @NonNull
    private Double kilogram;
}
