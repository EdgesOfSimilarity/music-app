package com.music.backend.rest.instrument;

import com.music.backend.model.entity.Note;
import com.music.backend.model.instruments.StringInstrument;
import com.music.backend.service.api.StringInstrumentService;
import com.music.backend.util.Tone;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;

@RestController
@Api(tags = {"Controller for string instruments"})
public class StringInstrumentController {

    private final Map<String, StringInstrumentService> services;

    public StringInstrumentController(@Autowired List<StringInstrumentService> services) {
        this.services = new HashMap<>();
        services.forEach(service -> this.services.put(service.getInstrument(), service));
    }

    @GetMapping("/{instrument}/raw")
    public StringInstrument getRaw(@PathVariable
                                   @ApiParam(value = "actual instrument list can get " +
                                           "in meta information controller") String instrument,
                                   @RequestParam(required = false)
                                   @ApiParam(value = "open notes of strings. example: E,B,G,D,A,E. " +
                                           "if null - use default strings") String[] openNotes) {
        return getService(instrument).getRawStringInstrument(openNotes);
    }

    @GetMapping("/{instrument}/tone")
    public StringInstrument getTone(@PathVariable
                                    @ApiParam(value = "actual instrument list can get " +
                                            "in meta information controller") String instrument,
                                    @RequestParam
                                    @ApiParam(value = "note of tone") String note,
                                    @RequestParam
                                    @ApiParam(value = "'major' or 'minor'") String tone,
                                    @RequestParam(required = false)
                                    @ApiParam(value = "open notes of strings. example: E,B,G,D,A,E. " +
                                            "if null - use default strings") String[] openNotes) {
        return getService(instrument)
                .getToneStringInstrument(new Note(note), Tone.valueOf(tone.toUpperCase()), openNotes);
    }

    @GetMapping("/{instrument}/chord")
    public StringInstrument getChord(@PathVariable
                                     @ApiParam(value = "actual instrument list can get " +
                                             "in meta information controller") String instrument,
                                     @RequestParam
                                     @ApiParam(value = "tonic of chord") String note,
                                     @RequestParam
                                     @ApiParam(value = "'major' or 'minor'") String tone,
                                     @RequestParam(required = false)
                                     @ApiParam(value = "open notes of strings. example: E,B,G,D,A,E. " +
                                             "if null - use default strings") String[] openNotes) {
        return getService(instrument)
                .getChordStringInstrument(new Note(note), Tone.valueOf(tone.toUpperCase()), openNotes);
    }

    @GetMapping("/{instrument}/interval")
    public StringInstrument getInterval(@PathVariable
                                        @ApiParam(value = "actual instrument list can get " +
                                                "in meta information controller") String instrument,
                                        @RequestParam
                                        @ApiParam(value = "number of guitar string with start key") int string,
                                        @RequestParam
                                        @ApiParam(value = "number of start key") int startKey,
                                        @RequestParam
                                        @ApiParam(value = "music interval. example '7' - quint, " +
                                                "'12' - octave") int interval,
                                        @RequestParam(required = false)
                                        @ApiParam(value = "open notes of strings. example: E,B,G,D,A,E. " +
                                                "if null - use default strings") String[] openNotes) {
        return getService(instrument).getIntervalStringInstrument(string, startKey, interval, openNotes);
    }

    @GetMapping("/{instrument}/powerChord")
    public StringInstrument getPowerChord(@PathVariable
                                          @ApiParam(value = "actual instrument list can get " +
                                                  "in meta information controller") String instrument,
                                          @RequestParam
                                          @ApiParam(value = "tonic of chord") String note,
                                          @RequestParam(required = false)
                                          @ApiParam(value = "open notes of strings. example: E,B,G,D,A,E. " +
                                                  "if null - use default strings") String[] openNotes) {
        return getService(instrument).getPowerChordStringInstrument(new Note(note), openNotes);
    }

    private StringInstrumentService getService(String instrument) {
        return Optional.ofNullable(this.services.get(instrument))
                .orElseThrow(() -> new IllegalArgumentException(format("incorrect instrument %s", instrument)));
    }
}
