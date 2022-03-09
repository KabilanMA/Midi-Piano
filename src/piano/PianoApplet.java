package piano;
import java.awt.event.*;
import javax.sound.midi.MidiUnavailableException;
import java.applet.*;
import java.awt.Color;
import java.util.HashSet;
import java.util.Set;
import music.*;

/*
 * <applet code="PianoApplet" width=500 height=300> </applet>
 */
public class PianoApplet extends Applet {
        private static PianoPlayer player;
        private final Set<Character> availableNoteKeys = new HashSet<>();
    
        @Override
	public void init() {
            /*
                initailize the set of key which can be played.
                1 - C
                2 - C-Sharp
                3 - D
                4 - D-Sharp
                5 - E
                6 - F
                7 - F-Sharp
                8 - G
                9 - G-Sharp
                0 - A
                - - A-Sharp
                = - B
                
            */
            availableNoteKeys.add('1');
            availableNoteKeys.add('2');
            availableNoteKeys.add('3');
            availableNoteKeys.add('4');
            availableNoteKeys.add('5');
            availableNoteKeys.add('6');
            availableNoteKeys.add('7');
            availableNoteKeys.add('8');
            availableNoteKeys.add('9');
            availableNoteKeys.add('0');
            availableNoteKeys.add('-');
            availableNoteKeys.add('=');
            setBackground(Color.GREEN);
            try {
                player = new PianoPlayer();
            }catch (MidiUnavailableException ex){
                return;
            }/*Add a KeyAdapter to the keylisteners to check the key presses*/
		addKeyListener(new KeyAdapter() {
                        @Override
			public void keyPressed(KeyEvent e) {
                            char key = (char) e.getKeyCode();
                            switch (key){
                                case 'S':
                                    player.changeInstrumentPiano();
                                    return;
                                case 'I':
                                    player.changeInstrument();
                                    return;
                                case 'P':
                                    player.requestPlayback();
                                    return;
                                case 'R':
                                    if(!player.isRecording())
                                        setBackground(Color.RED);
                                    else
                                        setBackground(Color.GREEN);
                                    
                                    player.toggleRecording();
                                    return;
				}
                            if (availableNoteKeys.contains(key) && !player.isRecordingPlaying()){
                                NoteEvent ne = new BeginNote(keyToPitch(key));
                                player.request(ne);
                            }
                        }
		});
                /*Add a KeyAdapter to the keylisteners to check the key releases*/
		addKeyListener(new KeyAdapter() {
                        @Override
			public void keyReleased(KeyEvent e) {
                            char key = (char) e.getKeyCode();
                            if(availableNoteKeys.contains(key) && !player.isRecordingPlaying()){
                                NoteEvent ne = new EndNote(keyToPitch(key));
                                player.request(ne);
                            }
			} 
		});
	}
        
        /*Convert the key to the Pitch*/
        private Pitch keyToPitch(char key) {
                    switch (key) {
                        case '1':
                             return new Pitch('C');
                        case '2':
                            return new Pitch('C').transpose(1);
			case '3':
                            return new Pitch('D');
                        case '4':
                            return new Pitch('D').transpose(1);
                        case '5':
                            return new Pitch('E');
                        case '6':
                            return new Pitch('F');
                        case '7':
                            return new Pitch('F').transpose(1);
                        case '8':
                            return new Pitch('G');
                        case '9':
                            return new Pitch('G').transpose(1);
                        case '0':
                            return new Pitch('A');
                        case '-':
                            return new Pitch('A').transpose(1);
                        case '=':
                            return new Pitch('B');
                            }
                            return null;
                }
}