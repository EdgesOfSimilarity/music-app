package com.music.backend.rest.instrument;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meta")
public class MetaInfoController {

    @Value("${instrument.names}")
    private String[] instrumentNames;

    @GetMapping("/instrumentNames")
    public String[] getInstrumentNames() {
        return instrumentNames;
    }
}
