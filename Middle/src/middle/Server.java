/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middle;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

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
//                Clients c = new Clients(cliente);
                
//                Thread t = new Thread(c);
//                t.start();
            }
        } catch (Exception e) {
            System.err.println("error");
        }
    }
}
