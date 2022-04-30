import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class Cliente implements Runnable {
    private int PORT;
    private String mensaje = null;
    private BufferedImage imag = null;

    public Cliente(int PORT, String mensaje){
        this.PORT = PORT;
        this.mensaje = mensaje;
    }
    public Cliente(int PORT,BufferedImage imag){
        this.PORT = PORT;
        this.imag = imag;
    }

    @Override
    public void run() {
        //String HOST = "169.254.134.73";
        //String HOST = "192.168.1.10";
        //String HOST = "192.168.0.1";
        String HOST = "192.168.100.5";

        DataInputStream in ;
        DataOutputStream out;

        try {
            Socket sServidor = new Socket(HOST, PORT);
            out = new DataOutputStream(sServidor.getOutputStream());


            if(mensaje!=null) {
                System.out.println("Servidor conectado");
                out.writeUTF(mensaje);
                out.writeUTF("");
                //ImageIO.write(imag,"jpg",sServidor.getOutputStream());
                sServidor.close();
                System.out.println("Servidor desconectado");
            }else{
                out.writeUTF("Imagen Recibida");
                System.out.println("Servidor conectado");
                ImageIO.write(imag,"jpg",sServidor.getOutputStream());
                sServidor.close();
                System.out.println("Servidor desconectado");

            }

        } catch (IOException e) {
        }

    }

}
