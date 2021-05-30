package com.example.vaccineavailabilityscheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Builder(builderMethodName="hiddenBuilder")
@Data
@AllArgsConstructor
public class AvailableSlot {
    private String name;
    private String date;
    private int min_age_limit;
    private int available_capacity;
    private String vaccine;
    private int available_capacity_dose1;
    private int available_capacity_dose2;

    public static AvailableSlotBuilder builder() {
        return hiddenBuilder();
    }
}
