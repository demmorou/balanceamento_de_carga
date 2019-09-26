/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middle;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author heliojunior
 */
public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket servidor = new ServerSocket(12345);
            System.out.println("Servidor ativo");
            while (true) {                
                Socket cliente = servidor.accept();
                Clients c = new Clients(cliente);
                
                Thread t = new Thread(c);
                t.start();
            }
        } catch (Exception e) {
            System.err.println("error");
        }
    }
}
