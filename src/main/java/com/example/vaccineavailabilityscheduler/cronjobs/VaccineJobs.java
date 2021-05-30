package com.example.vaccineavailabilityscheduler.cronjobs;


import com.example.vaccineavailabilityscheduler.dto.AvailableSlots;
import com.example.vaccineavailabilityscheduler.dto.SlotResponse;
import com.example.vaccineavailabilityscheduler.utils.SoundUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.sound.sampled.LineUnavailableException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Component
public class VaccineJobs {
    final
    RestTemplate restTemplate;

    @Autowired
    public VaccineJobs(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${pincodes}")
    Integer[] pincodes;

    @Scheduled(cron="*/10 * * * * ?")
    public void execute()
    {
        List<Integer> pincodesList = Arrays.asList(pincodes);
        pincodesList.stream().forEach(pincode-> checktheAvailability(pincode));
    }

    private void checktheAvailability(Integer pincode) {
        String vaccineSlotsAPIEndPoint = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByPin?pincode="+pincode+"&date="+todayDate();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
            ResponseEntity<SlotResponse> response = restTemplate.exchange(vaccineSlotsAPIEndPoint, HttpMethod.GET, entity, SlotResponse.class);
            long availableSlotsCount = response.getBody().availableSlots();

            if (availableSlotsCount > 1) {
                System.out.println("Slots["+availableSlotsCount+"] are available for pincode: "+pincode+" by "+ LocalDateTime.now()+ ", Book it quickly ");
                AvailableSlots availableSlots = response.getBody().getAvailableSlotsForDose1ForAbove18();
                availableSlots.print();
                GenerateBeepSound();
                System.out.println();
            }
            else if (response.getBody().isSlotAvailablesFor18Above()) {
                System.out.println("Slots are available for Dose2 and for above18...");
                AvailableSlots availableSlots = response.getBody().getAvailableSlotsForAbove18();
                availableSlots.print();
                System.out.println();
            }
        }catch (Exception exception) {
            System.out.println("Exception:"+exception);
        }
    }
    private String todayDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.now().format(formatter);

    }

    private void GenerateBeepSound() throws InterruptedException, LineUnavailableException {
                for(int i =1; i<=20;i++) {
                    SoundUtils.tone(1000, 100);
                    Thread.sleep(1000);
                }
    }
}
