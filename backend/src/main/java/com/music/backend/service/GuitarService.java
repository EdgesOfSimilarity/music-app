package com.music.backend.service;

import com.music.backend.service.api.StringInstrumentService;
import com.music.backend.util.KeyFiller;
import com.music.backend.util.NoteFiller;
import com.music.backend.util.StringFiller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.music.backend.model.InstrumentName.GUITAR;

@Service("guitarService")
@Slf4j
public class GuitarService extends StringInstrumentService {

    public GuitarService(@Value("${guitar.strings.amount}") int stringsAmount,
                         @Value("${guitar.keys.amount}") int keysAmount,
                         @Value("${guitar.open.notes}") String[] openNotes,
                         @Autowired KeyFiller keyFiller,
                         @Autowired NoteFiller noteFiller,
                         @Autowired StringFiller stringFiller,
                         @Value(GUITAR) String instrument) {
        super(stringsAmount, keysAmount, openNotes, noteFiller, keyFiller, stringFiller, instrument);
    }
}
