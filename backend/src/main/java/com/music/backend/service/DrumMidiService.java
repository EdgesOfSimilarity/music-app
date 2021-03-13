package com.music.backend.service;

import com.music.backend.model.entity.Key;
import com.music.backend.model.entity.MidiBank;
import com.music.backend.model.entity.Note;
import com.music.backend.model.instruments.DrumMidi;
import com.music.backend.service.api.InstrumentService;
import com.music.backend.util.KeyFiller;
import com.music.backend.util.NoteFiller;
import com.music.backend.util.Tone;
import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Stream;

import static com.music.backend.model.InstrumentName.DRUM_MIDI;

@Service
public class DrumMidiService implements InstrumentService {

    public static final int BANKS_AMOUNT = 3;
    public static final int BANK_DIMENSION = 4;

    private final NoteFiller noteFiller;
    private final KeyFiller keyFiller;

    @Getter
    private final String instrument;

    @Value("${drum.midi.banks}")
    private char[] bankNames;

    @Value("${drum.midi.start.note}")
    private String startNote;

    public DrumMidiService(@Autowired NoteFiller noteFiller,
                           @Autowired KeyFiller keyFiller,
                           @Value(DRUM_MIDI) String instrument) {
        this.noteFiller = noteFiller;
        this.keyFiller = keyFiller;
        this.instrument = instrument;
    }

    public DrumMidi getRawDrumMidi() {
        final DrumMidi drumMidi = new DrumMidi();

        drumMidi.setBanks(generateBanks());
        fillBanks(drumMidi.getBanks());

        return drumMidi;
    }

    public DrumMidi getToneDrumMidi(Note toneNote, Tone tone) {
        final DrumMidi drumMidi = getRawDrumMidi();
        final Note[] toneNotes = noteFiller.getNoteToneSequence(toneNote, tone);
        final Key[] keys = collectKeysToOneArray(drumMidi.getBanks());

        keyFiller.fillKeysInToneSequence(keys, toneNotes);

        return drumMidi;
    }

    public DrumMidi getChordDrumMidi(Note tonic, Tone tone) {
        final DrumMidi drumMidi = getRawDrumMidi();
        final Note[] chordNotes = noteFiller.getChordNotes(tonic, tone);
        final Key[] keys = collectKeysToOneArray(drumMidi.getBanks());

        keyFiller.fillKeysInChord(keys, chordNotes);

        return drumMidi;
    }

    public DrumMidi getIntervalDrumMidi(int startKeyNumber, int... intervals) {
        final DrumMidi drumMidi = getRawDrumMidi();
        final Key[] keys = collectKeysToOneArray(drumMidi.getBanks());

        keyFiller.fillKeyIntervals(keys, startKeyNumber, intervals);

        return drumMidi;
    }

    public DrumMidi getPowerChordDrumMidi(Note tonic) {
        final DrumMidi drumMidi = getRawDrumMidi();
        final Note[] chordNotes = noteFiller.getPowerChordNotes(tonic);
        final Key[] keys = collectKeysToOneArray(drumMidi.getBanks());

        keyFiller.fillKeysInChord(keys, chordNotes);

        return drumMidi;
    }

    private MidiBank[] generateBanks() {
        return Stream.generate(MidiBank::new)
                .limit(BANKS_AMOUNT)
                .toArray(MidiBank[]::new);
    }

    private void fillBanks(MidiBank[] banks) {
        fillBankNames(banks);
        generateKeys(banks);

        final Note[] notes = noteFiller
                .getNoteSequence(BANK_DIMENSION * BANK_DIMENSION * BANKS_AMOUNT, new Note(startNote));
        final Key[] keys = collectKeysToOneArray(banks);

        keyFiller.fillMidiKeys(keys, notes);
        revertKeyRows(banks);
    }

    private void fillBankNames(MidiBank[] banks) {
        for (int i = 0; i < BANKS_AMOUNT; i++) {
            banks[i].setName(bankNames[i]);
        }
    }

    private void generateKeys(MidiBank[] banks) {
        Arrays.stream(banks)
                .forEach(bank -> bank.setKeys(
                        Stream.generate(
                                () -> Stream.generate(Key::new)
                                        .limit(BANK_DIMENSION)
                                        .toArray(Key[]::new))
                                .limit(BANK_DIMENSION)
                                .toArray(Key[][]::new)
                ));
    }

    private Key[] collectKeysToOneArray(MidiBank[] banks) {
        return Arrays.stream(banks)
                .map(MidiBank::getKeys)
                .flatMap(Arrays::stream)
                .flatMap(Arrays::stream)
                .toArray(Key[]::new);
    }

    private void revertKeyRows(MidiBank[] banks) {
        Arrays.stream(banks)
                .map(MidiBank::getKeys)
                .forEach(ArrayUtils::reverse);
    }
}
