package com.bowlingDemo.bowling.service;

import org.springframework.stereotype.Service;
import com.bowlingDemo.bowling.request.BowlingRequest;
import com.bowlingDemo.bowling.response.TotalePartita;

import static java.lang.Integer.parseInt;

@Service
public class CalcolaFrameService {


    public static final String SPARE = "spare";
    public static final String STRIKE = "strike";
    public static final int SPARE_VALORE = 10;
    public static final int STRIKE_VALORE = 10;


    public void calcolaValoreFrame(BowlingRequest.Frame frame, TotalePartita totalePartita) {

        if (isPrimoFrame(frame)) {
            gestionePrimoFrame(frame, totalePartita);
        }

        if (isFrameIntermedio(frame))
            gestioneFrameIntermedi(frame, totalePartita);

        if (isUltimoFrame(frame)) {
            gestioneUltimoFrame(frame, totalePartita);
        }
    }

    private void gestioneUltimoFrame(BowlingRequest.Frame frame, TotalePartita totalePartita) {
        gestioneStrikeAndFrame(frame, totalePartita);
        gestioneNonoFrameSeStikeOSpare(frame, totalePartita);

        if (spare(frame)) {
            String valore = totalePartita.getFrames().get(totalePartita.getFrames().size() - 1).getValore();
            totalePartita.getFrames().add(TotalePartita.Frame.builder().numeoFrame(frame.getNumeroFrame()).valore("" +
                    (SPARE_VALORE + getValoreUltimoFrameSpare(frame) + parseInt(valore))).build());

        }
        if (strike(frame)) {
            String valore = totalePartita.getFrames().get(totalePartita.getFrames().size() - 1).getValore();

            if (STRIKE.equalsIgnoreCase(frame.getSecondoTiro())) {
                totalePartita.getFrames().add(TotalePartita.Frame.builder().numeoFrame(frame.getNumeroFrame()).valore("" +
                        (STRIKE_VALORE + getValoreUltimoStrike(frame) + parseInt(valore))).build());
            }

            if (SPARE.equalsIgnoreCase(frame.getTerzoTiro())) {
                totalePartita.getFrames().add(TotalePartita.Frame.builder().numeoFrame(frame.getNumeroFrame()).valore("" +
                        (STRIKE_VALORE + SPARE_VALORE + parseInt(valore))).build());
            }
        }


        if (!spare(frame) && !strike(frame) && STRIKE.equalsIgnoreCase(frame.getSecondoTiro())) {
            String valore = totalePartita.getFrames().get(totalePartita.getFrames().size() - 1).getValore();
            totalePartita.getFrames().add(TotalePartita.Frame.builder().numeoFrame(frame.getNumeroFrame()).valore(
                    "" + (parseInt(frame.getPrimoTiro()) + parseInt(frame.getSecondoTiro()) + parseInt(valore))).build());

        }
    }

    private void gestioneNonoFrameSeStikeOSpare(BowlingRequest.Frame frame, TotalePartita totalePartita) {
        String valorePrecendete = totalePartita.getFrames().get(totalePartita.getFrames().size() - 1).getValore();

        if (STRIKE.equalsIgnoreCase(valorePrecendete)) {

            if (strike(frame)) {
                if (STRIKE.equalsIgnoreCase(frame.getSecondoTiro())) {
                    totalePartita.getFrames().get(totalePartita.getFrames().size()-1).setValore("" + (getValoreDueFramePrecedenti(totalePartita) + STRIKE_VALORE + STRIKE_VALORE + STRIKE_VALORE));
                } else {
                    totalePartita.getFrames().get(totalePartita.getFrames().size()-1).setValore("" + (getValoreDueFramePrecedenti(totalePartita) + STRIKE_VALORE + STRIKE_VALORE + parseInt(frame.getSecondoTiro())));
                }

            }
            if (SPARE.equalsIgnoreCase(frame.getSecondoTiro())) {
                totalePartita.getFrames().get(totalePartita.getFrames().size()-1).setValore("" + (getValoreDueFramePrecedenti(totalePartita) + STRIKE_VALORE + SPARE_VALORE));

            }
            if (!STRIKE.equalsIgnoreCase(frame.getPrimoTiro()) && !SPARE.equalsIgnoreCase(frame.getSecondoTiro())) {
                totalePartita.getFrames().get(totalePartita.getFrames().size()-1).setValore("" + (getValoreDueFramePrecedenti(totalePartita) + parseInt(frame.getPrimoTiro()) + parseInt(frame.getSecondoTiro())));
            }
        }

        if (SPARE.equalsIgnoreCase(valorePrecendete)) {
            if (strike(frame)) {
                totalePartita.getFrames().get(totalePartita.getFrames().size()-1).setValore("" + (getValoreDueFramePrecedenti(totalePartita) + STRIKE_VALORE + SPARE_VALORE));
            } else {
                totalePartita.getFrames().get(totalePartita.getFrames().size()-1).setValore("" + (getValoreDueFramePrecedenti(totalePartita) + parseInt(frame.getPrimoTiro())));
            }

        }
    }

    private int getValoreUltimoStrike(BowlingRequest.Frame frame) {
        if (STRIKE.equalsIgnoreCase(frame.getSecondoTiro()) && STRIKE.equalsIgnoreCase(frame.getTerzoTiro())) {
            return STRIKE_VALORE + STRIKE_VALORE;
        }
        if (SPARE.equalsIgnoreCase(frame.getTerzoTiro())) {
            return SPARE_VALORE;
        }

        return parseInt(frame.getPrimoTiro()) + parseInt(frame.getSecondoTiro());

    }

    private int getValoreUltimoFrameSpare(BowlingRequest.Frame frame) {
        if (STRIKE.equalsIgnoreCase(frame.getTerzoTiro())) {
            return SPARE_VALORE;
        }
        return Integer.parseInt(frame.getTerzoTiro());
    }


    private boolean isFrameIntermedio(BowlingRequest.Frame frame) {
        return !(frame.getNumeroFrame().equalsIgnoreCase("1") || frame.getNumeroFrame().equalsIgnoreCase("10"));
    }


    private boolean isUltimoFrame(BowlingRequest.Frame frame) {
        return frame.getNumeroFrame().equalsIgnoreCase("10");

    }

    private void gestioneFrameIntermedi(BowlingRequest.Frame frame, TotalePartita totalePartita) {
        gestioneStrikeAndFrame(frame, totalePartita);

        if (spare(frame)) {

            totalePartita.getFrames().add(TotalePartita.Frame.builder().numeoFrame(frame.getNumeroFrame()).valore(
                    SPARE).build());
        }
        if (strike(frame)) {
            totalePartita.getFrames().add(TotalePartita.Frame.builder().numeoFrame(frame.getNumeroFrame()).valore(
                    STRIKE).build());
        }

        if (!spare(frame) && !strike(frame)) {
            String valorePrecendete = totalePartita.getFrames().get(totalePartita.getFrames().size() - 1).getValore();

            totalePartita.getFrames().add(TotalePartita.Frame.builder().numeoFrame(frame.getNumeroFrame()).valore(
                    "" + (parseInt(frame.getPrimoTiro()) + parseInt(frame.getSecondoTiro()) + parseInt(valorePrecendete))
            ).build());

        }
    }

    private void gestioneStrikeAndFrame(BowlingRequest.Frame frame, TotalePartita totalePartita) {
        if (isPrecedenteFrameSpare(totalePartita)) {
            totalePartita.getFrames().get(totalePartita.getFrames().size() - 1).setValore("" + (getValoreDueFramePrecedenti(totalePartita) + 10 + getValoreTiroAttualePerSpare(frame)));

        }
        if (isPrecendeteFrameStrike(totalePartita)) {
            if (isDueFramePrecedentiStrike(totalePartita)) {
                totalePartita.getFrames().get(totalePartita.getFrames().size() - 2).setValore("" + ((getValoreTreFramePrecedenti(totalePartita) + STRIKE_VALORE + STRIKE_VALORE + getValoreTiroAttualeStrike(frame))));

            }
            if (!strike(frame))
                totalePartita.getFrames().get(totalePartita.getFrames().size() - 1).setValore("" + ((getValoreDueFramePrecedenti(totalePartita) + STRIKE_VALORE + getValoreNonStike(frame))));

        }
    }

    private void gestionePrimoFrame(BowlingRequest.Frame frame, TotalePartita totalePartita) {
        if (spare(frame)) {

            totalePartita.getFrames().add(TotalePartita.Frame.builder().numeoFrame(frame.getNumeroFrame()).valore(
                    SPARE).build());
        }
        if (strike(frame)) {
            totalePartita.getFrames().add(TotalePartita.Frame.builder().numeoFrame(frame.getNumeroFrame()).valore(
                    STRIKE).build());
        }
        if (!spare(frame) && !strike(frame))
            totalePartita.getFrames().add(TotalePartita.Frame.builder().numeoFrame(frame.getNumeroFrame()).valore(
                    "" + (parseInt(frame.getPrimoTiro()) + parseInt(frame.getSecondoTiro()))
            ).build());
    }


    private boolean isDueFramePrecedentiStrike(TotalePartita totalePartita) {
        if (totalePartita.getFrames().size() < 2)
            return false;
        return STRIKE.equalsIgnoreCase(totalePartita.getFrames().get(totalePartita.getFrames().size() - 2).getValore());
    }

    private boolean isPrecendeteFrameStrike(TotalePartita totalePartita) {
        return STRIKE.equalsIgnoreCase(totalePartita.getFrames().get(totalePartita.getFrames().size() - 1).getValore());

    }

    private int getValoreDueFramePrecedenti(TotalePartita totalePartita) {
        if (totalePartita.getFrames().size() < 2)
            return 0;
        return Integer.parseInt(totalePartita.getFrames().get(totalePartita.getFrames().size() - 2).getValore());
    }

    private int getValoreTreFramePrecedenti(TotalePartita totalePartita) {
        if (totalePartita.getFrames().size() < 3)
            return 0;
        return Integer.parseInt(totalePartita.getFrames().get(totalePartita.getFrames().size() - 3).getValore());
    }

    private int getValoreTiroAttualePerSpare(BowlingRequest.Frame frame) {
        if (strike(frame))
            return STRIKE_VALORE;
        return parseInt(frame.getPrimoTiro());
    }

    private int getValoreNonStike(BowlingRequest.Frame frame) {
        if (spare(frame))
            return SPARE_VALORE;
        return parseInt(frame.getPrimoTiro()) + parseInt(frame.getSecondoTiro());
    }


    private int getValoreTiroAttualeStrike(BowlingRequest.Frame frame) {
        if (strike(frame))
            return STRIKE_VALORE;
        if (spare(frame))
            return SPARE_VALORE;
        return parseInt(frame.getPrimoTiro()) + parseInt(frame.getSecondoTiro());
    }


    private boolean isPrecedenteFrameSpare(TotalePartita totalePartita) {

        return SPARE.equalsIgnoreCase(totalePartita.getFrames().get(totalePartita.getFrames().size() - 1).getValore());
    }


    private boolean isPrimoFrame(BowlingRequest.Frame frame) {
        return frame.getNumeroFrame().equalsIgnoreCase("1");
    }


    private boolean strike(BowlingRequest.Frame frame) {
        return STRIKE.equalsIgnoreCase(frame.getPrimoTiro());

    }

    private boolean spare(BowlingRequest.Frame frame) {

        return SPARE.equalsIgnoreCase(frame.getSecondoTiro());

    }
}
