package com.music.backend.service;

import com.music.backend.model.entity.Note;
import com.music.backend.model.instruments.DrumMidi;
import org.springframework.stereotype.Service;

import static com.music.backend.model.Notes.*;

@Service
public class DrumMidiService {

    private static final Note START_A_NOTE = C;
    private static final Note START_B_NOTE = E;
    private static final Note START_C_NOTE = Gd;

    public DrumMidi getRaw() {
        final DrumMidi drumMidi = new DrumMidi();

        throw new UnsupportedOperationException("not impl");
    }
}
