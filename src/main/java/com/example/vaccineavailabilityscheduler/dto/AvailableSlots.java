package com.example.vaccineavailabilityscheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder(builderMethodName="hiddenBuilder")
@Data
@AllArgsConstructor
public class AvailableSlots {
    private List<AvailableSlot> slots;
    public static AvailableSlotsBuilder builder() {
        return hiddenBuilder();
    }

    public int count() {
        return slots.size();
    }

    public void print() {
        System.out.println(toString());
    }
}
