package com.example.vaccineavailabilityscheduler.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
@Data
public class Slot implements Serializable {
    private String date;
    private int min_age_limit;
    private int available_capacity;
    private String vaccine;
    private int available_capacity_dose1;
    private int available_capacity_dose2;

    public Slot() {
        super();
    }

    public AvailableSlot toAvailableSlot(String name) {
        return AvailableSlot
                .builder()
                .name(name)
                .date(this.date)
                .min_age_limit(this.min_age_limit)
                .vaccine(this.vaccine)
                .available_capacity(this.available_capacity)
                .available_capacity_dose1(this.available_capacity_dose1)
                .available_capacity_dose2(this.available_capacity_dose2)
                .build();
    }

    public boolean isAvailableFor(int ageLimit, int dose) {
        return (min_age_limit == ageLimit) && (available_capacity > 0)
                && isAvailableFor(dose);
    }

    private boolean isAvailableFor(int dose) {
        return (dose == 1) ?
                (available_capacity_dose1 > 0)
                : (available_capacity_dose2 > 0);
    }
}
