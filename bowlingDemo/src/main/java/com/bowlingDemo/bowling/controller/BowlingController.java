package com.bowlingDemo.bowling.controller;

import com.bowlingDemo.bowling.service.CalcolaFrameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import com.bowlingDemo.bowling.response.TotalePartita;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.bowlingDemo.bowling.request.BowlingRequest;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
public class BowlingController {


    private final CalcolaFrameService calcolaFrameService;



    @PostMapping("/calcolaRisultato")
    public TotalePartita totalePartita(@RequestBody BowlingRequest bowlingRequest) {


        TotalePartita totalePartita = TotalePartita.builder().Frames(new ArrayList<>()).build();

        for (BowlingRequest.Frame frame : bowlingRequest.getColpoArrayList()) {

            System.out.println("Computo Frame");
            calcolaFrameService.calcolaValoreFrame(frame, totalePartita);
            totalePartita.getFrames().stream().forEach(System.out::println);
            System.out.println("////////////////////////////" );

        }

        return totalePartita;
    }
}







