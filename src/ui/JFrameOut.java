package ui;

import agents.VisualizationAgent;
import jugadores.Jugador;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;

import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JPanel;

import static jugadores.Jugador.jugadoresToString;

public class JFrameOut extends JFrame{

    private static final long serialVersionUID= 1L;

    private final JPanel panel;
    private final VisualizationAgent agent;
    private final JTextArea register = new JTextArea();

    public JFrameOut(final VisualizationAgent agent) {

        this.panel= new JPanel();
        this.agent = agent;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100,100,550,550);

        this.panel.setBorder(new EmptyBorder(3,3,3,3));
        setContentPane(this.panel);
        this.panel.setLayout(null);

        final JLabel newlabel= new JLabel("Lista de jugadores con salarios actualizados: ");
        newlabel.setFont(new Font("Arial", Font.BOLD, 12));
        newlabel.setBounds(50,15,460,30);
        this.panel.add(newlabel);

        //register.setEnabled(false); // no descomentar o no se vera  en negro sino en azul raro
        register.setEditable(false);
        register.setForeground(Color.BLACK);
        register.setBounds(25,50,500,500);

        this.panel.add(register);
    }

    public void setText(final ArrayList<Jugador> jugadores) {

        final String result = jugadoresToString(jugadores);
        this.register.setText(result);
    }
}
