package com.music.backend.rest.instrument;

import com.music.backend.model.entity.Note;
import com.music.backend.model.instruments.Guitar;
import com.music.backend.service.GuitarService;
import com.music.backend.util.Tone;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/guitar")
@Api(tags = {"Controller for guitar"})
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
    public Guitar getTone(@RequestParam
                          @ApiParam(value = "note of tone") String note,
                          @RequestParam
                          @ApiParam(value = "'major' or 'minor'") String tone) {
        return service.getToneGuitar(new Note(note), Tone.valueOf(tone.toUpperCase()));
    }

    @GetMapping("/chord")
    public Guitar getChord(@RequestParam
                           @ApiParam(value = "tonic of chord") String note,
                           @RequestParam
                           @ApiParam(value = "'major' or 'minor'") String tone) {
        return service.getChordGuitar(new Note(note), Tone.valueOf(tone.toUpperCase()));
    }

    @GetMapping("/interval")
    public Guitar getInterval(@RequestParam
                              @ApiParam(value = "number of guitar string with start key") int string,
                              @RequestParam
                              @ApiParam(value = "number of start key") int startKey,
                              @RequestParam
                              @ApiParam(value = "music interval. example '7' - quint, '12' - octave") int interval) {
        return service.getIntervalGuitar(string, startKey, interval);
    }

    @GetMapping("/powerChord")
    public Guitar getPowerChord(@RequestParam
                                @ApiParam(value = "tonic of chord") String note) {
        return service.getPowerChordGuitar(new Note(note));
    }
}
