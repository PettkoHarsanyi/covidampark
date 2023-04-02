package GameEngine;

import java.net.URL;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;

public class BackgroundMusic implements LineListener {
    private URL music;
    private Clip clip;
    private boolean stopped;

    public BackgroundMusic(URL music_)
	{
		music = music_;
		stopped = true;
		try {
                    clip = (Clip)AudioSystem.getLine(new Line.Info(Clip.class));
                    clip.addLineListener(this);
		} catch (LineUnavailableException e) {
                    e.printStackTrace();
		}
	}
        
	@Override
    public void update(LineEvent event){
	if(!stopped){
            if (event.getType() == LineEvent.Type.STOP){
            start();
            }
        }
    }
    @SuppressWarnings("static-access")
    public void start(){
		stopped = false;
	    try{

	    	if(!clip.isOpen()){
	    		clip.open(AudioSystem.getAudioInputStream(music));
	    	}
	    	
	    	if(!clip.isRunning()){
	    		clip.loop(Clip.LOOP_CONTINUOUSLY);
	    		clip.start();
                }
	    }
	    catch (Exception exc){
	        exc.printStackTrace(System.out);
	    }
	}
	
    public void stop(){
        stopped = true;
        clip.stop();
        clip.close();
	}
    
    public void efx() throws InterruptedException{
    start();
    TimeUnit.MILLISECONDS.sleep(100);
    stop();
    }
    
    public boolean getStatus(){
        return !stopped;
    }
}