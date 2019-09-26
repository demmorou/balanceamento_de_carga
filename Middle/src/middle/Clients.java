/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middle;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 *
 * @author heliojunior
 */
public class Clients implements Runnable{
    private Socket cliente;

    public Clients(Socket cliente) {
        this.cliente = cliente;
    }
    
    @Override
    public void run() {
//        String message;
//        byte[] message = null;
        try {
//            resposta = new PrintStream(this.cliente.getOutputStream());
            System.out.println("Cliente c: "+this.cliente.getInetAddress().getHostAddress());
        
//            Scanner scanner = new Scanner(this.cliente.getInputStream());
            DataInputStream in = new DataInputStream(cliente.getInputStream());
            in.readByte();
            System.out.println("Recebeu");
            this.cliente.close();
        } catch (Exception e) {
            System.err.println("Erro!");
            System.out.println(e.getMessage());
        }

    }
}
