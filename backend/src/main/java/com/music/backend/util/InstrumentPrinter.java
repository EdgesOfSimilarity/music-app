package com.music.backend.util;

import com.music.backend.model.entity.GuitarString;
import com.music.backend.model.entity.Key;
import com.music.backend.model.entity.MidiBank;
import com.music.backend.model.entity.Note;
import com.music.backend.model.instruments.DrumMidi;
import com.music.backend.model.instruments.Guitar;
import com.music.backend.model.instruments.PianoMidi;
import com.music.backend.model.instruments.Ukulele;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static org.apache.commons.lang3.ArrayUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Component
public class InstrumentPrinter {

    private static final int NOTES_AMOUNT = 12;

    @Value("${piano.black.keys}")
    private int[] blackPianoKeys;

    public String printGuitar(Guitar guitar) {
        return wrapToMonospace(printStringInstrument(guitar.getStrings()));
    }

    public String printUkulele(Ukulele ukulele) {
        return wrapToMonospace(printStringInstrument(ukulele.getStrings()));
    }

    public String printDrumMidi(DrumMidi midi) {
        hideNotesIfNotRawMode(getKeys(midi));

        final MidiBank[] banks = midi.getBanks();
        ArrayUtils.reverse(banks); // reverting arrays in midi object, care

        final StringBuffer buffer = new StringBuffer();
        Arrays.stream(banks)
                .forEach(bank -> appendMidiBank(buffer, bank));

        return wrapToMonospace(buffer.toString());
    }

    public String printPianoMidi(PianoMidi midi) {
        final Key[] keys = midi.getKeys();

        hideNotesIfNotRawMode(keys);

        final StringBuffer buffer = new StringBuffer();
        appendBlackPianoKeys(buffer, getBlackPianoKeys(keys));
        appendWhitePianoKeys(buffer, getWhitePianoKeys(keys));

        return wrapToMonospace(buffer.toString());
    }

    private String wrapToMonospace(String text) {
        return "<p style=\"font-family: monospace; white-space: pre; font-size: 26px\">" + text + "</p>";
    }

    private String printStringInstrument(GuitarString[] strings) {
        if (isEmpty(strings)) {
            return EMPTY;
        }

        hideNotesIfNotRawMode(getKeys(strings));

        final StringBuffer buffer = new StringBuffer();
        final int keys = getStringKeyAmount(strings);

        appendKeyNumbers(buffer, keys);
        appendStringKeys(strings, buffer);

        return buffer.toString();
    }

    private int getStringKeyAmount(GuitarString[] strings) {
        return Arrays.stream(strings)
                .findFirst()
                .map(string -> string.getKeys().length)
                .orElse(0);
    }

    private void appendKeyNumbers(StringBuffer buffer, int keys) {
        for (int i = 0; i < keys; i++) {
            buffer.append(i < 10 ? i + "  " : i + " ");
        }
        buffer.append("<br>");
    }

    private void appendStringKeys(GuitarString[] strings, StringBuffer buffer) {
        for (GuitarString string : strings) {
            Arrays.stream(string.getKeys())
                    .map(Key::getNote)
                    .map(Note::getName)
                    .map(note -> note.length() < 2 ? note + "  " : note + " ")
                    .forEach(buffer::append);
            buffer.append("<br>");
        }
    }

    private void appendMidiBank(StringBuffer buffer, MidiBank bank) {
        Arrays.stream(bank.getKeys())
                .forEach(keys -> appendMidiBankRow(buffer, keys));
        buffer.append("<br><br><br>");
    }

    private void appendMidiBankRow(StringBuffer buffer, Key[] keys) {
        Arrays.stream(keys)
                .map(Key::getNote)
                .map(Note::getName)
                .map(note -> note.length() < 2 ? note + "  " : note + " ")
                .forEach(buffer::append);
        buffer.append("<br>");
    }

    private void hideNotesIfNotRawMode(Key[] keys) {
        if (isNotRawMode(keys)) {
            Arrays.stream(keys)
                    .filter(key -> !key.isInTone() && !key.isInChord() && !key.isInInterval())
                    .map(Key::getNote)
                    .forEach(note -> note.setName("--"));
        }
    }

    private boolean isNotRawMode(Key[] keys) {
        return Arrays.stream(keys)
                .anyMatch(key -> key.isInTone() || key.isInChord() || key.isInInterval());
    }

    private Key[] getKeys(DrumMidi midi) {
        return Arrays.stream(midi.getBanks())
                .map(MidiBank::getKeys)
                .flatMap(Arrays::stream)
                .flatMap(Arrays::stream)
                .toArray(Key[]::new);
    }

    private Key[] getKeys(GuitarString[] strings) {
        return Arrays.stream(strings)
                .map(GuitarString::getKeys)
                .flatMap(Arrays::stream)
                .toArray(Key[]::new);
    }

    private void appendBlackPianoKeys(StringBuffer buffer, Key[] keys) {
        Arrays.stream(keys)
                .map(this::resolveBlackKeyString)
                .forEach(buffer::append);
        buffer.append("<br>");
    }

    private Key[] getBlackPianoKeys(Key[] keys) {
        return Arrays.stream(keys)
                .filter(key -> ArrayUtils.contains(blackPianoKeys, key.getNumber() % NOTES_AMOUNT))
                .toArray(Key[]::new);
    }

    private String resolveBlackKeyString(Key key) {
        final int number = key.getNumber() % NOTES_AMOUNT;
        final String note = key.getNote().getName();
        switch (number) {
            case 2:
                return " " + note + " ";
            case 4:
                return note + "    ";
            case 7:
            case 9:
                return note + " ";
            case 11:
                return note + "   ";
            default:
                return EMPTY;
        }
    }

    private void appendWhitePianoKeys(StringBuffer buffer, Key[] keys) {
        Arrays.stream(keys)
                .map(Key::getNote)
                .map(Note::getName)
                .map(note -> note.length() < 2 ? note + "  " : note + " ")
                .forEach(buffer::append);
        buffer.append("<br>");
    }

    private Key[] getWhitePianoKeys(Key[] keys) {
        return Arrays.stream(keys)
                .filter(key -> !ArrayUtils.contains(blackPianoKeys, key.getNumber() % NOTES_AMOUNT))
                .toArray(Key[]::new);
    }
}
