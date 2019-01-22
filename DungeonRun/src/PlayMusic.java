import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;

public class PlayMusic implements Runnable
{
    private boolean running = false;
    private Thread thread;
// make an instance of this there you wanna play then call the playbackground with a string path to the file you wanna play
    
    public PlayMusic()
    {
        start();
    }

    public void start()
    {
        if(running)
            return;
        this.thread = new Thread(this);
       this. running = true;
        this.thread.start();
    }

    //
    private boolean playSong = false;
    private AudioInputStream inputStream;
    private String url;
    private Clip clip;

    @Override
    public void run()
    {
        while(running)
        {
            if(inputStream == null && playSong)
            {
                playSong = false;
                try
                {
                	this. inputStream = AudioSystem.getAudioInputStream(PlayMusic.class.getResource(url));
                	this. clip.open(inputStream);
                	 FloatControl gainControl = 
                	 		    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                	 		gainControl.setValue(0.0f); 
                	this. clip.loop(10);
                }
                catch(Exception e)
                {
                	GuiConsole.io.println("Somthings wrong in run getting the AudioStream"+e);
                }
            }
        }
    }

    public void playBackGround(String string) // call to play .wav file String is the path to the wav file and then add .wav from url variable
    {
        if(clip != null)
        {
        	this.clip.stop();
        	this.clip.close();
        }
        try
        {
            clip = AudioSystem.getClip();
        }
        catch(LineUnavailableException e)
        {
        	GuiConsole.io.println("Somthings wrong in playBackGround getting the  AudioSystem.getClip"+e);
        }
        url = string + ".wav";
        this.  playSong = true;
        this. inputStream = null;
    }

    public void disposeSound()
    {
        if(clip != null)
        {
        	this.clip.loop(-1);
        	this. clip.stop();
        	this. clip.close();
        }
        this. clip = null;
        this. playSong = false;
        this. inputStream = null;
    }
    public void stopbackground()
    {
    	
    	  this.clip.stop();
          this.clip.close();
         
        
       
    }
 public void   changeVolume(int i){
	 FloatControl gainControl = 
 		    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
 		gainControl.setValue(-10.0f); 
    }
}//END CLASS
