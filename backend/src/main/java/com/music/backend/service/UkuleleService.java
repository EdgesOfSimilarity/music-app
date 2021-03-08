package com.music.backend.service;

import com.music.backend.model.entity.GuitarString;
import com.music.backend.model.entity.Key;
import com.music.backend.model.entity.Note;
import com.music.backend.model.instruments.Ukulele;
import com.music.backend.util.KeyFiller;
import com.music.backend.util.NoteFiller;
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

    @Value("${ukulele.open.notes}")
    private String[] openNotes;

    public UkuleleService(@Autowired KeyFiller keyFiller, @Autowired NoteFiller noteFiller) {
        this.keyFiller = keyFiller;
        this.noteFiller = noteFiller;
    }

    public Ukulele getRawUkulele() {
        final Ukulele ukulele = new Ukulele();
        ukulele.setStrings(getRawStrings());

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
        final GuitarString targetString = strings[stringNumber - 1];
        final Key targetKey = targetString.getKeys()[keyNumber];
        final Note intervalNote = noteFiller.getIntervalNote(targetKey.getNote(), interval);

        targetKey.setInInterval(true);
        Arrays.stream(strings)
                .forEach(string -> keyFiller.fillStringKeyIntervals(string.getKeys(), targetKey, intervalNote));

        return ukulele;
    }

    private GuitarString[] getRawStrings() {
        final GuitarString[] strings = new GuitarString[STRINGS_AMOUNT];

        for (int i = 0; i < openNotes.length; i++) {
            strings[i] = new GuitarString();
            fillString(strings[i], new Note(openNotes[i]), i + 1);
        }

        return strings;
    }

    private void fillString(GuitarString string, Note openNote, int stringNumber) {
        string.setKeys(new Key[STRING_KEYS]);
        string.setNumber(stringNumber);
        keyFiller.fillStringKeys(string.getKeys(), noteFiller.getNoteSequence(STRING_KEYS, openNote));
    }
}
