package com.music.backend.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static org.apache.commons.lang3.StringUtils.capitalize;

@Data
@EqualsAndHashCode
public class Note {

    private java.lang.String name;

    public Note() {
    }

    public Note(java.lang.String name) {
        this.name = capitalize(name);
    }
}
