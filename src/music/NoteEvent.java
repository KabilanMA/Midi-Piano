
package music;
/**
 * An abstract class for handling the note events
*/
public abstract class NoteEvent {
    protected final Pitch pitch;
    protected final long delay;
    
    public NoteEvent(Pitch pitch){
        this (pitch, 0);
    }
    
    public NoteEvent(Pitch pitch, long delay){
        this.delay = delay;
        this.pitch = pitch;
    }
    
    abstract public NoteEvent delayed(long delay);
    abstract public void execute(MusicMachine m);

    abstract public long getDelay();

    abstract public Pitch getPitch();
}
