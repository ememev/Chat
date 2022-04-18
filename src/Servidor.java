import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;

public class Servidor extends Observable implements Runnable {
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
            DataOutputStream out;
            System.out.println("Servidor inciado");

            while (true){
                scliente = servidor.accept();//Escucha para encontrar un cliente que quiera c onectarse, retorna el cocket que se conectó
                System.out.println("cliente desconectado");
                in = new DataInputStream(scliente.getInputStream());//Leer más sobre estas dos clases de input y output Stream
                msnRecibir = in.readUTF();
                this.setChanged();
                this.notifyObservers(msnRecibir);
                this.clearChanged();

                scliente.close();//Cierra el cliente
                System.out.println("cliente desconectado");
            }
        } catch (IOException e) {
        }
    }
}
