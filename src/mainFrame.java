import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class mainFrame extends JFrame implements Observer {
    private JTextArea txtShowMsn;
    private JButton btnEnviar;
    private JPanel mainPane;
    private JTextField txtSendMsn;
    private JButton attachImg;
    private JLabel immg;
    private JPanel paneImg;

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
        attachImg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser= new JFileChooser();
                int  res= fileChooser.showOpenDialog(null);
                if(res ==JFileChooser.APPROVE_OPTION){
                    File path = new File(fileChooser.getSelectedFile().getAbsolutePath());
                    BufferedImage imag = null;
                    try {
                        imag = ImageIO.read(path);
                        Image image = imag.getScaledInstance(70,70,java.awt.Image.SCALE_SMOOTH);
                    //paneImg.imageUpdate(image,1,70,70,70,70);
                    immg.setIcon(new ImageIcon( image));

                    Cliente cliente = new Cliente(8000,imag);
                    Thread hiloCli = new Thread(cliente);
                    hiloCli.start();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        mainFrame myFrame = new mainFrame();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof BufferedImage){
            BufferedImage imag = (BufferedImage) arg;
            Image image = imag.getScaledInstance(70,70,java.awt.Image.SCALE_SMOOTH);
            immg.setIcon(new ImageIcon( image));

        }else {
            txtShowMsn.append((String) arg + "\n");
        }

    }
}
