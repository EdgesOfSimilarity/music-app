package com.music.backend.rest.instrument;

import com.music.backend.model.entity.Note;
import com.music.backend.model.instruments.Ukulele;
import com.music.backend.service.UkuleleService;
import com.music.backend.util.Tone;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/custom/ukulele")
@Api(tags = {"Controller for ukulele with custom strings"})
public class CustomUkuleleController {

    private final UkuleleService service;

    public CustomUkuleleController(@Autowired UkuleleService service) {
        this.service = service;
    }

    @GetMapping("/raw")
    public Ukulele getRaw(@RequestParam String[] openNotes) {
        return service.getCustomRawUkulele(openNotes);
    }

    @GetMapping("/tone")
    public Ukulele getTone(@RequestParam String note, @RequestParam String tone, @RequestParam String[] openNotes) {
        return service.getCustomToneUkulele(new Note(note), Tone.valueOf(tone.toUpperCase()), openNotes);
    }

    @GetMapping("/chord")
    public Ukulele getChord(@RequestParam String note, @RequestParam String tone, @RequestParam String[] openNotes) {
        return service.getCustomChordUkulele(new Note(note), Tone.valueOf(tone.toUpperCase()), openNotes);
    }

    @GetMapping("/interval")
    public Ukulele getInterval(@RequestParam int string,
                               @RequestParam int startKey,
                               @RequestParam int interval,
                               @RequestParam String[] openNotes) {
        return service.getCustomIntervalUkulele(string, startKey, interval, openNotes);
    }

    @GetMapping("/powerChord")
    public Ukulele getPowerChord(@RequestParam String note, @RequestParam String[] openNotes) {
        return service.getCustomPowerChordUkulele(new Note(note), openNotes);
    }
}
