package com.music.backend.service;

import com.music.backend.model.entity.GuitarString;
import com.music.backend.model.entity.Note;
import com.music.backend.model.instruments.Guitar;
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
public class GuitarService {

    private static final int STRINGS_AMOUNT = 6;
    private static final int STRING_KEYS = 25;

    private final KeyFiller keyFiller;
    private final NoteFiller noteFiller;
    private final StringFiller stringFiller;

    @Value("${guitar.open.notes}")
    private String[] openNotes;

    public GuitarService(@Autowired KeyFiller keyFiller,
                         @Autowired NoteFiller noteFiller,
                         @Autowired StringFiller stringFiller) {
        this.keyFiller = keyFiller;
        this.noteFiller = noteFiller;
        this.stringFiller = stringFiller;
    }

    public Guitar getRawGuitar() {
        final Guitar guitar = new Guitar();
        guitar.setStrings(stringFiller.getRawStrings(STRINGS_AMOUNT, STRING_KEYS, openNotes));

        return guitar;
    }

    public Guitar getCustomRawGuitar(String[] openNotes) {
        validateStringsAmount(openNotes);

        final Guitar guitar = new Guitar();
        guitar.setStrings(stringFiller.getRawStrings(STRINGS_AMOUNT, STRING_KEYS, openNotes));

        return guitar;
    }

    public Guitar getToneGuitar(Note toneNote, Tone tone) {
        final Guitar guitar = getRawGuitar();
        fillToneGuitar(toneNote, tone, guitar);

        return guitar;
    }

    public Guitar getCustomToneGuitar(Note toneNote, Tone tone, String[] openNotes) {
        final Guitar guitar = getCustomRawGuitar(openNotes);
        fillToneGuitar(toneNote, tone, guitar);

        return guitar;
    }

    public Guitar getChordGuitar(Note tonic, Tone tone) {
        final Guitar guitar = getRawGuitar();
        fillChordGuitar(tonic, tone, guitar);

        return guitar;
    }

    public Guitar getCustomChordGuitar(Note tonic, Tone tone, String[] openNotes) {
        final Guitar guitar = getCustomRawGuitar(openNotes);
        fillChordGuitar(tonic, tone, guitar);

        return guitar;
    }

    public Guitar getIntervalGuitar(int stringNumber, int keyNumber, int interval) {
        final Guitar guitar = getRawGuitar();
        fillIntervalGuitar(stringNumber, keyNumber, interval, guitar);

        return guitar;
    }

    public Guitar getCustomIntervalGuitar(int stringNumber, int keyNumber, int interval, String[] openNotes) {
        final Guitar guitar = getCustomRawGuitar(openNotes);
        fillIntervalGuitar(stringNumber, keyNumber, interval, guitar);

        return guitar;
    }

    public Guitar getPowerChordGuitar(Note tonic) {
        final Guitar guitar = getRawGuitar();
        fillPowerChordGuitar(tonic, guitar);

        return guitar;
    }

    public Guitar getCustomPowerChordGuitar(Note tonic, String[] openNotes) {
        final Guitar guitar = getCustomRawGuitar(openNotes);
        fillPowerChordGuitar(tonic, guitar);

        return guitar;
    }

    private void fillToneGuitar(Note toneNote, Tone tone, Guitar guitar) {
        final Note[] toneSequence = noteFiller.getNoteToneSequence(toneNote, tone);
        Arrays.stream(guitar.getStrings())
                .forEach(string -> keyFiller.fillKeysInToneSequence(string.getKeys(), toneSequence));
    }

    private void fillChordGuitar(Note tonic, Tone tone, Guitar guitar) {
        final Note[] chordNotes = noteFiller.getChordNotes(tonic, tone);
        Arrays.stream(guitar.getStrings())
                .forEach(string -> keyFiller.fillKeysInChord(string.getKeys(), chordNotes));
    }

    private void fillIntervalGuitar(int stringNumber, int keyNumber, int interval, Guitar guitar) {
        if (stringNumber < 1 || stringNumber > STRINGS_AMOUNT || keyNumber < 0 || keyNumber > STRING_KEYS) {
            log.info("incorrect input string number {} or key number {}. return raw guitar", stringNumber, keyNumber);
            return;
        }

        final GuitarString[] strings = guitar.getStrings();
        stringFiller.fillIntervalStrings(strings, stringNumber, keyNumber, interval);
    }

    private void fillPowerChordGuitar(Note tonic, Guitar guitar) {
        final Note[] notes = noteFiller.getPowerChordNotes(tonic);
        Arrays.stream(guitar.getStrings())
                .forEach(string -> keyFiller.fillKeysInChord(string.getKeys(), notes));
    }

    private void validateStringsAmount(String[] openNotes) {
        if (isNull(openNotes) || openNotes.length != STRINGS_AMOUNT) {
            log.error("open notes length is not guitar strings amount {}", STRINGS_AMOUNT);
            throw new IllegalArgumentException(
                    format("open notes length is not guitar strings amount %d", STRINGS_AMOUNT));
        }
    }
}
