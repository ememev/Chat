import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class mainFrame extends JFrame implements Observer {
    private JTextArea txtShowMsn;
    private JButton btnEnviar;
    private JPanel mainPane;
    private JTextField txtSendMsn;

    public mainFrame(){
        setContentPane(mainPane);
        setTitle("Chat");
        setSize(450,200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        Servidor servidor = new Servidor(6000);
        Thread hiloServ = new Thread(servidor);
        servidor.addObserver(this);
        hiloServ.start();


        btnEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtShowMsn.append(txtSendMsn.getText()+ "\n");
                Cliente cliente = new Cliente(8000,txtSendMsn.getText());
                Thread hiloCli = new Thread(cliente);
                hiloCli.start();
                txtSendMsn.setText("");
            }
        });
    }

    public static void main(String[] args) {
        mainFrame myFrame = new mainFrame();
    }

    @Override
    public void update(Observable o, Object arg) {
        txtShowMsn.append((String) arg+"\n");

    }
}
