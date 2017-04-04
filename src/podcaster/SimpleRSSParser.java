/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podcaster;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Dave
 */
public class SimpleRSSParser {
    private final String content;
    
    public SimpleRSSParser(String inContent) {
        this.content = inContent;
    }
    
    public String getDescription() {
        StringBuffer Description = new StringBuffer();
        String tmp = content.substring(content.indexOf("<item>"));
        
        String desc;
        
        while ((desc = getTextBetween(tmp, "<description><![CDATA[", "]]></description>")) != "") {
			// Titeln för en nyhet ligger mellan taggarna <title>. Så länge som
			// ovan returnerar något annat än en tom sträng finns fler nyheter
                        
                                                
			Description.append(removeCDATA(desc) + "<br>");
                        
			// Tar reda på var denna nyhet (item) slutar
			int index = tmp.indexOf("</item>");

			// Skapar en delsträng av kvarvarande innehåll med
			// början från där denna nyhet slutar. Adderar ett eftersom
			// </item> annars ligger kvar i den nya strängen vilket gör
			// att samma nyhet (title) även den finns kvar i strängen.
			tmp = tmp.substring(index + 1);
		}

		return Description.toString();

    }
    
    public String getTitle() {
        String s = getTextBetween("<title>", "</title");
        
        s = s.replaceAll("&amp;", "&");
        return s;
    }
    
    private String getTextBetween(String start, String end) {
        return getTextBetween(content, start, end);
    }
    
    public Image getImage() throws IOException {
        Image icon = null;
        
        String s = getTextBetween("<image>", "</image>");
        s = getTextBetween("<url>", "</url>");
        
        if (s.isEmpty()) {
            return null;
        }
        
        try {
            URL imageUrl = new URL(s);
            icon = ImageIO.read(imageUrl);
        } catch (MalformedURLException ex) {
            Logger.getLogger(SimpleRSSParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return icon;
    }
    
    public ArrayList<String> getEpisodeImage() throws IOException {
         
         ArrayList<String> imgVec = new ArrayList<String>();
        
        String tmp = content.substring(content.indexOf("<item>"));        
        
        String link;
	while ((link = getTextBetween(tmp, "<itunes:image href=\"", "\" />")) != "") {
        
        
            //URL imageUrl = new URL(link);
            //icon = ImageIO.read(imageUrl);
            imgVec.add(link);
        
            int index = tmp.indexOf("</item>");

			// Skapar en delsträng av kvarvarande innehåll med
			// början från där denna nyhet slutar. Adderar ett eftersom
			// </item> annars ligger kvar i den nya strängen vilket gör
			// att samma nyhet (title) även den finns kvar i strängen.
            tmp = tmp.substring(index + 1);
        }
        
        return imgVec;
    }
    
    public ArrayList<String> getEpisodeImageAcast() throws IOException {
         
         ArrayList<String> imgVec = new ArrayList<String>();
        
        String tmp = content.substring(content.indexOf("<item>"));        
        
        String link;
	while ((link = getTextBetween(tmp, "<itunes:image href=\"", "\"/>")) != "") {
        
        
            //URL imageUrl = new URL(link);
            //icon = ImageIO.read(imageUrl);
            imgVec.add(link);
        
            int index = tmp.indexOf("</item>");

			// Skapar en delsträng av kvarvarande innehåll med
			// början från där denna nyhet slutar. Adderar ett eftersom
			// </item> annars ligger kvar i den nya strängen vilket gör
			// att samma nyhet (title) även den finns kvar i strängen.
            tmp = tmp.substring(index + 1);
        }
        
        return imgVec;
    }
    
    public String getSound() {
        StringBuffer sound = new StringBuffer();
        

		// Varje nyhet ligger i separata <item>-taggar. Vi skapar därför
		// en ny delsträng av content med början vid första taggen <item>
		String tmp = content.substring(content.indexOf("<item>"));

		String link;
		while ((link = getTextBetween(tmp, "<link>", "</link>")) != "") {
			// Titeln för en nyhet ligger mellan taggarna <title>. Så länge som
			// ovan returnerar något annat än en tom sträng finns fler nyheter
                        
                        if (link.contains(".mp3")) {                        
			sound.append(removeCDATA(link) + "<br>");
                        } 
                        
			// Tar reda på var denna nyhet (item) slutar
			int index = tmp.indexOf("</item>");

			// Skapar en delsträng av kvarvarande innehåll med
			// början från där denna nyhet slutar. Adderar ett eftersom
			// </item> annars ligger kvar i den nya strängen vilket gör
			// att samma nyhet (title) även den finns kvar i strängen.
			tmp = tmp.substring(index + 1);
		}
                System.out.println(sound.toString().length());
                
		return sound.toString();
    }
    
    public String getSoundOther() {
        StringBuffer sound = new StringBuffer();

		// Varje nyhet ligger i separata <item>-taggar. Vi skapar därför
		// en ny delsträng av content med början vid första taggen <item>
		String tmp = content.substring(content.indexOf("<item>"));

		String link;
		while ((link = getTextBetween(tmp, "url=\"", "?")) != "") {
			// Titeln för en nyhet ligger mellan taggarna <title>. Så länge som
			// ovan returnerar något annat än en tom sträng finns fler nyheter
                        
                        if (link.contains(".mp3")) {                        
			sound.append(removeCDATA(link) + "<br>");
                        }
			// Tar reda på var denna nyhet (item) slutar
			int index = tmp.indexOf("</item>");

			// Skapar en delsträng av kvarvarande innehåll med
			// början från där denna nyhet slutar. Adderar ett eftersom
			// </item> annars ligger kvar i den nya strängen vilket gör
			// att samma nyhet (title) även den finns kvar i strängen.
			tmp = tmp.substring(index + 1);
		}

		return sound.toString();
    }
    
    public String getSoundAcast() {
        StringBuffer sound = new StringBuffer();

		// Varje nyhet ligger i separata <item>-taggar. Vi skapar därför
		// en ny delsträng av content med början vid första taggen <item>
		String tmp = content.substring(content.indexOf("<item>"));

		String link;
		while ((link = getTextBetween(tmp, "<enclosure url=\"", "\" length")) != "") {
			// Titeln för en nyhet ligger mellan taggarna <title>. Så länge som
			// ovan returnerar något annat än en tom sträng finns fler nyheter
                        
                        if (link.contains(".mp3")) {                        
			sound.append((link) + "<br>");
                        }
			// Tar reda på var denna nyhet (item) slutar
			int index = tmp.indexOf("</item>");

			// Skapar en delsträng av kvarvarande innehåll med
			// början från där denna nyhet slutar. Adderar ett eftersom
			// </item> annars ligger kvar i den nya strängen vilket gör
			// att samma nyhet (title) även den finns kvar i strängen.
			tmp = tmp.substring(index + 1);
		}
                
                
		return sound.toString();
    }
    
    public String getNews() {
		StringBuffer news = new StringBuffer();

		// Varje nyhet ligger i separata <item>-taggar. Vi skapar därför
		// en ny delsträng av content med början vid första taggen <item>
		String tmp = content.substring(content.indexOf("<item>"));

		String title;
		while ((title = getTextBetween(tmp, "<title>", "</title>")) != "") {
			// Titeln för en nyhet ligger mellan taggarna <title>. Så länge som
			// ovan returnerar något annat än en tom sträng finns fler nyheter

			news.append(removeCDATA(title) + "<br>");

			// Tar reda på var denna nyhet (item) slutar
			int index = tmp.indexOf("</item>");

			// Skapar en delsträng av kvarvarande innehåll med
			// början från där denna nyhet slutar. Adderar ett eftersom
			// </item> annars ligger kvar i den nya strängen vilket gör
			// att samma nyhet (title) även den finns kvar i strängen.
			tmp = tmp.substring(index + 1);
		}
                String formatted = news.toString();
                formatted = formatted.replaceAll("&quot;", "");
                formatted = formatted.replaceAll("&apos;", "");
                formatted = formatted.replaceAll("\"", "");
                formatted = formatted.replace("?", "");
		return formatted;
	}
    
    private String removeCDATA(String text) {
		// Vissa RSS-flöden lägger sin text i CDATA-sektioner.
		// Vi kontrollerar därför om så är fallet för denna

		String cdata = getTextBetween(text, "CDATA[", "]");
		return cdata.length() > 0 ? cdata : text;
	}
    
    private String getTextBetween(String text, String start, String end) {
        int startindex = text.indexOf(start);
        int endindex = text.indexOf(end, startindex);
        
        if (startindex < 0 || endindex < 0 || endindex < startindex) {
            return "";
        }
        
        return text.substring(startindex + start.length(), endindex);
    }
}
