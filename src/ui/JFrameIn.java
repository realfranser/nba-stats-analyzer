package ui;

import agents.ExecutorAgent;
import com.google.gson.Gson;
import jade.gui.GuiEvent;
import jugadores.Jugador;
import validators.NameValidator;
import validators.SalaryValidator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class JFrameIn extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;

    private ExecutorAgent myAgent;

    private JTextField nombreJugador;

    private JTextField salario;

    private JButton darSalario;
    private JButton listaJugadores;

    private Jugador nuevoJugador;

    public JFrameIn(ExecutorAgent a) {
        myAgent = a;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setBounds(100, 100, 550, 300);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);

        contentPane.setLayout(null);

        JLabel lblnombre = new JLabel("Inserte el nombre del jugador sobre el que modificar el salario: ");

        lblnombre.setFont(new Font("Arial", Font.BOLD, 13));
        lblnombre.setBounds(60, 20, 450, 50);
        contentPane.add(lblnombre);

        nombreJugador = new JTextField();
        nombreJugador.setBounds(200, 85, 90, 20);
        contentPane.add(nombreJugador);
        nombreJugador.setColumns(10);

        JLabel lblSalario = new JLabel("Escriba el salario del jugador: ");
        lblSalario.setFont(new Font("Arial", Font.BOLD, 13));
        lblSalario.setBounds(50, 120, 440, 30);
        contentPane.add(lblSalario);

        salario= new JTextField();
        salario.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if ((int) e.getKeyChar() < 46 || (int) e.getKeyChar() > 57) {
                    e.consume();
                }
            }
        });
        salario.setBounds(50, 170, 70, 20);
        contentPane.add(salario);
        salario.setColumns(10);

        darSalario= new JButton("Nuevo Salario ");
        darSalario.addActionListener(this);
        darSalario.setFont(new Font("Arial", Font.BOLD, 12));
        darSalario.setBounds(90, 218, 130, 23);

        contentPane.add(darSalario);

        listaJugadores= new JButton("Visualizar lista de jugadores");
        listaJugadores.addActionListener(this);
        listaJugadores.setFont(new Font("Arial", Font.BOLD, 12));
        listaJugadores.setBounds(220, 218, 200, 23);

        contentPane.add(listaJugadores);

        nombreJugador.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent AE) {
        final String nombre = this.nombreJugador.getText();

        if(AE.getSource() == listaJugadores){
            final GuiEvent GE = new GuiEvent(this, 2);
            this.myAgent.postGuiEvent(GE);
            return;
        }

        if (AE.getSource() != darSalario) { return; }

        if (!NameValidator.isValidName(nombre)) {
            this.nombreJugador.setBackground(Color.WHITE);
            salario.setBackground(Color.WHITE);
            JOptionPane.showMessageDialog(contentPane, NameValidator.NAME_ERROR, NameValidator.NAME_ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
            this.nombreJugador.setBackground(new Color(255,255,0));
            this.nombreJugador.requestFocus();
        }

        if (!SalaryValidator.isValidSalary(Float.parseFloat(salario.getText()))) {
            this.nombreJugador.setBackground(Color.WHITE);
            this.salario.setBackground(Color.WHITE);
            JOptionPane.showMessageDialog(contentPane, SalaryValidator.SALARY_VALIDATOR_ERROR, SalaryValidator.SALARY_ERROR_TYPE, JOptionPane.ERROR_MESSAGE);
            this.nombreJugador.setBackground(Color.BLUE);
            this.nombreJugador.requestFocus();
        }

        ArrayList<Jugador> listaJugadores;

        try {
            listaJugadores = leemosJugadores();

            boolean jugadorEncontrado = false;
            for (Jugador jugador : listaJugadores) {

                if (jugador.getNombre().equals(nombre)) {
                    jugador.setSalario(Float.parseFloat(this.salario.getText()));
                    this.nuevoJugador = jugador;
                    jugadorEncontrado = true;

                    JOptionPane.showMessageDialog(contentPane, "El nombre se ha insertado con exito.", "OK", 1);
                    GuiEvent GE= new GuiEvent(this, 1);
                    myAgent.postGuiEvent(GE);

                    break;
                }
            }

            if (!jugadorEncontrado) {
                nombreJugador.setBackground(Color.WHITE);
                salario.setBackground(Color.WHITE);
                JOptionPane.showMessageDialog(contentPane, "El jugador no existe.", "ERROR", 0);
                nombreJugador.setBackground(new Color(255,255,0));
                nombreJugador.requestFocus();
            }

            resetInputValues();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resetInputValues() {

    }

    public Jugador getNuevoJugador() {
        return this.nuevoJugador;
    }

    public JTextField getTextJugador() {
        return nombreJugador;
    }

    public void setTextJugador(JTextField codigoBarras) {
        this.nombreJugador= codigoBarras;
    }

    public JTextField getTextSalario() {
        return salario;
    }

    public void setTextSalario(JTextField calificacion) {
        this.salario= calificacion;
    }

    public ArrayList<Jugador> leemosJugadores() throws FileNotFoundException, IOException {
        ArrayList<Jugador> jugadores;
        FileReader jugadoresFile = new FileReader("jugadores.json");
        java.lang.reflect.Type mapTokenType = new com.google.gson.reflect.TypeToken<ArrayList<Jugador>>(){}.getType();
        jugadores= new Gson().fromJson(jugadoresFile, mapTokenType);
        jugadoresFile.close();
        return jugadores;
    }
}
