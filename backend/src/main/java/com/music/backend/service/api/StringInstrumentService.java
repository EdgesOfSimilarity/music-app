package com.music.backend.service.api;

import com.music.backend.model.entity.Note;
import com.music.backend.model.entity.String;
import com.music.backend.model.instruments.StringInstrument;
import com.music.backend.util.KeyFiller;
import com.music.backend.util.NoteFiller;
import com.music.backend.util.StringFiller;
import com.music.backend.util.Tone;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
public abstract class StringInstrumentService implements InstrumentService {

    protected int stringsAmount;
    protected int keysAmount;
    protected java.lang.String[] openNotes;

    protected NoteFiller noteFiller;
    protected KeyFiller keyFiller;
    protected StringFiller stringFiller;

    @Getter
    protected java.lang.String instrument;

    public StringInstrumentService(int stringsAmount, int keysAmount, java.lang.String[] openNotes,
                                   NoteFiller noteFiller, KeyFiller keyFiller, StringFiller stringFiller,
                                   java.lang.String instrument) {
        this.stringsAmount = stringsAmount;
        this.keysAmount = keysAmount;
        this.openNotes = openNotes;
        this.noteFiller = noteFiller;
        this.keyFiller = keyFiller;
        this.stringFiller = stringFiller;
        this.instrument = instrument;
    }

    public StringInstrument getRawStringInstrument(java.lang.String[] openNotes) {
        validateStringsAmount(openNotes);

        final StringInstrument guitar = new StringInstrument();
        guitar.setStrings(
                stringFiller.getRawStrings(stringsAmount, keysAmount, isNull(openNotes) ? this.openNotes : openNotes));

        return guitar;
    }

    public StringInstrument getToneStringInstrument(Note toneNote, Tone tone, java.lang.String[] openNotes) {
        final StringInstrument guitar = getRawStringInstrument(openNotes);
        fillToneStringInstrument(toneNote, tone, guitar);

        return guitar;
    }

    public StringInstrument getChordStringInstrument(Note tonic, Tone tone, java.lang.String[] openNotes) {
        final StringInstrument guitar = getRawStringInstrument(openNotes);
        fillChordStringInstrument(tonic, tone, guitar);

        return guitar;
    }

    public StringInstrument getIntervalStringInstrument(int stringNumber, int keyNumber, int interval,
                                                        java.lang.String[] openNotes) {
        final StringInstrument guitar = getRawStringInstrument(openNotes);
        fillIntervalStringInstrument(stringNumber, keyNumber, interval, guitar);

        return guitar;
    }

    public StringInstrument getPowerChordStringInstrument(Note tonic, java.lang.String[] openNotes) {
        final StringInstrument guitar = getRawStringInstrument(openNotes);
        fillPowerChordStringInstrument(tonic, guitar);

        return guitar;
    }

    private void fillToneStringInstrument(Note toneNote, Tone tone, StringInstrument guitar) {
        final Note[] toneSequence = noteFiller.getNoteToneSequence(toneNote, tone);
        Arrays.stream(guitar.getStrings())
                .forEach(string -> keyFiller.fillKeysInToneSequence(string.getKeys(), toneSequence));
    }

    private void fillChordStringInstrument(Note tonic, Tone tone, StringInstrument guitar) {
        final Note[] chordNotes = noteFiller.getChordNotes(tonic, tone);
        Arrays.stream(guitar.getStrings())
                .forEach(string -> keyFiller.fillKeysInChord(string.getKeys(), chordNotes));
    }

    private void fillIntervalStringInstrument(int stringNumber, int keyNumber, int interval, StringInstrument guitar) {
        if (stringNumber < 1 || stringNumber > stringsAmount || keyNumber < 0 || keyNumber > keysAmount) {
            log.info("incorrect input string number {} or key number {}. return raw", stringNumber, keyNumber);
            return;
        }

        final String[] strings = guitar.getStrings();
        stringFiller.fillIntervalStrings(strings, stringNumber, keyNumber, interval);
    }

    private void fillPowerChordStringInstrument(Note tonic, StringInstrument guitar) {
        final Note[] notes = noteFiller.getPowerChordNotes(tonic);
        Arrays.stream(guitar.getStrings())
                .forEach(string -> keyFiller.fillKeysInChord(string.getKeys(), notes));
    }

    private void validateStringsAmount(java.lang.String[] openNotes) {
        if (nonNull(openNotes) && openNotes.length != stringsAmount) {
            log.error("open notes length is not equal instrument strings amount {}", stringsAmount);
            throw new IllegalArgumentException(
                    format("open notes length is not equal instrument strings amount %d", stringsAmount));
        }
    }
}
