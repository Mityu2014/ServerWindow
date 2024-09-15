import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ServerWindow extends JFrame {
    private static final int POS_X = 500;
    private static final int POS_Y = 550;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private final JPanel panelTop = new JPanel(new BorderLayout());
    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JButton btnStart = new JButton("Start");
    private final JButton btnStor = new JButton("Stop");
    private final JTextArea log = new JTextArea();
    private boolean inServerWorking;

    List<ClientGUI> clients = new ArrayList<ClientGUI>();

    ServerWindow() {
        inServerWorking = false;
        btnStor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inServerWorking = false;
                System.out.println("Сервер запущен!\n");
            }
        });

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inServerWorking = true;
                log.append("Сервер запущен!\n");
            }
        });
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat server");
        setAlwaysOnTop(true);

        panelTop.add(log);
        add(panelTop, BorderLayout.NORTH);

        panelBottom.add(btnStart, BorderLayout.WEST);
        panelBottom.add(btnStor, BorderLayout.EAST);
        add(panelBottom, BorderLayout.SOUTH);

        log.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(log);
        add(scrollPane);

        setVisible(true);
    }

    public Boolean getServer(ClientGUI clientGUI) {
        if (inServerWorking) {
            clients.add(clientGUI);
            log.append(clientGUI.tfLogin.getText() + " подключился к беседе\n");
        }
        return inServerWorking;
    }

    public void message(String s) {
        log.append(s + "\n");
        try (FileWriter writer = new FileWriter("log.txt", true)) {
            writer.write(s + "\n");
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        for (ClientGUI elem : clients) {
            elem.messages(s);
        }
    }

    public static String reader () throws IOException {
        return new String(Files.readAllBytes(Paths.get("log.txt")));
    }
}
