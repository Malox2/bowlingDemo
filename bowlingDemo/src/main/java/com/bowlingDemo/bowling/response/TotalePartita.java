package com.bowlingDemo.bowling.response;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.ArrayList;


@Value
@Builder
public class TotalePartita {

    private ArrayList<Frame> Frames;


    @Builder
    @Data
    public static class Frame {
        private String numeoFrame;
        private String valore;
    }


}
