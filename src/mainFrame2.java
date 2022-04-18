import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;


public class mainFrame2 extends JFrame implements Observer {
    private JButton btnToSend;
    private JTextArea txtToShow;
    private JTextField txtToSend;
    private JPanel mainPane;

    public mainFrame2(){
        setContentPane(mainPane);
        setTitle("Chat");
        setSize(450,200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        Servidor servidor = new Servidor(8000);
        Thread hiloServ = new Thread(servidor);
        servidor.addObserver(this);
        hiloServ.start();


        btnToSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtToShow.append(txtToSend.getText()+"\n");
                Cliente cliente = new Cliente(6000,txtToSend.getText());
                Thread hiloCli = new Thread(cliente);
                hiloCli.start();
                txtToSend.setText("");
            }
        });
    }

    public static void main(String[] args) {
        mainFrame2 myFrame = new mainFrame2();
    }



    @Override
    public void update(Observable o, Object arg) {
        txtToShow.append((String) arg+ "\n");
    }
}

