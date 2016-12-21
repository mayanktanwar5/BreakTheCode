import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import org.restlet.*;
import org.restlet.resource.*;
import org.json.JSONObject ;
import org.restlet.resource.*;
import org.restlet.representation.* ;
import org.restlet.ext.json.* ;
import org.restlet.data.* ;

/**
 * Write a description of class Timer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Timer extends Actor
{
    private int count; // the counter field
    private int initialCount; // the initial time given before event occurs
    private boolean running;
 
    public Timer()
    {
        this(0, true);
    }
 
    public Timer(int timeBeforeEvent)
    {
        this(timeBeforeEvent, true);
    }
 
    public Timer(int timeBeforeEvent, boolean getsStarted) // int value given in seconds
    {
        setTimer(timeBeforeEvent);
        updateImage();
        running = getsStarted;
    }
 
    public void setTimer(int timeBeforeEvent)
    {
        initialCount = 60 * timeBeforeEvent;
        count = -initialCount;
    }
 
    private void updateImage()
    {
        String prefix = "Time Left - ";
        if (count >= 0) {
            prefix = "T + ";
        }
        int time = count * (int)Math.signum(count);
        time = time / 60;
        int secs = time % 60;
        time = (time - secs) / 60;
        int mins = time % 60;
        int hrs = (time - mins) / 24;
        String h = "00"+hrs;
        
        while (h.length() > 2) 
            h = h.substring(1);
        String m = "00"+mins;
        
        while (m.length() > 2) 
            m = m.substring(1);
        String s = "00" + secs;
        
        while (s.length() > 2) 
            s = s.substring(1);
            
        String text = prefix +" - " + m + "m : " + s + "s";
        GreenfootImage textImage = new GreenfootImage(text, 20, Color.black, new Color(0, 0, 0, 0));
        GreenfootImage image = new GreenfootImage(textImage.getWidth()+20, textImage.getHeight()+10);
        image.drawRect(0, 0, image.getWidth()-1, image.getHeight()-1);
        image.drawImage(textImage, (image.getWidth()-textImage.getWidth())/2, (image.getHeight()-textImage.getHeight())/2);
        setImage(image);
       
        
        if(mins == 0 && secs == 0)
        {
            ClientResource client = ClientRequestManager.getClient(ClientRequestManager.getRequestURL("/endgame"));
            client.get();
            stop();
        }
        
    }
 
    public void act()
    {
        if (running)
        {
            count++;
            if ((count + initialCount) % 60 == 0) 
                updateImage();
        }
    }
 
    public int getTime()
    {
        return count / 60;
    }
 
    public void start()
    {
        running = true;
    }
 
    public void stop()
    {
        running = false;
    }
}
