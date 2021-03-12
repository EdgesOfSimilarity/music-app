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
@RequestMapping("/text/custom/guitar")
public class TextCustomGuitarController {

    private final GuitarService service;
    private final InstrumentPrinter printer;

    public TextCustomGuitarController(@Autowired GuitarService service, @Autowired InstrumentPrinter printer) {
        this.service = service;
        this.printer = printer;
    }

    @GetMapping("/raw")
    public String getCustomRaw(@RequestParam String[] openNotes) {
        return printer.printGuitar(service.getCustomRawGuitar(openNotes));
    }

    @GetMapping("/tone")
    public String getTone(@RequestParam String note, @RequestParam String tone, @RequestParam String[] openNotes) {
        return printer.printGuitar(service.getCustomToneGuitar(
                new Note(note), Tone.valueOf(tone.toUpperCase()), openNotes));
    }

    @GetMapping("/chord")
    public String getChord(@RequestParam String note, @RequestParam String tone, @RequestParam String[] openNotes) {
        return printer.printGuitar(service.getCustomChordGuitar(
                new Note(note), Tone.valueOf(tone.toUpperCase()), openNotes));
    }

    @GetMapping("/interval")
    public String getInterval(@RequestParam int string,
                              @RequestParam int startKey,
                              @RequestParam int interval,
                              @RequestParam String[] openNotes) {
        return printer.printGuitar(service.getCustomIntervalGuitar(string, startKey, interval, openNotes));
    }
}
