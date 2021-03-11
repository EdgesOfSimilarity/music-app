package com.music.backend.rest.text;

import com.music.backend.model.entity.Note;
import com.music.backend.service.DrumMidiService;
import com.music.backend.util.InstrumentPrinter;
import com.music.backend.util.Tone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/text/drumMidi")
public class TextDrumMidiController {

    private final DrumMidiService service;
    private final InstrumentPrinter printer;

    public TextDrumMidiController(@Autowired DrumMidiService service, @Autowired InstrumentPrinter printer) {
        this.service = service;
        this.printer = printer;
    }

    @GetMapping("/raw")
    public String getRaw() {
        return printer.printDrumMidi(service.getRawDrumMidi());
    }

    @GetMapping("/tone")
    public String getTone(@RequestParam String note, @RequestParam String tone) {
        return printer.printDrumMidi(service.getToneDrumMidi(new Note(note), Tone.valueOf(tone.toUpperCase())));
    }

    @GetMapping("/chord")
    public String getChord(@RequestParam String note, @RequestParam String tone) {
        return printer.printDrumMidi(service.getChordDrumMidi(new Note(note), Tone.valueOf(tone.toUpperCase())));
    }

    @GetMapping("/interval")
    public String getInterval(@RequestParam int startKey,
                              @RequestParam int... interval) {
        return printer.printDrumMidi(service.getIntervalDrumMidi(startKey, interval));
    }
}
