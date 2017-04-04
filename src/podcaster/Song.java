/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podcaster;

/**
 *
 * @author Dave
 */
public class Song {
    
    private String mp3link;
    private String title;
    private boolean downloaded = false;
    private String description;
    private String songImage;
    
    public Song(String title) {
        this.title = title;
        this.mp3link = "";
        this.songImage = "";
    }
    
    public void setMP3(String url) {
        this.mp3link = url;
    }
    
    public String getMP3() {
        return this.mp3link;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setDownloaded(boolean status) {
        downloaded = status;
    }
    
    public boolean getDownloaded() {
        return downloaded;
    }
    
    public void setDescription(String text) {
        description = text;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setImage(String currImg) {
        this.songImage = currImg;
    }
    
    public String getImage() {
        return songImage;
    }
}
