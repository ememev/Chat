import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.concurrent.Flow;

public class Servidor  extends Observable implements Runnable/*, Flow.Publisher*/{
    String msnRecibir;
    private int Puerto;

    public Servidor(int Puerto){
        this.Puerto=Puerto;
    }



    @Override
    public void run() {
        try {
            ServerSocket servidor = new ServerSocket(Puerto);//implementa un objeto de la clase socket y se construye uno en el puerto indicado.
            Socket scliente = null;//Este es el socket que recibiremos del cliente
            DataInputStream in;
            System.out.println("Servidor inciado");

            while (true){
                scliente = servidor.accept();//Escucha para encontrar un cliente que quiera c onectarse, retorna el cocket que se conectó
                System.out.println("cliente desconectado");
                try {
                    in = new DataInputStream(scliente.getInputStream());//Leer más sobre estas dos clases de input y output Stream

                    msnRecibir = in.readUTF();
                    BufferedImage image = ImageIO.read(in);

                    if( image != null){
                        this.setChanged();
                        this.notifyObservers(image);
                        this.clearChanged();
                    }
                    if(msnRecibir!=null && !msnRecibir.equals("Imagen Recibida")){
                        this.setChanged();
                        this.notifyObservers(msnRecibir);
                        this.clearChanged();
                    }


                }catch (IOException e){

                }

                //this.subscribe();

                scliente.close();//Cierra el cliente
                System.out.println("cliente desconectado");
            }
        } catch (IOException e) {
        }
    }
}
