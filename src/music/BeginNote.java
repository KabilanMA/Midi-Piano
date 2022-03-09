package music;

public class BeginNote extends NoteEvent {
    private Pitch pitch;
    
    public BeginNote(Pitch pitch){
        this (pitch, 0);
    }
    
    public BeginNote(Pitch pitch, long delay){
        super(pitch, delay);
        this.pitch = pitch;
    }
    
    @Override
    public void execute(MusicMachine m){
        m.beginNote(this);
    }
    
    
    @Override
    public BeginNote delayed(long delay){
        return new BeginNote(pitch, delay);
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