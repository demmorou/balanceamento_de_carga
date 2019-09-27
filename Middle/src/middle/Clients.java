/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middle;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;
import javax.imageio.ImageIO;
import sun.misc.BASE64Decoder;

/**
 *
 * @author heliojunior
 */
public class Clients implements Runnable {

    private Socket cliente;
    private ServerRest servidor;

    public Clients(Socket cliente, ServerRest servidor) {
        this.cliente = cliente;
        this.servidor = servidor;
    }

    @Override
    public void run() {
        try {
            System.out.println("Cliente: " + this.cliente.getInetAddress().getHostAddress());

            BufferedReader br = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            String output = br.readLine();
            
            //
            System.out.println("-------------------- Recebendo do celular --------------------");
            System.out.println(output);
            String encoded = servidor.rotacionar(output);
            System.out.println("----------------------------------------------");
            //
            
            System.out.println("---------------------- Enviando para o celular --------------------");
            System.out.println(encoded);
            System.out.println("-----------------------------------------------------------------");
            
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(cliente.getOutputStream()));
            bw.write(encoded);
            bw.newLine();
            bw.flush();
            br.close();
            this.cliente.close();
        } catch (Exception e) {
            System.err.println("Erro!");
            System.out.println(e.getMessage());
        }
    }
}
