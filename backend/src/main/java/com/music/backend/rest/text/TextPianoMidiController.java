package com.music.backend.rest.text;

import com.music.backend.model.entity.Note;
import com.music.backend.service.PianoMidiService;
import com.music.backend.util.InstrumentPrinter;
import com.music.backend.util.Tone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/text/pianoMidi")
public class TextPianoMidiController {

    private final PianoMidiService service;
    private final InstrumentPrinter printer;

    public TextPianoMidiController(@Autowired PianoMidiService service, @Autowired InstrumentPrinter printer) {
        this.service = service;
        this.printer = printer;
    }

    @GetMapping("/raw")
    public String getRaw() {
        return printer.printPianoMidi(service.getRawPianoMidi());
    }

    @GetMapping("/tone")
    public String getTone(@RequestParam String note, @RequestParam String tone) {
        return printer.printPianoMidi(service.getTonePianoMidi(new Note(note), Tone.valueOf(tone.toUpperCase())));
    }

    @GetMapping("/chord")
    public String getChord(@RequestParam String note, @RequestParam String tone) {
        return printer.printPianoMidi(service.getChordPianoMidi(new Note(note), Tone.valueOf(tone.toUpperCase())));
    }

    @GetMapping("/interval")
    public String getInterval(@RequestParam int startKey,
                              @RequestParam int... interval) {
        return printer.printPianoMidi(service.getIntervalPianoMidi(startKey, interval));
    }
}
