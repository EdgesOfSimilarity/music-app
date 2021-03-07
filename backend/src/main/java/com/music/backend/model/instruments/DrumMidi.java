package com.music.backend.model.instruments;

import com.music.backend.model.entity.MidiBank;
import lombok.Data;

@Data
public class DrumMidi {

    private MidiBank[] banks;
}
