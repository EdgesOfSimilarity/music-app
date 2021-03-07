package com.music.backend.model.instruments;

import com.music.backend.model.entity.GuitarString;
import lombok.Data;

@Data
public class Guitar {

    private GuitarString[] strings;
}
