package com.music.backend.service;

import com.music.backend.model.entity.Note;
import org.springframework.stereotype.Service;

import static com.music.backend.model.Notes.*;

@Service
public class GuitarService {

    private static final Note START_FIRST_STRING = E;
    private static final Note START_SECOND_STRING = B;
    private static final Note START_THIRD_STRING = G;
    private static final Note START_FOURTH_STRING = D;
    private static final Note START_FIFTH_STRING = A;
    private static final Note START_SIXTH_STRING = E;
}
