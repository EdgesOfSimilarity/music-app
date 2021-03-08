package com.music.backend.util;

import com.music.backend.model.entity.Note;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.music.backend.model.Interval.*;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

@Component
public class NoteFiller {

    private static final int NOTES_AMOUNT = 12;
    private static final int TONE_NOTES_AMOUNT = 7;
    private static final int CHORD_NOTES_AMOUNT = 3;

    @Value("${note.sequence}")
    private String[] noteSequence;

    @Value("${tone.major.pattern}")
    private int[] majorPattern;
    @Value("${tone.minor.pattern}")
    private int[] minorPattern;

    public Note[] getNoteSequence(int length, Note startNote) {
        final Note[] notes = new Note[length];

        for (int i = 0, n = getNoteIndex(startNote); i < length; i++, n++) {
            notes[i] = new Note(noteSequence[n % noteSequence.length]);
        }

        return notes;
    }

    public Note[] getNoteToneSequence(Note toneNote, Tone tone) {
        final Note[] noteSequence = getNoteSequence(NOTES_AMOUNT, toneNote);
        final Note[] toneNotes = new Note[TONE_NOTES_AMOUNT];

        for (int i = 0; i < toneNotes.length; i++) {
            toneNotes[i] = noteSequence[tone == Tone.MAJOR ? majorPattern[i] : minorPattern[i]];
        }

        return toneNotes;
    }

    public Note[] getChordNotes(Note tonic, Tone tone) {
        final Note[] chordNotes = new Note[CHORD_NOTES_AMOUNT];

        chordNotes[0] = tonic;
        chordNotes[1] = tone == Tone.MAJOR ? getIntervalNote(tonic, BIG_THIRD) : getIntervalNote(tonic, SMALL_THIRD);
        chordNotes[2] = getIntervalNote(tonic, QUINT);

        return chordNotes;
    }

    public Note getIntervalNote(Note note, int interval) {
        final Note[] noteSequence = getNoteSequence(NOTES_AMOUNT, note);
        return noteSequence[interval > 0 ? interval % NOTES_AMOUNT :
                (NOTES_AMOUNT + (interval % NOTES_AMOUNT)) % NOTES_AMOUNT];
    }

    private int getNoteIndex(Note note) {
        for (int i = 0; i < noteSequence.length; i++) {
            if (equalsIgnoreCase(note.getName(), noteSequence[i])) {
                return i;
            }
        }

        throw new IllegalArgumentException("incorrect note " + note.getName());
    }
}
