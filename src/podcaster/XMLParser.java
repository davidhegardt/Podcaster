/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podcaster;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Dave
 */
public class XMLParser {
    private URL url;
    private ArrayList<String> titleList;
    private ArrayList<String> descList;
    private ArrayList<String> imgArray;
    private ArrayList<String> mp3Correct;
    private String title;
    private String welcomeText;
    private String startImg;
    
    public XMLParser(URL currURL) {
        this.url = currURL;
    }
    
    public ArrayList<String> getimgArray() {
        return imgArray;
    }
    
    public ArrayList<String> getMP3Array() {
        return mp3Correct;
    }
    
    public ArrayList<String> getDescArray() {
        return descList;
    }
    
    public ArrayList<String> getTitleArray() {
        return titleList;
    }
    
    public String getWelcomeText() {
        return welcomeText;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getStartImage() {
        return startImg;
    }
    
    
    public void parseXML() throws IOException {
        //URLConnection uc = url.openConnection();
        String urlString = url.toString();
        //File inputFile = new File((String) uc.getContent());
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        
        //SAXBuilder sax = new SAXBuilder();        
        //Document doc = sax.build(inputFile);
        
        //System.out.println("root : " + doc.getRootElement().getName());        
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new URL(urlString).openStream());
            doc.getDocumentElement().normalize();
            System.out.println("Root : " + doc.getDocumentElement().getNodeName());
            
            NodeList titleNodes = doc.getElementsByTagName("title");
            NodeList linkNodes = doc.getElementsByTagName("link");
            NodeList imgNodes = doc.getElementsByTagName("itunes:image");
            NodeList descNodes = doc.getElementsByTagName("itunes:summary");
            
            titleList = new ArrayList<String>();
            
            for (int temp = 0; temp < titleNodes.getLength(); temp++) {
                Node nNode = titleNodes.item(temp);
                //System.out.println(nNode.getNodeName());
                System.out.println(nNode.getTextContent());
                String tempOut = nNode.getTextContent();
                if (tempOut.contains("\"")) {
                    tempOut = tempOut.replace("\"", "");
                }
                titleList.add(tempOut);
            }
            title = titleList.get(0);
            titleList.remove(0);
            
            descList = new ArrayList<String>();
            
            for (int z = 0; z < descNodes.getLength(); z++) {
                Node descNode = descNodes.item(z);
                
                System.out.println(descNode.getTextContent());
                System.out.println("\n");
                String tempOut = descNode.getTextContent();
                descList.add(tempOut);
            }
            welcomeText = descList.get(0);
            
            descList.remove(0);
            
            imgArray = new ArrayList<String>();
            
            for (int x = 0; x < imgNodes.getLength(); x++) {
                Node imgNode = imgNodes.item(x);
                Element eElement = (Element) imgNode;
                System.out.println(eElement.getAttribute("href"));
                String tempOut = eElement.getAttribute("href");
                imgArray.add(tempOut);
            }
            startImg = imgArray.get(0);
            imgArray.remove(0);
            
            String[] mp3Array = new String[linkNodes.getLength()];
            
            for (int index = 0; index < linkNodes.getLength(); index++) {
                Node linkNode = linkNodes.item(index);
                
                System.out.println(index + " " + linkNode.getTextContent());
                String tempOut = linkNode.getTextContent();
                //String result = getTextBetween(tempOut);
                mp3Array[index] = tempOut;
                //System.out.println(tempOut);
            }
            
            mp3Correct = new ArrayList<String>();
            
            for (int i = 0; i < mp3Array.length; i++) {
                String newLink = getTextBetween(mp3Array[i]);
                System.out.println(i + " " + newLink);
                mp3Correct.add(newLink);
            }
            
            mp3Correct.remove(0);
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(XMLParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void parseXMLSR() throws IOException {
        //URLConnection uc = url.openConnection();
        String urlString = url.toString();
        //File inputFile = new File((String) uc.getContent());
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        
        //SAXBuilder sax = new SAXBuilder();        
        //Document doc = sax.build(inputFile);
        
        //System.out.println("root : " + doc.getRootElement().getName());        
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new URL(urlString).openStream());
            doc.getDocumentElement().normalize();
            System.out.println("Root : " + doc.getDocumentElement().getNodeName());
            
            NodeList titleNodes = doc.getElementsByTagName("title");
            NodeList linkNodes = doc.getElementsByTagName("enclosure");
            NodeList imgNodes = doc.getElementsByTagName("itunes:image");
            NodeList descNodes = doc.getElementsByTagName("itunes:summary");
            
            titleList = new ArrayList<String>();
            
            for (int temp = 0; temp < titleNodes.getLength(); temp++) {
                Node nNode = titleNodes.item(temp);
                //System.out.println(nNode.getNodeName());
                System.out.println(nNode.getTextContent());
                String tempOut = nNode.getTextContent();
                if (tempOut.contains("\"")) {
                    tempOut = tempOut.replace("\"", "");
                }
                titleList.add(tempOut);
            }
            title = titleList.get(0);
            titleList.remove(0);
            titleList.remove(1);
            
            descList = new ArrayList<String>();
            
            for (int z = 0; z < descNodes.getLength(); z++) {
                Node descNode = descNodes.item(z);
                
                System.out.println(descNode.getTextContent());
                System.out.println("\n");
                String tempOut = descNode.getTextContent();
                descList.add(tempOut);
            }
            welcomeText = descList.get(0);
            
            descList.remove(0);
            
            imgArray = new ArrayList<String>();
            
            for (int x = 0; x < imgNodes.getLength(); x++) {
                Node imgNode = imgNodes.item(x);
                Element eElement = (Element) imgNode;                
                String tempOut = eElement.getAttribute("href");
                tempOut = getTextBetween(tempOut);
                System.out.println(tempOut);
                imgArray.add(tempOut);
            }
            startImg = imgArray.get(0);
            imgArray.remove(0);
            
            
            String[] mp3Array = new String[linkNodes.getLength()];
            
            for (int index = 0; index < linkNodes.getLength(); index++) {
                Node linkNode = linkNodes.item(index);
                Element eElement = (Element) linkNode;
                String tempOut = eElement.getAttribute("url");
                //String result = getTextBetween(tempOut);
                mp3Array[index] = tempOut;
                System.out.println(tempOut);
            }
            
            mp3Correct = new ArrayList<String>();
            
            for (int i = 0; i < mp3Array.length; i++) {
                String newLink = mp3Array[i];
                System.out.println(i + " " + newLink);
                mp3Correct.add(newLink);
            }
            
            //mp3Correct.remove(0);
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(XMLParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private String getTextBetween(String text) {
        if (text.contains("?")) {
        String result = text.substring(0, text.indexOf("?"));
        
        return result;
        } else return text;
    }
}
