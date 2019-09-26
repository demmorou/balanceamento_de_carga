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
            output = output.replace("[", "");
            output = output.replace("]", "");
            System.out.println(output);
            
            FileOutputStream imageOutFile = new FileOutputStream("../imagem.png");
            
            byte[] imageByteArray = Base64.getDecoder().decode(output);
            
            imageOutFile.write(imageByteArray);
            Image picture = ImageIO.read(new File("../imagem.png"));
            BufferedImage ppp = (BufferedImage) picture;
            BufferedImage newImage = new BufferedImage( picture.getHeight(null), picture.getWidth(null), ppp.getType() );;
            for( int i=0 ; i < picture.getWidth(null); i++ )
                for( int j=0 ; j < picture.getHeight(null); j++ )
                    newImage.setRGB( picture.getHeight(null)-1-j, i, ppp.getRGB(i,j));
            FileOutputStream saida = new FileOutputStream("../imagem_rodada.png");
            
            
            ImageIO.write(newImage, "png", saida);
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(newImage, "png", baos);
            String encoded = Base64.getEncoder().encodeToString(baos.toByteArray());
            System.out.println(encoded);
            //
            
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
