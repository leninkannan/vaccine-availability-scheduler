package com.example.vaccineavailabilityscheduler.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Data
public class Center implements Serializable {
    private String name;
    private String from;
    private String to;
    private List<Slot> sessions;

    public Center()  {
        super();
    }

    public  boolean isAvailableFor(int ageLimit, int dose) {
        return sessions.stream().anyMatch(slot-> slot.isAvailableFor(ageLimit,dose));
    }

    public AvailableSlots toAvailableSlotsFor(int ageLimit, int dose) {
        List<AvailableSlot> slots = sessions
                .stream()
                .filter(slot -> slot.isAvailableFor(ageLimit,dose))
                .map(slot -> slot.toAvailableSlot(name))
                .collect(toList());
        return AvailableSlots.builder().slots(slots).build();
    }
}
