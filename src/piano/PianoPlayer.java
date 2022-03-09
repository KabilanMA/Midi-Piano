
package piano;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.sound.midi.MidiUnavailableException;
import music.NoteEvent;

public class PianoPlayer {
    private final BlockingQueue<NoteEvent> queue, delayQueue;
    private final PianoMachine machine;
    private boolean isRecordingPlaying;
    
    public PianoPlayer() throws MidiUnavailableException{
        queue = new LinkedBlockingQueue<>();
        delayQueue = new LinkedBlockingQueue<>();
        machine = new PianoMachine(this);
        isRecordingPlaying = false;
        /*
        * start two threads:
        *   1. Check the queue and play the note events on the queue
        *   2. Check the delayQueue and put that into the queue to play
        *       the notes in case of playing back the recording.
        */
        new Thread("QueueThread"){
            @Override
            public void run(){
                processQueue();
            }
        }.start();
        
        new Thread("DelayQueueThread"){
            @Override
            public void run(){
                processDelayQueue();
            }
        }.start();
    }
    
    public void request(NoteEvent e){
        try {
            queue.put(e);
        } catch (InterruptedException ex) {
            System.out.println("Something went wrong. Restart again");
        }
    }
    
    public void requestPlayback(){
        machine.requestPlayback();
    }
    
    public void toggleRecording(){
        machine.toggleRecording();
    }
    
    public void playbackRecording(List<NoteEvent> recording){
        isRecordingPlaying = true;
        if(recording.isEmpty()){
            isRecordingPlaying = false;
        }
        recording.forEach((e) -> {
            try {
                delayQueue.put(e);
            } catch (InterruptedException ex) {
                System.out.println("Something went wrong. Restart again");
            }
        });
    }
    
    public void processQueue(){
        while(true){
            try {
                NoteEvent e = queue.take();
                e.execute(machine);
            } catch (InterruptedException ex) {
                System.out.println("Something went wrong. Restart again");
            }
        }
    }
    
    public void processDelayQueue(){
        while(true){
            try {
                NoteEvent e = delayQueue.take();
                midi.Midi.wait((int)(e.getDelay()));
                queue.put(e);
                isRecordingPlaying = !delayQueue.isEmpty();
            } catch (InterruptedException ex) {
            }
        }
    }
    
    public boolean isRecording(){
        return machine.isRecording();
    }
    
    public boolean isRecordingPlaying(){
        return isRecordingPlaying;
    }
    
    public void changeInstrument(){
        machine.changeInstrument();
    }

    /**
     * change back to PIANO instrument 
     */
    public void changeInstrumentPiano() {
        machine.changeInstrumentPiano();
    }
}
