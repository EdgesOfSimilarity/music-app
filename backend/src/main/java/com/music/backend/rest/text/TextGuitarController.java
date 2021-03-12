package com.music.backend.rest.text;

import com.music.backend.model.entity.Note;
import com.music.backend.service.GuitarService;
import com.music.backend.util.InstrumentPrinter;
import com.music.backend.util.Tone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/text/guitar")
public class TextGuitarController {

    private final GuitarService service;
    private final InstrumentPrinter printer;

    public TextGuitarController(@Autowired GuitarService guitarService, @Autowired InstrumentPrinter printer) {
        this.service = guitarService;
        this.printer = printer;
    }

    @GetMapping("/raw")
    public String getRaw() {
        return printer.printGuitar(service.getRawGuitar());
    }

    @GetMapping("/tone")
    public String getTone(@RequestParam String note, @RequestParam String tone) {
        return printer.printGuitar(service.getToneGuitar(new Note(note), Tone.valueOf(tone.toUpperCase())));
    }

    @GetMapping("/chord")
    public String getChord(@RequestParam String note, @RequestParam String tone) {
        return printer.printGuitar(service.getChordGuitar(new Note(note), Tone.valueOf(tone.toUpperCase())));
    }

    @GetMapping("/interval")
    public String getInterval(@RequestParam int string,
                              @RequestParam int startKey,
                              @RequestParam int interval) {
        return printer.printGuitar(service.getIntervalGuitar(string, startKey, interval));
    }

    @GetMapping("/powerChord")
    public String getPowerChord(@RequestParam String note) {
        return printer.printGuitar(service.getPowerChordGuitar(new Note(note)));
    }
}
