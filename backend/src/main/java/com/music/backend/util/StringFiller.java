package com.music.backend.util;

import com.music.backend.model.entity.GuitarString;
import com.music.backend.model.entity.Key;
import com.music.backend.model.entity.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class StringFiller {

    private final NoteFiller noteFiller;
    private final KeyFiller keyFiller;

    public StringFiller(@Autowired KeyFiller keyFiller,
                        @Autowired NoteFiller noteFiller) {
        this.keyFiller = keyFiller;
        this.noteFiller = noteFiller;
    }

    public GuitarString[] getRawStrings(int stringAmount, int stringKeys, String[] openNotes) {
        final GuitarString[] strings = new GuitarString[stringAmount];

        for (int i = 0; i < openNotes.length; i++) {
            strings[i] = new GuitarString();
            fillString(strings[i], new Note(openNotes[i]), i + 1, stringKeys);
        }

        return strings;
    }

    public void fillIntervalStrings(GuitarString[] strings, int stringNumber, int keyNumber, int interval) {
        final GuitarString targetString = strings[stringNumber - 1];
        final Key targetKey = targetString.getKeys()[keyNumber];
        final Note intervalNote = noteFiller.getIntervalNote(targetKey.getNote(), interval);

        targetKey.setInInterval(true);
        Arrays.stream(strings)
                .forEach(string -> keyFiller.fillStringKeyIntervals(string.getKeys(), targetKey, intervalNote));
    }

    private void fillString(GuitarString string, Note openNote, int stringNumber, int stringKeys) {
        string.setKeys(new Key[stringKeys]);
        string.setNumber(stringNumber);
        keyFiller.fillStringKeys(string.getKeys(), noteFiller.getNoteSequence(stringKeys, openNote), stringNumber);
    }
}
