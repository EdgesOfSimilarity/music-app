package com.music.backend.util;

import com.music.backend.model.entity.Key;
import com.music.backend.model.entity.Note;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
@Slf4j
public class KeyFiller {

    public void fillMidiKeys(Key[] keys, Note[] noteSequence) {
        for (int i = 0; i < noteSequence.length; i++) {
            fillOneMidiKey(keys, noteSequence[i], i);
        }
    }

    public void fillStringKeys(Key[] keys, Note[] noteSequence, int stringNumber) {
        for (int i = 0; i < noteSequence.length; i++) {
            fillOneStringKey(keys, noteSequence[i], stringNumber, i);
        }
    }

    public void fillKeysInToneSequence(Key[] keys, Note[] toneSequence) {
        final Set<Note> sequenceNotes = Arrays.stream(toneSequence)
                .collect(Collectors.toSet());
        Arrays.stream(keys)
                .filter(key -> sequenceNotes.contains(key.getNote()))
                .forEach(key -> key.setInTone(true));
    }

    public void fillKeysInChord(Key[] keys, Note[] chordSequence) {
        final Set<Note> chordNotes = Arrays.stream(chordSequence)
                .collect(Collectors.toSet());
        Arrays.stream(keys)
                .filter(key -> chordNotes.contains(key.getNote()))
                .forEach(key -> key.setInChord(true));
    }

    public void fillKeyIntervals(Key[] keys, int startKeyNumber, int... intervals) {
        if (startKeyNumber <= 0 || startKeyNumber > keys.length) {
            log.info("incorrect key number. not fill");
            return;
        }

        Optional<Key> currentKey = getKey(keys, startKeyNumber);
        currentKey.ifPresent(key -> key.setInInterval(true));

        if (isNull(intervals) || intervals.length == 0) {
            log.info("empty intervals");
            return;
        }

        currentKey.ifPresent(key -> fillKeyIntervalsWithoutStart(keys, key, intervals));
    }

    private Optional<Key> getKey(Key[] keys, int number) {
        if (number > keys.length || number < 0) {
            return Optional.empty();
        }

        return Arrays.stream(keys)
                .filter(key -> key.getNumber() == number)
                .findFirst();
    }

    public void fillStringKeyIntervals(Key[] keys, Key currentKey, Note intervalNote) {
        Arrays.stream(keys)
                .filter(key -> key.getNote().equals(intervalNote))
                .peek(key -> key.setPrevIntervalKey(currentKey))
                .forEach(key -> key.setInInterval(true));
    }

    private void fillOneMidiKey(Key[] keys, Note note, int i) {
        final Key key = Optional.ofNullable(keys[i]).orElse(new Key());
        key.setNumber(i + 1);
        key.setNote(note);
        keys[i] = key;
    }

    private void fillOneStringKey(Key[] keys, Note note, int stringNumber, int i) {
        final Key key = Optional.ofNullable(keys[i]).orElse(new Key());
        key.setNumber(i);
        key.setNote(note);
        key.setStringNumber(stringNumber);
        keys[i] = key;
    }

    private void fillKeyIntervalsWithoutStart(Key[] keys, Key currentKey, int[] intervals) {
        for (int interval : intervals) {
            if (interval == 0) {
                continue;
            }

            final Optional<Key> nextKeyOptional = getKey(keys, currentKey.getNumber() + interval);
            if (!nextKeyOptional.isPresent()) {
                break;
            }

            final Key nextKey = nextKeyOptional.get();

            nextKey.setInInterval(true);
            nextKey.setPrevIntervalKey(currentKey);

            currentKey = nextKey;
        }
    }
}
