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
@RequestMapping("/ukulele")
@Api(tags = {"Controller for ukulele"})
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
    public Ukulele getTone(@RequestParam
                           @ApiParam(value = "note of tone") String note,
                           @RequestParam
                           @ApiParam(value = "'major' or 'minor'") String tone) {
        return service.getToneUkulele(new Note(note), Tone.valueOf(tone.toUpperCase()));
    }

    @GetMapping("/chord")
    public Ukulele getChord(@RequestParam
                            @ApiParam(value = "note of chord") String note,
                            @RequestParam
                            @ApiParam(value = "'major' or 'minor'") String tone) {
        return service.getChordUkulele(new Note(note), Tone.valueOf(tone.toUpperCase()));
    }

    @GetMapping("/interval")
    public Ukulele getInterval(@RequestParam
                               @ApiParam(value = "number of guitar string with start key") int string,
                               @RequestParam
                               @ApiParam(value = "number of start key") int startKey,
                               @RequestParam
                               @ApiParam(value = "music interval. example '7' - quint, '12' - octave") int interval) {
        return service.getIntervalUkulele(string, startKey, interval);
    }

    @GetMapping("/powerChord")
    public Ukulele getPowerChord(@RequestParam
                                 @ApiParam(value = "note of chord") String note) {
        return service.getPowerChordUkulele(new Note(note));
    }
}
