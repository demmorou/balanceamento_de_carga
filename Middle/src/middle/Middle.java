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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author heliojunior
 */
public class Middle {

    /**
     * @param args the command line arguments
     */
    private static List<ServerRest> servers = new ArrayList<>();
    private static ServerRest melhor;
    
    public static void main(String[] args) throws InterruptedException{
        // TODO code application logic here
        
        servers.add(new ServerRest("localhost:12345"));
//        servers.add(new ServerRest("localhost:8081"));

        while (true) {
            for (ServerRest server : servers) {
                server.Teste();
            }

            melhorServer();
            System.out.println("Melhor server: "+melhor);
            
            Thread.sleep(5000);
        }
    }
    
    public static void melhorServer() {
        for (ServerRest server : servers) {
            if (server.getPing().equals("Online")) {
                if (melhor == null) {
                    melhor = server;
                } else {
                    if (Double.valueOf(melhor.getScore()) > Double.valueOf(server.getScore())) {
                        melhor = server;
                    }
                }
            }
        }
    }
}
