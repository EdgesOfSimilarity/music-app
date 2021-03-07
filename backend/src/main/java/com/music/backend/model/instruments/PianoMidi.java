package com.music.backend.model.instruments;

import com.music.backend.model.entity.Key;
import lombok.Data;

@Data
public class PianoMidi {

    private Key[] keys;
}
