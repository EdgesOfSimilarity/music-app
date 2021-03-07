package com.music.backend.service;

import com.music.backend.model.entity.Note;
import org.springframework.stereotype.Service;

import static com.music.backend.model.Notes.*;

@Service
public class UkuleleService {

    private static final Note START_FIRST_NOTE = A;
    private static final Note START_SECOND_NOTE = E;
    private static final Note START_THIRD_NOTE = C;
    private static final Note START_FOURTH_NOTE = G;
}
