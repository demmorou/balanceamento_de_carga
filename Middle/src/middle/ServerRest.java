/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middle;

import com.google.gson.Gson;
import java.awt.PageAttributes;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import static java.net.Proxy.Type.HTTP;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javafx.scene.media.Media;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import sun.net.www.http.HttpClient;
//import static middle.Convert.compress;


/**
 *
 * @author heliojunior
 */
public class ServerRest {
    private String IPPort;
    private String ping;
    private String latencia;
    private String cpuUse;
    private String score;
    private String URL;
    
    public ServerRest(String ipPort) {
        this.IPPort = ipPort;
        URL = "http://"+this.IPPort+"/Server/webresources/imagem/";
        this.ping = null;
        this.latencia = null;
        this.cpuUse = null;
    }

//    public static void main(String[] args) {
//        
//    }
    
    public boolean Teste() {
        try {
//            System.out.println("Testando conexão "+this.IPPort);
            String URL_MEDIA = URL+"GetStatus";
            long start = System.currentTimeMillis();
            URL obj = new URL(URL_MEDIA);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            
            in.close();
            
            this.latencia = (System.currentTimeMillis()-start)+"";
            this.ping = "Online";
            this.cpuUse = response.toString();
            this.score = (Double.valueOf(this.cpuUse)*10+Double.valueOf(this.latencia))+"";
            
            return true;
        } catch (Exception e) {
            this.score = null;
            this.ping = "Offline";
            return false;
        }
    }
    
    public String rotacionar(String coder) {
        try {
            String URL_MEDIA = this.URL+"/rotate";
            
            coder = coder.replace("[", "");
            coder = coder.replace("]", "");
            System.out.println("--------------- Coder ---------------");
            System.out.println(coder);
            System.out.println("------------------------------------");
            
            Gson ser = new Gson();
            
            Imagem image = new Imagem();
            image.setCodigo(coder);

            DefaultHttpClient client = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
            HttpResponse resp = null;
            InputStream in = null;
            Gson json = new Gson();
            System.out.println(json.toJson(image));
            String recebido = "";
           try {
               HttpPost post = new HttpPost(URL_MEDIA);
               StringEntity se = new StringEntity(json.toJson(image));  
               se.setContentType(new BasicHeader("Content-Type", "application/json"));
               post.setEntity(se);
               resp = client.execute(post);
               
               int data = resp.getEntity().getContent().read();
               while (data != -1) {
                   recebido += (char) data;
                   data = resp.getEntity().getContent().read();
               }

               if(resp!=null){
                   in = resp.getEntity().getContent();//Get the data in the entity
               }
               
           } catch(Exception e) {
               e.printStackTrace();
           }
            return recebido;
        } catch (Exception e) {
            System.err.println("Erro em rotacionar");
            System.err.println(e.getMessage());
            return coder;
        }
    }
    
    
    
    public String toString() {
        return "Host: "+this.IPPort+", Score: "+this.score+" {Ping: "+this.ping+", Latencia: "+this.latencia+", CPU: "+this.cpuUse+"}";
    }
    
    public String getPing() {
        return ping;
    }

    public void setPing(String ping) {
        this.ping = ping;
    }

    public String getLatencia() {
        return latencia;
    }

    public void setLatencia(String latencia) {
        this.latencia = latencia;
    }

    public String getCpuUse() {
        return cpuUse;
    }

    public void setCpuUse(String cpuUse) {
        this.cpuUse = cpuUse;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
    
}
