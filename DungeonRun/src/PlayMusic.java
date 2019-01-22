import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
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
        thread = new Thread(this);
        running = true;
        thread.start();
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
                    inputStream = AudioSystem.getAudioInputStream(PlayMusic.class.getResource(url));
                    clip.open(inputStream);
                    clip.loop(10);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public void playBackGround(String string) // call to play .wav file String is the path to the wav file and then add .wav from url variable
    {
        if(clip != null)
        {
            clip.stop();
            clip.close();
        }
        try
        {
            clip = AudioSystem.getClip();
        }
        catch(LineUnavailableException e)
        {
            e.printStackTrace();
        }
        url = string + ".wav";
       playSong = true;
       inputStream = null;
    }

    public void disposeSound()
    {
        if(clip != null)
        {
        	clip.loop(-1);
            clip.stop();
            clip.close();
        }
        clip = null;
        playSong = false;
        inputStream = null;
    }
    public void stopbackground()
    {
    	
    	  this.clip.stop();
          this.clip.close();
         
        
       
    }
}
