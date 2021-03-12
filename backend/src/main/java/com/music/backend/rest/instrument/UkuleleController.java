package com.music.backend.rest.instrument;

import com.music.backend.model.entity.Note;
import com.music.backend.model.instruments.Ukulele;
import com.music.backend.service.UkuleleService;
import com.music.backend.util.Tone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ukulele")
public class UkuleleController {

    private final UkuleleService service;

    public UkuleleController(@Autowired UkuleleService service) {
        this.service = service;
    }

    @GetMapping("/raw")
    public Ukulele getRaw() {
        return service.getRawUkulele();
    }

    @GetMapping("/tone")
    public Ukulele getTone(@RequestParam String note, @RequestParam String tone) {
        return service.getToneUkulele(new Note(note), Tone.valueOf(tone.toUpperCase()));
    }

    @GetMapping("/chord")
    public Ukulele getChord(@RequestParam String note, @RequestParam String tone) {
        return service.getChordUkulele(new Note(note), Tone.valueOf(tone.toUpperCase()));
    }

    @GetMapping("/interval")
    public Ukulele getInterval(@RequestParam int string,
                               @RequestParam int startKey,
                               @RequestParam int interval) {
        return service.getIntervalUkulele(string, startKey, interval);
    }
}
