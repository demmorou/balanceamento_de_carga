/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middle;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
    
    public ServerRest(String ipPort) {
        this.IPPort = ipPort;
        this.ping = null;
        this.latencia = null;
        this.cpuUse = null;
    }

//    public static void main(String[] args) {
//        
//    }
    
    public boolean Teste() {
        try {
            System.out.println("Testando conexão "+this.IPPort);
            String URL_MEDIA = "http://"+this.IPPort+"/DetectFace/webresources/generic/GetStatus";
            long start = System.currentTimeMillis();
            URL obj = new URL(URL_MEDIA);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
//            int responseCode = con.getResponseCode();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
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
            System.out.println("Testando conexão "+this.IPPort);
            String URL_MEDIA = "http://"+this.IPPort+"/DetectFace/webresources/generic/Rotacionar"+coder;
            long start = System.currentTimeMillis();
            URL obj = new URL(URL_MEDIA);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
//            int responseCode = con.getResponseCode();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (Exception e) {
            return coder;
        }
    }
    
    public String toString() {
        return "Host: "+this.IPPort+", Score: "+this.score+" {Ping: "+this.ping+", Latencia: "+this.latencia+", CPU: "+this.cpuUse+"}\n";
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
