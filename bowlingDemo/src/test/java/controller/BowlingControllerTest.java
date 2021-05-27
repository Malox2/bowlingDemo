package controller;

import com.bowlingDemo.bowling.controller.BowlingController;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.bowlingDemo.bowling.request.BowlingRequest;
import com.bowlingDemo.bowling.response.TotalePartita;
import com.bowlingDemo.bowling.service.CalcolaFrameService;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class BowlingControllerTest {


    @InjectMocks
    private BowlingController bowlingController;

    @Mock
    private CalcolaFrameService calcolaFrameService;


    @Test
    public void totalePartita() {

        ArrayList<BowlingRequest.Frame> frameArrayList = new ArrayList<>();

        BowlingRequest.Frame primoFrame = BowlingRequest.Frame.builder().primoTiro("1").secondoTiro("2").build();
        frameArrayList.add(primoFrame);
        BowlingRequest.Frame secondoFrame = BowlingRequest.Frame.builder().primoTiro("3").secondoTiro("5").build();
        frameArrayList.add(secondoFrame);


        TotalePartita totalePartitaFinaleCalcolato = bowlingController.totalePartita(BowlingRequest.builder().colpoArrayList(frameArrayList).build());
        verify(calcolaFrameService).calcolaValoreFrame(eq(primoFrame), any());

        verify(calcolaFrameService).calcolaValoreFrame(eq(secondoFrame),any());

        Assertions.assertThat(totalePartitaFinaleCalcolato).isNotNull();


    }
}
