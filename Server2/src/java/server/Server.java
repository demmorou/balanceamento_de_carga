/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.Base64;
import javax.imageio.ImageIO;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author deusimar
 */
@Path("server")
public class Server {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Server
     */
    public Server() {
    }

    /**
     * Retrieves representation of an instance of server.Server
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("GetStatus")
    public String getOtherJson() {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        double s = (double) osBean.getSystemLoadAverage();
        return String.valueOf(s);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("Rotacionar/{valor}")
    public String rotacionar(@PathParam("valor") String output) {
        try {
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
            return encoded;
        } catch (Exception e) {
            System.err.println("Erro..");
            return output;
        } 
    }

    /**
     * PUT method for updating or creating an instance of Server
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }
}
