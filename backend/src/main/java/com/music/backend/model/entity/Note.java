package com.music.backend.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Note {

    private String name;

    public Note() {
    }

    public Note(String name) {
        this.name = name.replace('d', '#');
    }
}
