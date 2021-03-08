package com.music.backend.model.entity;

import lombok.Data;

@Data
public class Key {

    private int number;
    private Integer stringNumber;

    private Note note;
    private Key prevIntervalKey;

    private boolean isInTone;
    private boolean isInChord;
    private boolean isInInterval;
}
