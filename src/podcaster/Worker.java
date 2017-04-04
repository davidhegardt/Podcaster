/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podcaster;

import java.io.BufferedOutputStream;
import java.io.File;
import static java.lang.Math.toIntExact;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.SwingWorker;

/**
 *
 * @author Dave
 */
/*
public class Worker extends SwingWorker<Void, Void>{
    
    private String file;
    private String title;
    
    public Worker(String currFile, String currTitle) {
        this.file = currFile;
        this.title = title;
    }
    
    @Override
    protected Void doInBackground() throws Exception {
        URLConnection conn = new URL(file).openConnection();
        InputStream is = conn.getInputStream();

        OutputStream outstream = new FileOutputStream(new File(title + ".mp3"));
        byte[] buffer = new byte[4096];
        int len;
        while ((len = is.read(buffer)) > 0) {
            outstream.write(buffer, 0, len);
            setProgress(len);
        }
        outstream.close();
        return null;
        
       }
    }
 */  

class Worker extends SwingWorker<Void, Void> {
   private String site;
   private File file;

   public Worker(String site, String file) {
      this.site = site;      
      this.file = new File(file + ".mp3");
   }

   @Override
   protected Void doInBackground() throws Exception {
      URL url = new URL(site);
      HttpURLConnection connection = (HttpURLConnection) url
            .openConnection();
      long filesize = connection.getContentLength();
      long totalDataRead = 0;
      try (java.io.BufferedInputStream in = new java.io.BufferedInputStream(
            connection.getInputStream())) {
         java.io.FileOutputStream fos = new java.io.FileOutputStream(file);
         try (java.io.BufferedOutputStream bout = new BufferedOutputStream(
               fos, 1024)) {
            byte[] data = new byte[1024];
            int i;
            while ((i = in.read(data, 0, 1024)) >= 0) {
               totalDataRead = totalDataRead + i;
               bout.write(data, 0, i);
               long tmpPercent = (totalDataRead * 100) / filesize;
               int percent = toIntExact(tmpPercent);
               if (percent > 100) { 
                   percent = 99; 
               }
               setProgress(percent);
            }
         }
      }
      return null;
   }
}
