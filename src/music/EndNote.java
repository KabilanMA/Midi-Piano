package music;

/**
 *
 * @author KABILAN
 */
public class EndNote extends NoteEvent {
    private Pitch pitch;
    
    public EndNote(Pitch pitch){
        this(pitch,0);
    }
    
    public EndNote(Pitch pitch, long delay){
        super(pitch, delay);
        this.pitch = pitch;
    }

    /**
     *
     * @param delay
     * @return
     */
    @Override
    public EndNote delayed(long delay) {
        return new EndNote(pitch, delay);
    }

    @Override
    public void execute(MusicMachine m) {
        m.endNote(this);
    }

    @Override
    public long getDelay() {
        return delay;
    }

    @Override
    public Pitch getPitch() {
        return pitch;
    }
    
    @Override
    public String toString(){
        return (pitch.toString());
    }
}
