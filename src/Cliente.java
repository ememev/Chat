import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Cliente implements Runnable {
    private int PORT;
    private String mensaje;

    public Cliente(int PORT, String mensaje){
        this.PORT = PORT;
        this.mensaje = mensaje;
    }

    @Override
    public void run() {
        String HOST = "192.168.100.9";
        DataInputStream in ;
        DataOutputStream out;

        try {
            Socket sServidor = new Socket(HOST,PORT);
            System.out.println("Servidor conectado");
            out = new DataOutputStream(sServidor.getOutputStream());
            out.writeUTF(mensaje);
            sServidor.close();
            System.out.println("Servidor desconectado");

        } catch (IOException e) {
        }

    }

}
