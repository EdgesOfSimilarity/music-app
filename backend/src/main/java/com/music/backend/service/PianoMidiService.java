package com.music.backend.service;

import com.music.backend.model.entity.Key;
import com.music.backend.model.entity.Note;
import com.music.backend.model.instruments.PianoMidi;
import com.music.backend.service.api.InstrumentService;
import com.music.backend.util.KeyFiller;
import com.music.backend.util.NoteFiller;
import com.music.backend.util.Tone;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.music.backend.model.InstrumentName.PIANO_MIDI;

@Service
public class PianoMidiService implements InstrumentService {

    private static final int DEFAULT_LENGTH = 61;

    private final NoteFiller noteFiller;
    private final KeyFiller keyFiller;

    @Getter
    private final String instrument;

    @Value("${piano.start.note}")
    private String startNote;


    public PianoMidiService(@Autowired NoteFiller noteFiller,
                            @Autowired KeyFiller keyFiller,
                            @Value(PIANO_MIDI) String instrument) {
        this.noteFiller = noteFiller;
        this.keyFiller = keyFiller;
        this.instrument = instrument;
    }

    public PianoMidi getRawPianoMidi() {
        final PianoMidi pianoMidi = new PianoMidi();
        final Key[] keys = new Key[DEFAULT_LENGTH];
        final Note[] noteSequence = noteFiller.getNoteSequence(keys.length, new Note(startNote));

        keyFiller.fillMidiKeys(keys, noteSequence);

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

    public PianoMidi getIntervalPianoMidi(int startKeyNumber, int... intervals) {
        final PianoMidi pianoMidi = getRawPianoMidi();
        final Key[] pianoKeys = pianoMidi.getKeys();

        keyFiller.fillKeyIntervals(pianoKeys, startKeyNumber, intervals);

        return pianoMidi;
    }

    public PianoMidi getPowerChordPianoMidi(Note tonic) {
        final PianoMidi pianoMidi = getRawPianoMidi();
        final Note[] chordNotes = noteFiller.getPowerChordNotes(tonic);

        keyFiller.fillKeysInChord(pianoMidi.getKeys(), chordNotes);

        return pianoMidi;
    }
}
