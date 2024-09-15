import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;

public class ClientGUI extends JFrame {
    private  static final int WIDTH = 400;
    private  static final int HEIGHT = 300;
    private final JTextArea log = new JTextArea();
    private final JPanel panelTop = new JPanel(new GridLayout(2,3));
    private final JTextField  tfIPAddress = new JTextField("127.0.0.1");
    private final JTextField  tfPort = new JTextField("8189");
    public final JTextField  tfLogin = new JTextField("ivan");
    private final JPasswordField tfPassword = new JPasswordField("123456");
    private final JButton btnLogin = new JButton("Login");
    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSent = new JButton("Sent");

    ClientGUI(ServerWindow serverWindow){
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (serverWindow.getServer(ClientGUI.this)){
                    panelTop.setVisible(false);
                    log.append("Вы успешно подключились!\n" + "\n");
                    try {
                        log.append(serverWindow.reader());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }else{
                    log.append("Подключение не удалось\n");
                }
            }
        });
                btnSent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverWindow.message(tfLogin.getText() + ": " + tfMessage.getText());
                tfMessage.setText("");
            }
        });

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(WIDTH,HEIGHT);
        setTitle("Chat Client");

        panelTop.add(tfIPAddress);
        panelTop.add(tfPort);
        panelTop.add(tfLogin);
        panelTop.add(tfPassword);
        panelTop.add(btnLogin);
        add(panelTop, BorderLayout.NORTH);

        panelBottom.add(tfMessage,BorderLayout.CENTER);
        panelBottom.add(btnSent, BorderLayout.EAST);
        add(panelBottom, BorderLayout.SOUTH);

        log.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(log);
        add(scrollPane);

        setVisible(true);
    }
    public void messages(String s){
        log.append(s + "\n");
    }

}
