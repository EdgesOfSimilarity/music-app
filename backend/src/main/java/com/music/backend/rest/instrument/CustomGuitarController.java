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
@RequestMapping("/custom/guitar")
@Api(tags = {"Controller for guitar with custom strings"})
public class CustomGuitarController {

    private final GuitarService service;

    public CustomGuitarController(@Autowired GuitarService service) {
        this.service = service;
    }

    @GetMapping("/raw")
    public Guitar getRaw(@RequestParam
                         @ApiParam(value = "open notes of strings. example: E,B,G,D,A,E") String[] openNotes) {
        return service.getCustomRawGuitar(openNotes);
    }

    @GetMapping("/tone")
    public Guitar getTone(@RequestParam
                          @ApiParam(value = "note of tone") String note,
                          @RequestParam
                          @ApiParam(value = "'major' or 'minor'") String tone,
                          @RequestParam
                          @ApiParam(value = "open notes of strings. example: E,B,G,D,A,E") String[] openNotes) {
        return service.getCustomToneGuitar(new Note(note), Tone.valueOf(tone.toUpperCase()), openNotes);
    }

    @GetMapping("/chord")
    public Guitar getChord(@RequestParam
                           @ApiParam(value = "note of chord") String note,
                           @RequestParam
                           @ApiParam(value = "'major' or 'minor'") String tone,
                           @RequestParam
                           @ApiParam(value = "open notes of strings. example: E,B,G,D,A,E") String[] openNotes) {
        return service.getCustomChordGuitar(new Note(note), Tone.valueOf(tone.toUpperCase()), openNotes);
    }

    @GetMapping("/interval")
    public Guitar getInterval(@RequestParam
                              @ApiParam(value = "number of guitar string with start key") int string,
                              @RequestParam
                              @ApiParam(value = "number of start key") int startKey,
                              @RequestParam
                              @ApiParam(value = "music interval. example '7' - quint, '12' - octave") int interval,
                              @RequestParam
                              @ApiParam(value = "open notes of strings. example: E,B,G,D,A,E") String[] openNotes) {
        return service.getCustomIntervalGuitar(string, startKey, interval, openNotes);
    }

    @GetMapping("/powerChord")
    public Guitar getPowerChord(@RequestParam
                                @ApiParam(value = "note of chord") String note,
                                @RequestParam
                                @ApiParam(value = "open notes of strings. example: E,B,G,D,A,E") String[] openNotes) {
        return service.getCustomPowerChordGuitar(new Note(note), openNotes);
    }
}
