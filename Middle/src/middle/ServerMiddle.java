/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middle;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import static middle.Middle.melhorServer;

/**
 *
 * @author deusimar
 */
public class ServerMiddle {
    private static List<ServerRest> servers = new ArrayList<>();
    private static ServerRest melhor;
    
    public static void main(String[] args) {
        try {
            ServerSocket servidor = new ServerSocket(12345);
            System.out.println("Servidor ativo");
            
            servers.add(new ServerRest("localhost:12345"));
            
            Thread consultas = new Thread (() -> {
                while (true) {
                for (ServerRest server : servers) {
                    server.Teste();
                }

                melhorServer();
                System.out.println("Melhor server: "+melhor);
                    try {
                        Thread.sleep(5000);
                    } catch (Exception e) {
                    }
                }
            });
            
            consultas.start();
            
            while (true) {                
                Socket cliente = servidor.accept();
                Clients c = new Clients(cliente, melhor);
                
                Thread t = new Thread(c);
                t.start();
            }
        } catch (Exception e) {
            System.err.println("error");
            System.out.println(e.getMessage());
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
