package ui;

import agents.DataAgent;

import javax.swing.JFrame;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JFramePrincipal extends JFrame{

    private JFrame frame;
    public JTextArea area;

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            try {
                JFramePrincipal window = new JFramePrincipal();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public JFramePrincipal() {
    }

    public JFramePrincipal(DataAgent a) {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 694, 533);
        getContentPane().setLayout(null);

        JTextArea textArea = new JTextArea();
        textArea.setBounds(61, 53, 190, 55);
        getContentPane().add(textArea);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
//Enviamos el texto que ha introducido el Cliente
                String texto = textArea.getText();
                a.texto = texto;
                a.doWake();
            }
        });
        btnBuscar.setBounds(391, 49, 115, 29);
        getContentPane().add(btnBuscar);
        JLabel lblIntroduzcaTextoA = new JLabel("Introduzca texto a buscar");
        lblIntroduzcaTextoA.setBounds(61, 16, 232, 20);
        getContentPane().add(lblIntroduzcaTextoA);
        JTextArea textArea_1 = new JTextArea();
        textArea_1.setBounds(61, 192, 561, 238);
        getContentPane().add(textArea_1);
        area = textArea_1;
        JLabel lblResultadoBsqueda = new JLabel("Resultado b\u00FAsqueda");
        lblResultadoBsqueda.setBounds(61, 155, 200, 20);
        getContentPane().add(lblResultadoBsqueda);
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);
        JMenu mnEdit = new JMenu("Edit");
        menuBar.add(mnEdit);
    }
    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public JTextArea getArea() {
        return area;
    }
}

