package com.music.backend.rest.instrument;

import com.music.backend.service.api.InstrumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/meta")
public class MetaInfoController {

    private final String[] instrumentNames;

    public MetaInfoController(@Autowired List<InstrumentService> services) {
        this.instrumentNames = services.stream()
                .map(InstrumentService::getInstrument)
                .toArray(String[]::new);
    }

    @GetMapping("/instrumentNames")
    public String[] getInstrumentNames() {
        return instrumentNames;
    }
}
