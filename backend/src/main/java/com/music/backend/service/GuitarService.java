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

    public Guitar getToneGuitar(Note toneNote, Tone tone) {
        final Guitar guitar = getRawGuitar();
        final Note[] toneSequence = noteFiller.getNoteToneSequence(toneNote, tone);

        Arrays.stream(guitar.getStrings())
                .forEach(string -> keyFiller.fillKeysInToneSequence(string.getKeys(), toneSequence));

        return guitar;
    }

    public Guitar getChordGuitar(Note tonic, Tone tone) {
        final Guitar guitar = getRawGuitar();
        final Note[] chordNotes = noteFiller.getChordNotes(tonic, tone);

        Arrays.stream(guitar.getStrings())
                .forEach(string -> keyFiller.fillKeysInChord(string.getKeys(), chordNotes));

        return guitar;
    }

    public Guitar getIntervalGuitar(int stringNumber, int keyNumber, int interval) {
        final Guitar guitar = getRawGuitar();
        if (stringNumber < 1 || stringNumber > STRINGS_AMOUNT || keyNumber < 0 || keyNumber > STRING_KEYS) {
            log.info("incorrect input string number {} or key number {}. return raw guitar", stringNumber, keyNumber);
            return guitar;
        }

        final GuitarString[] strings = guitar.getStrings();
        stringFiller.fillIntervalStrings(strings, stringNumber, keyNumber, interval);

        return guitar;
    }
}
