package com.music.backend.rest.instrument;

import com.music.backend.model.entity.Note;
import com.music.backend.model.instruments.DrumMidi;
import com.music.backend.service.DrumMidiService;
import com.music.backend.util.Tone;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.music.backend.model.InstrumentName.DRUM_MIDI;

@RestController
@RequestMapping("/" + DRUM_MIDI)
@Api(tags = {"Controller for akai mpd 218 midi"})
public class DrumMidiController {

    private final DrumMidiService service;

    public DrumMidiController(@Autowired DrumMidiService service) {
        this.service = service;
    }

    @GetMapping("/raw")
    public DrumMidi getRaw() {
        return service.getRawDrumMidi();
    }

    @GetMapping("/tone")
    public DrumMidi getTone(@RequestParam
                            @ApiParam(value = "note of tone") String note,
                            @RequestParam
                            @ApiParam(value = "'major' or 'minor'") String tone) {
        return service.getToneDrumMidi(new Note(note), Tone.valueOf(tone.toUpperCase()));
    }

    @GetMapping("/chord")
    public DrumMidi getChord(@RequestParam
                             @ApiParam(value = "tonic of chord") String note,
                             @RequestParam
                             @ApiParam(value = "'major' or 'minor'") String tone) {
        return service.getChordDrumMidi(new Note(note), Tone.valueOf(tone.toUpperCase()));
    }

    @GetMapping("/interval")
    public DrumMidi getInterval(@RequestParam
                                @ApiParam(value = "number of start key") int startKey,
                                @RequestParam
                                @ApiParam(value = "music interval. example '7,12' - " +
                                                  "quint and octave from quint") int... interval) {
        return service.getIntervalDrumMidi(startKey, interval);
    }

    @GetMapping("/powerChord")
    public DrumMidi getPowerChord(@RequestParam
                                  @ApiParam(value = "tonic of chord") String note) {
        return service.getPowerChordDrumMidi(new Note(note));
    }
}
