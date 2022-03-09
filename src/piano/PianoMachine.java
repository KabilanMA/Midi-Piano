package piano;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.sound.midi.MidiUnavailableException;
import midi.Midi;
import music.MusicMachine;
import music.NoteEvent;
import music.Pitch;

/**
 *
 * @author Kabilan
 */
public class PianoMachine implements MusicMachine {
    private final PianoPlayer player;
    private final Midi midi;
    private List<NoteEvent> recording, lastRecording;
    private final Set<Pitch> pitchesPlaying;
    private volatile boolean isRecording;
    private volatile long currentTime;
    
    public PianoMachine(PianoPlayer player) throws MidiUnavailableException{
        lastRecording = new ArrayList<>();
        recording = new ArrayList<>();
        pitchesPlaying = new HashSet<>();
        isRecording = false;
        midi = new Midi();
        this.player = player;
    }
    
    public void toggleRecording(){
        if(isRecording){
            lastRecording = recording;
        }else{
            currentTime = System.currentTimeMillis();
            recording = new ArrayList<>();
        }
        isRecording = !(isRecording);
        System.out.println("Recording : "+isRecording);
    }
    
    /**
     *
     * @param event
     * take the Note event and start playing the note from midi.
     * store the event on a list to record the events
     */
    @Override
    public void beginNote(NoteEvent event){
        Pitch pitch = event.getPitch();
        if(pitchesPlaying.contains(pitch))
            return;
        event = event.delayed((System.currentTimeMillis()-currentTime));
        currentTime = System.currentTimeMillis();
        pitchesPlaying.add(pitch);
        System.out.println(event);
        midi.beginNote(pitch.toMidiFrequency());
        if(isRecording)
            recording.add(event);
    }

    /**
     *
     * @param event
     * take the Note event and stop playing the note from midi.
     * store the event on a list to record the events
     */
    @Override
    public void endNote(NoteEvent event){
        Pitch pitch = event.getPitch();
        if(pitchesPlaying.contains(pitch)){
            midi.endNote(pitch.toMidiFrequency());
            pitchesPlaying.remove(pitch);
            if(isRecording){
                event = event.delayed((System.currentTimeMillis() - currentTime));
                currentTime = System.currentTimeMillis();
                recording.add(event);
            }
        }
        
    }
    public void requestPlayback(){
        player.playbackRecording(lastRecording);
    }
    
    /**
     *
     * @return
     */
    public boolean isRecording() {
        return isRecording;
    }

    public void changeInstrument(){
        midi.changeDefaultInstrument();
    }

    /**
     * Change back to default instrument
     */
    void changeInstrumentPiano() {
        midi.changeInstrumentPiano();
    }
}
