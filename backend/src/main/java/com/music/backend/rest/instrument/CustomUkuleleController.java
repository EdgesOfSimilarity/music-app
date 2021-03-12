package com.music.backend.rest.instrument;

import com.music.backend.model.entity.Note;
import com.music.backend.model.instruments.Ukulele;
import com.music.backend.service.UkuleleService;
import com.music.backend.util.Tone;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
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
    public Ukulele getRaw(@RequestParam
                          @ApiParam(value = "open notes of strings. example: A,E,C,G") String[] openNotes) {
        return service.getCustomRawUkulele(openNotes);
    }

    @GetMapping("/tone")
    public Ukulele getTone(@RequestParam
                           @ApiParam(value = "note of tone") String note,
                           @RequestParam
                           @ApiParam(value = "'major' or 'minor'") String tone,
                           @RequestParam
                           @ApiParam(value = "open notes of strings. example: A,E,C,G") String[] openNotes) {
        return service.getCustomToneUkulele(new Note(note), Tone.valueOf(tone.toUpperCase()), openNotes);
    }

    @GetMapping("/chord")
    public Ukulele getChord(@RequestParam
                            @ApiParam(value = "note of chord") String note,
                            @RequestParam
                            @ApiParam(value = "'major' or 'minor'") String tone,
                            @RequestParam
                            @ApiParam(value = "open notes of strings. example: A,E,C,G") String[] openNotes) {
        return service.getCustomChordUkulele(new Note(note), Tone.valueOf(tone.toUpperCase()), openNotes);
    }

    @GetMapping("/interval")
    public Ukulele getInterval(@RequestParam
                               @ApiParam(value = "number of guitar string with start key") int string,
                               @RequestParam
                               @ApiParam(value = "number of start key") int startKey,
                               @RequestParam
                               @ApiParam(value = "music interval. example '7' - quint, '12' - octave") int interval,
                               @RequestParam
                               @ApiParam(value = "open notes of strings. example: A,E,C,G") String[] openNotes) {
        return service.getCustomIntervalUkulele(string, startKey, interval, openNotes);
    }

    @GetMapping("/powerChord")
    public Ukulele getPowerChord(@RequestParam
                                 @ApiParam(value = "note of chord") String note,
                                 @RequestParam
                                 @ApiParam(value = "open notes of strings. example: A,E,C,G") String[] openNotes) {
        return service.getCustomPowerChordUkulele(new Note(note), openNotes);
    }
}
