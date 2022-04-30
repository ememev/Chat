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


public class mainFrame2 extends JFrame implements Observer {
    private JButton btnToSend;
    private JTextArea txtToShow;
    private JTextField txtToSend;
    private JPanel mainPane;
    private JLabel immg;
    private JButton btnImagen;



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
        btnImagen.addActionListener(new ActionListener() {
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
        mainFrame2 myFrame = new mainFrame2();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof BufferedImage){
            BufferedImage imag = (BufferedImage) arg;
            Image image = imag.getScaledInstance(70,70,java.awt.Image.SCALE_SMOOTH);
            immg.setIcon(new ImageIcon( image));

        }else {
            txtToShow.append((String) arg + "\n");
        }
    }
}

