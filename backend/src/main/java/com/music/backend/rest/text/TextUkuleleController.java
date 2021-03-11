package com.music.backend.rest.text;

import com.music.backend.model.entity.Note;
import com.music.backend.service.UkuleleService;
import com.music.backend.util.InstrumentPrinter;
import com.music.backend.util.Tone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/text/ukulele")
public class TextUkuleleController {

    private final UkuleleService service;
    private final InstrumentPrinter printer;

    public TextUkuleleController(@Autowired UkuleleService service, @Autowired InstrumentPrinter printer) {
        this.service = service;
        this.printer = printer;
    }

    @GetMapping("/raw")
    public String getRaw() {
        return printer.printUkulele(service.getRawUkulele());
    }

    @GetMapping("/tone")
    public String getTone(@RequestParam String note, @RequestParam String tone) {
        return printer.printUkulele(service.getToneUkulele(new Note(note), Tone.valueOf(tone.toUpperCase())));
    }

    @GetMapping("/chord")
    public String getChord(@RequestParam String note, @RequestParam String tone) {
        return printer.printUkulele(service.getChordUkulele(new Note(note), Tone.valueOf(tone.toUpperCase())));
    }

    @GetMapping("/interval")
    public String getInterval(@RequestParam int string,
                              @RequestParam int startKey,
                              @RequestParam int interval) {
        return printer.printUkulele(service.getIntervalUkulele(string, startKey, interval));
    }
}
