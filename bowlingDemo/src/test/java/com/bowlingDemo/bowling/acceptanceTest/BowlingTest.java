package com.bowlingDemo.bowling.acceptanceTest;


import com.bowlingDemo.bowling.BowlingDemoApplication;
import com.bowlingDemo.bowling.response.TotalePartita;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.MvcResult;
import com.bowlingDemo.bowling.request.BowlingRequest;

import java.util.ArrayList;
@SpringBootTest(classes = BowlingDemoApplication.class)
@AutoConfigureMockMvc
public class BowlingTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void acceptanceTest() throws Exception {


        ArrayList<BowlingRequest.Frame> frameArrayList = new ArrayList<>() ;

       frameArrayList.add( BowlingRequest.Frame.builder().numeroFrame("1").primoTiro("1").secondoTiro("4").build());
        frameArrayList.add( BowlingRequest.Frame.builder().numeroFrame("2").primoTiro("4").secondoTiro("5").build());
        frameArrayList.add( BowlingRequest.Frame.builder().numeroFrame("3").primoTiro("6").secondoTiro("spare").build());
        frameArrayList.add( BowlingRequest.Frame.builder().numeroFrame("4").primoTiro("5").secondoTiro("spare").build());
        frameArrayList.add( BowlingRequest.Frame.builder().numeroFrame("5").primoTiro("strike").build());
        frameArrayList.add( BowlingRequest.Frame.builder().numeroFrame("6").primoTiro("0").secondoTiro("1").build());
        frameArrayList.add( BowlingRequest.Frame.builder().numeroFrame("7").primoTiro("7").secondoTiro("spare").build());
        frameArrayList.add( BowlingRequest.Frame.builder().numeroFrame("8").primoTiro("6").secondoTiro("spare").build());
        frameArrayList.add( BowlingRequest.Frame.builder().numeroFrame("9").primoTiro("strike").build());
        frameArrayList.add( BowlingRequest.Frame.builder().numeroFrame("10").primoTiro("2").secondoTiro("spare").terzoTiro("6").build());
        BowlingRequest bowlingRequest  = BowlingRequest.builder().colpoArrayList(frameArrayList).build() ;


        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(bowlingRequest);


        MvcResult mvcResult = mvc.perform(post("/calcolaRisultato")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk()).andReturn();


        ArrayList<TotalePartita.Frame> arrayListFrame = new ArrayList<>() ;
        arrayListFrame.add(TotalePartita.Frame.builder().numeoFrame("1").valore("5").build());
        arrayListFrame.add(TotalePartita.Frame.builder().numeoFrame("2").valore("14").build());
        arrayListFrame.add(TotalePartita.Frame.builder().numeoFrame("3").valore("29").build());
        arrayListFrame.add(TotalePartita.Frame.builder().numeoFrame("4").valore("49").build());
        arrayListFrame.add(TotalePartita.Frame.builder().numeoFrame("5").valore("60").build());
        arrayListFrame.add(TotalePartita.Frame.builder().numeoFrame("6").valore("61").build());
        arrayListFrame.add(TotalePartita.Frame.builder().numeoFrame("7").valore("77").build());
        arrayListFrame.add(TotalePartita.Frame.builder().numeoFrame("8").valore("97").build());
        arrayListFrame.add(TotalePartita.Frame.builder().numeoFrame("9").valore("117").build());
        arrayListFrame.add(TotalePartita.Frame.builder().numeoFrame("10").valore("133").build());

        TotalePartita totalePartita= TotalePartita.builder().Frames(arrayListFrame).build();

        String expected = ow.writeValueAsString(totalePartita);

        Assertions.assertThat(expected).isEqualToIgnoringWhitespace( mvcResult.getResponse().getContentAsString());
    }

}


