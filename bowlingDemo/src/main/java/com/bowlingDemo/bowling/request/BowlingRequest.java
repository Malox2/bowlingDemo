package com.bowlingDemo.bowling.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.ArrayList;

@Value
@Builder
public class BowlingRequest {

    private ArrayList <Frame> colpoArrayList;


    @Value
    @Builder
    public static class Frame {

        private String numeroFrame;
        private String primoTiro;
        private String secondoTiro;
        private String terzoTiro;
    }


}
