/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podcaster;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.util.ArrayList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.SwingWorker;


/**
 *
 * @author Dave
 */
public class Player extends Application {
    
    private String media;
    private ArrayList<Song> songList;
    private static MediaPlayer mediaPlayer;
    private boolean playing = false;
    private boolean paused = false;
    private boolean downloading = false;
    private int progress;
    
    
    public Player() {
        songList = new ArrayList<>();
        JFXPanel fxPanel = new JFXPanel();
        
    }
    
    public void addSong(Song currSong) {
        songList.add(currSong);
    }
    
    public void clearList() {
        songList.clear();
    }
    
    public void play(int index) {
        String testSong = songList.get(index).getMP3();
        Media hit = new Media(testSong);
        mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
        playing = true;
    }
    
    public void playTest(int index) throws IOException {
        
        String testSong = songList.get(index).getTitle() + ".mp3";
        Media hit = new Media(new File(testSong).toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
        
    }
    
    public void Forward() {
        Duration current = mediaPlayer.getCurrentTime();
        double currentSec = current.toSeconds();
        Duration forward = Duration.seconds(10);        
        double forwardSec = forward.toSeconds();
        double newTime = forwardSec + currentSec;
        Duration spolaTill = Duration.seconds(newTime);
        
        Duration newDuration = spolaTill;
        mediaPlayer.seek(newDuration);
    }
    
    public void Reverse() {
        Duration current = mediaPlayer.getCurrentTime();
        double currentSec = current.toSeconds();
        Duration forward = Duration.seconds(10);        
        double forwardSec = forward.toSeconds();
        double newTime = forwardSec - currentSec;
        Duration spolaTill = Duration.seconds(newTime);
        
        Duration newDuration = spolaTill;
        mediaPlayer.seek(newDuration);
        
    }
    
    public void Stop() {
        mediaPlayer.stop();
        playing = false;
        paused = false;
    }
    
    public void Pause() {
        mediaPlayer.pause();
        paused = true;
        playing = false;
    }
    
    public void Resume() {
        mediaPlayer.play();
        paused = false;
        playing = true;
    }
        
    
    public void download(int index) throws MalformedURLException, IOException {
        if (paused) {
            Resume();
            return;
        }
        if (checkDownloads(index)) {
            playTest(index);
        }
        
    if (checkLink(index) && !checkDownloads(index)) {    
       /* URLConnection conn = new URL(songList.get(index).getMP3()).openConnection();
        InputStream is = conn.getInputStream();

        OutputStream outstream = new FileOutputStream(new File(songList.get(index).getTitle() + ".mp3"));
        byte[] buffer = new byte[4096];
        int len;
        while ((len = is.read(buffer)) > 0) {
            outstream.write(buffer, 0, len);
            downloading = true;
        }
        outstream.close();
        downloading = false;
        */
       String file = songList.get(index).getMP3();
       String title = songList.get(index).getTitle();
       
       Worker worker = new Worker(file,title);
       
       worker.addPropertyChangeListener(new PropertyChangeListener() {
           @Override
           public void propertyChange(PropertyChangeEvent evt) {
               if ("progress".equals(evt.getPropertyName())) {
                   progress = ((Integer) evt.getNewValue());
                   downloading = true;
                   System.out.println(progress);
               } else if (evt.getNewValue() == SwingWorker.StateValue.DONE) {
                   try {
                       worker.get();                       
                       playTest(index);
                       downloading = false;
                   } catch (InterruptedException ex) {
                       Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                   } catch (ExecutionException ex) {
                       Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                   } catch (IOException ex) {
                       Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                   }
                   
               }
           }
       });
       worker.execute();
       
       
        }
    }
    
    public ArrayList<Song> getSongList() {
        return songList;
    }
    
    public int getProgress() {
        return progress;
    }
    
    public boolean isDownloading() {
        return downloading;
    }
    
    public boolean isPaused() {
        return paused;
    }
    
    public boolean checkDownloads(int index) {
        File f = new File(songList.get(index).getTitle() + ".mp3");
            if(f.exists() && !f.isDirectory()) { 
                 return true;
            } else {
                return false;
            }
    }
    
    public void checkDownloaded() {
        for (int i = 0; i < songList.size(); i++) {
            File f = new File(songList.get(i).getTitle() + ".mp3");
            if(f.exists() && !f.isDirectory()) { 
                 songList.get(i).setDownloaded(true);
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
    }
    
    public boolean checkLink(int index) {
        if (songList.get(index).getMP3().isEmpty()){
            return false;
        } else {
            return true;
        }
    }
    
}
