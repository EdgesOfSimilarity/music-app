package com.music.backend.rest.instrument;

import com.music.backend.model.entity.Note;
import com.music.backend.model.instruments.PianoMidi;
import com.music.backend.service.PianoMidiService;
import com.music.backend.util.Tone;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pianoMidi")
@Api(tags = {"Controller for nektar piano midi"})
public class PianoMidiController {

    private final PianoMidiService service;

    public PianoMidiController(@Autowired PianoMidiService service) {
        this.service = service;
    }

    @GetMapping("/raw")
    public PianoMidi getRaw() {
        return service.getRawPianoMidi();
    }

    @GetMapping("/tone")
    public PianoMidi getTone(@RequestParam String note, @RequestParam String tone) {
        return service.getTonePianoMidi(new Note(note), Tone.valueOf(tone.toUpperCase()));
    }

    @GetMapping("/chord")
    public PianoMidi getChord(@RequestParam String note, @RequestParam String tone) {
        return service.getChordPianoMidi(new Note(note), Tone.valueOf(tone.toUpperCase()));
    }

    @GetMapping("/interval")
    public PianoMidi getInterval(@RequestParam int startKey,
                                 @RequestParam int... interval) {
        return service.getIntervalPianoMidi(startKey, interval);
    }

    @GetMapping("/powerChord")
    public PianoMidi getPowerChord(@RequestParam String note) {
        return service.getPowerChordPianoMidi(new Note(note));
    }
}
