package service;

import com.bowlingDemo.bowling.service.CalcolaFrameService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import com.bowlingDemo.bowling.request.BowlingRequest;
import com.bowlingDemo.bowling.response.TotalePartita;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class CalcolaFrameServiceTest {


    @InjectMocks
    private CalcolaFrameService calcolaFrameService;


    @Test
    public void calcolaValoreFramePrimoColpoNoStrikeNoSpare() {
        TotalePartita totalePartita = TotalePartita.builder().Frames(new ArrayList<>()).build();
        calcolaFrameService.calcolaValoreFrame(BowlingRequest.Frame.builder().numeroFrame("1").primoTiro("1").secondoTiro("2").build(),
                totalePartita);

        Assertions.assertThat(totalePartita.getFrames().get(0).getValore()).isEqualTo("3");
        Assertions.assertThat(totalePartita.getFrames().get(0).getNumeoFrame()).isEqualTo("1");


    }

    @Test
    public void calcolaValoreFramePrimoColpoSpare() {
        TotalePartita totalePartita = TotalePartita.builder().Frames(new ArrayList<>()).build();
        calcolaFrameService.calcolaValoreFrame(BowlingRequest.Frame.builder().numeroFrame("1").primoTiro("1").secondoTiro("spare").build(),
                totalePartita);

        Assertions.assertThat(totalePartita.getFrames().get(0).getValore()).isEqualTo("spare");
        Assertions.assertThat(totalePartita.getFrames().get(0).getNumeoFrame()).isEqualTo("1");


    }

    @Test
    public void calcolaValoreFramePrimoColpoStrike() {
        TotalePartita totalePartita = TotalePartita.builder().Frames(new ArrayList<>()).build();
        calcolaFrameService.calcolaValoreFrame(BowlingRequest.Frame.builder().numeroFrame("1").primoTiro("strike").build(),
                totalePartita);

        Assertions.assertThat(totalePartita.getFrames().get(0).getValore()).isEqualTo("strike");
        Assertions.assertThat(totalePartita.getFrames().get(0).getNumeoFrame()).isEqualTo("1");


    }

    @Test
    public void calcolaValoreFramePrimoColpoNoStrikeNoSpareSecondooStrikeNoSpare() {
        ArrayList<TotalePartita.Frame> frameArrayList = new ArrayList<>() ;
        frameArrayList.add(TotalePartita.Frame.builder().numeoFrame("1").valore("5").build());
        TotalePartita totalePartita = TotalePartita.builder().Frames(frameArrayList).build();

        calcolaFrameService.calcolaValoreFrame(BowlingRequest.Frame.builder()
                        .numeroFrame("2").primoTiro("4").secondoTiro("5").build(),
                totalePartita);

        Assertions.assertThat(totalePartita.getFrames().get(0).getValore()).isEqualTo("5");
        Assertions.assertThat(totalePartita.getFrames().get(0).getNumeoFrame()).isEqualTo("1");
        Assertions.assertThat(totalePartita.getFrames().get(1).getValore()).isEqualTo("14");
        Assertions.assertThat(totalePartita.getFrames().get(1).getNumeoFrame()).isEqualTo("2");


    }


    @Test
    public void calcolaValoreFrameTiroPrecenteSpareSuccessivoStrike() {
        ArrayList<TotalePartita.Frame> frameArrayList = new ArrayList<>() ;
        frameArrayList.add(TotalePartita.Frame.builder().numeoFrame("1").valore("spare").build());
        TotalePartita totalePartita = TotalePartita.builder().Frames(frameArrayList).build();

        calcolaFrameService.calcolaValoreFrame(BowlingRequest.Frame.builder()
                        .numeroFrame("2").primoTiro("strike").secondoTiro("5").build(),
                totalePartita);

        Assertions.assertThat(totalePartita.getFrames().get(0).getValore()).isEqualTo("20");
        Assertions.assertThat(totalePartita.getFrames().get(0).getNumeoFrame()).isEqualTo("1");
        Assertions.assertThat(totalePartita.getFrames().get(1).getValore()).isEqualTo("strike");
        Assertions.assertThat(totalePartita.getFrames().get(1).getNumeoFrame()).isEqualTo("2");


    }

    @Test
    public void calcolaValoreFrameTriploStrike() {
        ArrayList<TotalePartita.Frame> frameArrayList = new ArrayList<>() ;
        frameArrayList.add(TotalePartita.Frame.builder().numeoFrame("1").valore("strike").build());
        frameArrayList.add(TotalePartita.Frame.builder().numeoFrame("2").valore("strike").build());
        TotalePartita totalePartita = TotalePartita.builder().Frames(frameArrayList).build();

        calcolaFrameService.calcolaValoreFrame(BowlingRequest.Frame.builder()
                        .numeroFrame("3").primoTiro("strike").build(),
                totalePartita);

        Assertions.assertThat(totalePartita.getFrames().get(0).getValore()).isEqualTo("30");
        Assertions.assertThat(totalePartita.getFrames().get(0).getNumeoFrame()).isEqualTo("1");
        Assertions.assertThat(totalePartita.getFrames().get(1).getValore()).isEqualTo("strike");
        Assertions.assertThat(totalePartita.getFrames().get(1).getNumeoFrame()).isEqualTo("2");
        Assertions.assertThat(totalePartita.getFrames().get(2).getValore()).isEqualTo("strike");
        Assertions.assertThat(totalePartita.getFrames().get(2).getNumeoFrame()).isEqualTo("3");


    }    @Test
    public void calcolaValoreFrameQuadruploStrike() {
        ArrayList<TotalePartita.Frame> frameArrayList = new ArrayList<>() ;
        frameArrayList.add(TotalePartita.Frame.builder().numeoFrame("1").valore("30").build());
        frameArrayList.add(TotalePartita.Frame.builder().numeoFrame("2").valore("strike").build());
        frameArrayList.add(TotalePartita.Frame.builder().numeoFrame("3").valore("strike").build());
        TotalePartita totalePartita = TotalePartita.builder().Frames(frameArrayList).build();

        calcolaFrameService.calcolaValoreFrame(BowlingRequest.Frame.builder()
                        .numeroFrame("4").primoTiro("strike").build(),
                totalePartita);

        Assertions.assertThat(totalePartita.getFrames().get(0).getValore()).isEqualTo("30");
        Assertions.assertThat(totalePartita.getFrames().get(0).getNumeoFrame()).isEqualTo("1");
        Assertions.assertThat(totalePartita.getFrames().get(1).getValore()).isEqualTo("60");
        Assertions.assertThat(totalePartita.getFrames().get(1).getNumeoFrame()).isEqualTo("2");
        Assertions.assertThat(totalePartita.getFrames().get(2).getValore()).isEqualTo("strike");
        Assertions.assertThat(totalePartita.getFrames().get(2).getNumeoFrame()).isEqualTo("3");
        Assertions.assertThat(totalePartita.getFrames().get(3).getValore()).isEqualTo("strike");
        Assertions.assertThat(totalePartita.getFrames().get(3).getNumeoFrame()).isEqualTo("4");


    }

}
