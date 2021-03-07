package com.music.backend.model.entity;

import lombok.Data;

@Data
public class MidiBank {

    private char name;
    private Key[][] keys;
}
