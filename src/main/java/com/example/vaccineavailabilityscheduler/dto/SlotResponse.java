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
    public long availableSlots() {
        List<Center> availableCenters = centers.stream().filter(Center::isAvailableForDose1ForAbove18).collect(toList());
        long totalCount = availableCenters.stream().mapToLong(center -> center.getSessions().stream().filter(Slot::isAvailableForDose1ForAbove18).mapToLong(Slot::getAvailable_capacity_dose1).sum()).sum();
        return totalCount;
    }
    public AvailableSlots getAvailableSlotsForDose1ForAbove18() {
        List<Center> availableCenters = centers.stream().filter(Center::isAvailableForDose1ForAbove18).collect(toList());
        List<AvailableSlots> availableSlotsList = availableCenters.stream().map(center -> center.toAvailableSlotsForDose1AndForAbove18()).collect(toList());
        List<AvailableSlot> availableSlots  = new ArrayList<>();
        availableSlotsList.stream().forEach(slotsList -> availableSlots.addAll(slotsList.getSlots()) );
        return AvailableSlots.builder().slots(availableSlots).build();
    }

    public boolean isSlotAvailablesFor18Above() {
        return centers.stream().filter((center -> center.isAvailableForAbove18())).findFirst().isPresent();
    }
    public AvailableSlots getAvailableSlotsForAbove18() {
        List<Center> availableCenters = centers.stream().filter(Center::isAvailableForAbove18).collect(toList());
        List<AvailableSlots> availableSlotsList = availableCenters.stream().map(center -> center.toAvailableSlotsForAbove18()).collect(toList());
        List<AvailableSlot> availableSlots  = new ArrayList<>();
        availableSlotsList.stream().forEach(slotsList -> availableSlots.addAll(slotsList.getSlots()) );
        return AvailableSlots.builder().slots(availableSlots).build();
    }
}
