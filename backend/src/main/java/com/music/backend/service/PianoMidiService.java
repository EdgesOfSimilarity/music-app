package com.music.backend.service;

import com.music.backend.model.entity.Key;
import com.music.backend.model.entity.Note;
import com.music.backend.model.instruments.PianoMidi;
import com.music.backend.util.KeyFiller;
import com.music.backend.util.NoteFiller;
import com.music.backend.util.Tone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.music.backend.model.Notes.C;
import static java.util.Objects.isNull;

@Service
public class PianoMidiService {

    private static final int DEFAULT_LENGTH = 61;
    private static final Note START_NOTE = C;

    private final NoteFiller noteFiller;
    private final KeyFiller keyFiller;

    public PianoMidiService(@Autowired NoteFiller noteFiller,
                            @Autowired KeyFiller keyFiller) {
        this.noteFiller = noteFiller;
        this.keyFiller = keyFiller;
    }

    public PianoMidi getRawPianoMidi() {
        final PianoMidi pianoMidi = new PianoMidi();
        final Key[] keys = new Key[DEFAULT_LENGTH];
        final Note[] noteSequence = noteFiller.getNoteSequence(keys.length, START_NOTE);

        keyFiller.fillKeys(keys, noteSequence);

        pianoMidi.setKeys(keys);
        return pianoMidi;
    }

    public PianoMidi getTonePianoMidi(Note toneNote, Tone tone) {
        final PianoMidi pianoMidi = getRawPianoMidi();
        final Note[] toneNotes = noteFiller.getNoteToneSequence(toneNote, tone);

        keyFiller.fillKeysInToneSequence(pianoMidi.getKeys(), toneNotes);

        return pianoMidi;
    }

    public PianoMidi getChordPianoMidi(Note tonic, Tone tone) {
        final PianoMidi pianoMidi = getRawPianoMidi();
        final Note[] chordNotes = noteFiller.getChordNotes(tonic, tone);

        keyFiller.fillKeysInChord(pianoMidi.getKeys(), chordNotes);

        return pianoMidi;
    }

    public PianoMidi getIntervalPianoMidi(Key key, int... intervals) {
        final PianoMidi pianoMidi = getRawPianoMidi();
        final int startKeyNumber = key.getNumber();
        final Key[] pianoKeys = pianoMidi.getKeys();

        if (startKeyNumber <= 0 || startKeyNumber > pianoKeys.length) {
            return pianoMidi;
        }

        Key currentKey = keyFiller.getKey(pianoKeys, startKeyNumber);
        currentKey.setInInterval(true);

        if (isNull(intervals) || intervals.length == 0) {
            return pianoMidi;
        }

        keyFiller.fillKeyIntervals(pianoKeys, currentKey, intervals);

        return pianoMidi;
    }
}
