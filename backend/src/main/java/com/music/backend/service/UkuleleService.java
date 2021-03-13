package com.music.backend.service;

import com.music.backend.service.api.StringInstrumentService;
import com.music.backend.util.KeyFiller;
import com.music.backend.util.NoteFiller;
import com.music.backend.util.StringFiller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.music.backend.model.InstrumentName.UKULELE;

@Service("ukuleleService")
@Slf4j
public class UkuleleService extends StringInstrumentService {

    public UkuleleService(@Value("${ukulele.strings.amount}") int stringsAmount,
                          @Value("${ukulele.keys.amount}") int keysAmount,
                          @Value("${ukulele.open.notes}") java.lang.String[] openNotes,
                          @Autowired KeyFiller keyFiller,
                          @Autowired NoteFiller noteFiller,
                          @Autowired StringFiller stringFiller,
                          @Value(UKULELE) String instrument) {
        super(stringsAmount, keysAmount, openNotes, noteFiller, keyFiller, stringFiller, instrument);
    }
}
