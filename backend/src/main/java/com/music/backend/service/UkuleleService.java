package com.music.backend.service;

import com.music.backend.model.entity.GuitarString;
import com.music.backend.model.entity.Note;
import com.music.backend.model.instruments.Ukulele;
import com.music.backend.util.KeyFiller;
import com.music.backend.util.NoteFiller;
import com.music.backend.util.StringFiller;
import com.music.backend.util.Tone;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static java.lang.String.format;
import static java.util.Objects.isNull;

@Service
@Slf4j
public class UkuleleService {

    private static final int STRINGS_AMOUNT = 4;
    private static final int STRING_KEYS = 13;

    private final KeyFiller keyFiller;
    private final NoteFiller noteFiller;
    private final StringFiller stringFiller;

    @Value("${ukulele.open.notes}")
    private String[] openNotes;

    public UkuleleService(@Autowired KeyFiller keyFiller,
                          @Autowired NoteFiller noteFiller,
                          @Autowired StringFiller stringFiller) {
        this.keyFiller = keyFiller;
        this.noteFiller = noteFiller;
        this.stringFiller = stringFiller;
    }

    public Ukulele getRawUkulele() {
        final Ukulele ukulele = new Ukulele();
        ukulele.setStrings(stringFiller.getRawStrings(STRINGS_AMOUNT, STRING_KEYS, openNotes));

        return ukulele;
    }

    public Ukulele getCustomRawUkulele(String[] openNotes) {
        validateStringsAmount(openNotes);

        final Ukulele ukulele = new Ukulele();
        ukulele.setStrings(stringFiller.getRawStrings(STRINGS_AMOUNT, STRING_KEYS, openNotes));

        return ukulele;
    }

    public Ukulele getToneUkulele(Note toneNote, Tone tone) {
        final Ukulele ukulele = getRawUkulele();
        fillToneUkulele(toneNote, tone, ukulele);

        return ukulele;
    }

    public Ukulele getCustomToneUkulele(Note toneNote, Tone tone, String[] openNotes) {
        final Ukulele ukulele = getCustomRawUkulele(openNotes);
        fillToneUkulele(toneNote, tone, ukulele);

        return ukulele;
    }

    public Ukulele getChordUkulele(Note tonic, Tone tone) {
        final Ukulele ukulele = getRawUkulele();
        fillChordUkulele(tonic, tone, ukulele);

        return ukulele;
    }

    public Ukulele getCustomChordUkulele(Note tonic, Tone tone, String[] openNotes) {
        final Ukulele ukulele = getCustomRawUkulele(openNotes);
        fillChordUkulele(tonic, tone, ukulele);

        return ukulele;
    }

    public Ukulele getIntervalUkulele(int stringNumber, int keyNumber, int interval) {
        final Ukulele ukulele = getRawUkulele();
        fillIntervalUkulele(stringNumber, keyNumber, interval, ukulele);

        return ukulele;
    }

    public Ukulele getCustomIntervalUkulele(int stringNumber, int keyNumber, int interval, String[] openNotes) {
        final Ukulele ukulele = getCustomRawUkulele(openNotes);
        fillIntervalUkulele(stringNumber, keyNumber, interval, ukulele);

        return ukulele;
    }

    public Ukulele getPowerChordUkulele(Note tonic) {
        final Ukulele guitar = getRawUkulele();
        fillPowerChordUkulele(tonic, guitar);

        return guitar;
    }

    public Ukulele getCustomPowerChordUkulele(Note tonic, String[] openNotes) {
        final Ukulele guitar = getCustomRawUkulele(openNotes);
        fillPowerChordUkulele(tonic, guitar);

        return guitar;
    }

    private void fillToneUkulele(Note toneNote, Tone tone, Ukulele ukulele) {
        final Note[] notes = noteFiller.getNoteToneSequence(toneNote, tone);

        Arrays.stream(ukulele.getStrings())
                .forEach(string -> keyFiller.fillKeysInToneSequence(string.getKeys(), notes));
    }

    private void fillChordUkulele(Note tonic, Tone tone, Ukulele ukulele) {
        final Note[] notes = noteFiller.getChordNotes(tonic, tone);

        Arrays.stream(ukulele.getStrings())
                .forEach(string -> keyFiller.fillKeysInChord(string.getKeys(), notes));
    }

    private void fillIntervalUkulele(int stringNumber, int keyNumber, int interval, Ukulele ukulele) {
        if (stringNumber < 1 || stringNumber > STRINGS_AMOUNT || keyNumber < 0 || keyNumber > STRING_KEYS) {
            log.info("incorrect string number {} or key number {}. return raw ukulele", stringNumber, keyNumber);
            return;
        }

        final GuitarString[] strings = ukulele.getStrings();
        stringFiller.fillIntervalStrings(strings, stringNumber, keyNumber, interval);
    }

    private void fillPowerChordUkulele(Note tonic, Ukulele guitar) {
        final Note[] notes = noteFiller.getPowerChordNotes(tonic);
        Arrays.stream(guitar.getStrings())
                .forEach(string -> keyFiller.fillKeysInChord(string.getKeys(), notes));
    }

    private void validateStringsAmount(String[] openNotes) {
        if (isNull(openNotes) || openNotes.length != STRINGS_AMOUNT) {
            log.error("open notes length is not ukulele strings amount {}", STRINGS_AMOUNT);
            throw new IllegalArgumentException(
                    format("open notes length is not ukulele strings amount %d", STRINGS_AMOUNT));
        }
    }
}
