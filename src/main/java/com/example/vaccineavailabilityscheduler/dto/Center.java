package com.example.vaccineavailabilityscheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.session.InMemoryWebSessionStore;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

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

    public boolean isAvailableForDose1ForAbove18() {
        return sessions.stream().filter(Slot::isAvailableForDose1ForAbove18).findFirst().isPresent();
    }
    public boolean isAvailableForAbove18() {
        return sessions.stream().filter(Slot::isAvailableForAbove18).findFirst().isPresent();
    }

    public AvailableSlots toAvailableSlotsForDose1AndForAbove18() {
        List<AvailableSlot> slots = sessions.stream().filter(Slot::isAvailableForDose1ForAbove18).map(slot -> slot.toAvailableSlot(name)).collect(Collectors.toList());
        return AvailableSlots.builder().slots(slots).build();
    }
    public AvailableSlots toAvailableSlotsForAbove18() {
        List<AvailableSlot> slots = sessions.stream().filter(Slot::isAvailableForAbove18).map(slot -> slot.toAvailableSlot(name)).collect(Collectors.toList());
        return AvailableSlots.builder().slots(slots).build();
    }
}
