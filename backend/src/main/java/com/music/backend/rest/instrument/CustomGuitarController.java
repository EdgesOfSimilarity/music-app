package com.music.backend.rest.instrument;

import com.music.backend.model.entity.Note;
import com.music.backend.model.instruments.Guitar;
import com.music.backend.service.GuitarService;
import com.music.backend.util.Tone;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/custom/guitar")
@Api(tags = {"Controller for guitar with custom strings"})
public class CustomGuitarController {

    private final GuitarService service;

    public CustomGuitarController(@Autowired GuitarService service) {
        this.service = service;
    }

    @GetMapping("/raw")
    public Guitar getRaw(@RequestParam String[] openNotes) {
        return service.getCustomRawGuitar(openNotes);
    }

    @GetMapping("/tone")
    public Guitar getTone(@RequestParam String note, @RequestParam String tone, @RequestParam String[] openNotes) {
        return service.getCustomToneGuitar(new Note(note), Tone.valueOf(tone.toUpperCase()), openNotes);
    }

    @GetMapping("/chord")
    public Guitar getChord(@RequestParam String note, @RequestParam String tone, @RequestParam String[] openNotes) {
        return service.getCustomChordGuitar(new Note(note), Tone.valueOf(tone.toUpperCase()), openNotes);
    }

    @GetMapping("/interval")
    public Guitar getInterval(@RequestParam int string,
                              @RequestParam int startKey,
                              @RequestParam int interval,
                              @RequestParam String[] openNotes) {
        return service.getCustomIntervalGuitar(string, startKey, interval, openNotes);
    }
}
