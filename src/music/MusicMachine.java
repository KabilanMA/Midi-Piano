package music;

/**
 * This interface help to connect PianoMachine and NoteEvents
 * with low coupling.
 */
public interface MusicMachine {
    public void beginNote(NoteEvent event);
    public void endNote(NoteEvent event);
}
