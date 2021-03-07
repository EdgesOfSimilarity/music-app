package com.music.backend.model.entity;

import lombok.Data;

@Data
public class Note {

    private String name;

    public Note() {
    }

    public Note(String name) {
        this.name = name;
    }
}
