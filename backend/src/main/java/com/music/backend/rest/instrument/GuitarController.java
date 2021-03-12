package com.music.backend.rest.instrument;

import com.music.backend.model.entity.Note;
import com.music.backend.model.instruments.Guitar;
import com.music.backend.service.GuitarService;
import com.music.backend.util.Tone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/guitar")
public class GuitarController {

    private final GuitarService service;

    public GuitarController(@Autowired GuitarService service) {
        this.service = service;
    }

    @GetMapping("/raw")
    public Guitar getRaw() {
        return service.getRawGuitar();
    }

    @GetMapping("/tone")
    public Guitar getTone(@RequestParam String note, @RequestParam String tone) {
        return service.getToneGuitar(new Note(note), Tone.valueOf(tone.toUpperCase()));
    }

    @GetMapping("/chord")
    public Guitar getChord(@RequestParam String note, @RequestParam String tone) {
        return service.getChordGuitar(new Note(note), Tone.valueOf(tone.toUpperCase()));
    }

    @GetMapping("/interval")
    public Guitar getInterval(@RequestParam int string,
                              @RequestParam int startKey,
                              @RequestParam int interval) {
        return service.getIntervalGuitar(string, startKey, interval);
    }
}
