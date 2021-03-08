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

    public Ukulele getToneUkulele(Note toneNote, Tone tone) {
        final Ukulele ukulele = getRawUkulele();
        final Note[] notes = noteFiller.getNoteToneSequence(toneNote, tone);

        Arrays.stream(ukulele.getStrings())
                .forEach(string -> keyFiller.fillKeysInToneSequence(string.getKeys(), notes));

        return ukulele;
    }

    public Ukulele getChordUkulele(Note tonic, Tone tone) {
        final Ukulele ukulele = getRawUkulele();
        final Note[] notes = noteFiller.getChordNotes(tonic, tone);

        Arrays.stream(ukulele.getStrings())
                .forEach(string -> keyFiller.fillKeysInChord(string.getKeys(), notes));

        return ukulele;
    }

    public Ukulele getIntervalUkulele(int stringNumber, int keyNumber, int interval) {
        final Ukulele ukulele = getRawUkulele();
        if (stringNumber < 1 || stringNumber > STRINGS_AMOUNT || keyNumber < 0 || keyNumber > STRING_KEYS) {
            log.info("incorrect string number {} or key number {}. return raw ukulele", stringNumber, keyNumber);
            return ukulele;
        }

        final GuitarString[] strings = ukulele.getStrings();
        stringFiller.fillIntervalStrings(strings, stringNumber, keyNumber, interval);

        return ukulele;
    }
}
