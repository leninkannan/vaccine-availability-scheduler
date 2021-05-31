package com.example.vaccineavailabilityscheduler.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Data
public class SlotResponse implements Serializable {
    private List<Center> centers;

    public SlotResponse() {
        super();
    }

    public boolean isAvailableFor(int ageLimit, int dose) {
       return centers
               .stream()
               .anyMatch(center->center.isAvailableFor(ageLimit,dose));
    }

    public AvailableSlots getAvailableSlotsFor(int ageLimit, int dose) {
        List<AvailableSlot> availableSlots  = new ArrayList<>();

        List<Center> availableCenters = centers
                .stream()
                .filter(center -> center.isAvailableFor(ageLimit, dose))
                .collect(toList());

        List<AvailableSlots> availableSlotsList = availableCenters
                .stream()
                .map(center -> center.toAvailableSlotsFor(ageLimit,dose))
                .collect(toList());

        availableSlotsList
                .stream()
                .forEach(slotsList -> availableSlots.addAll(slotsList.getSlots()) );

        return AvailableSlots
                .builder()
                .slots(availableSlots)
                .build();
    }
}
