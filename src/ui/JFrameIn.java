package ui;

import agents.ExecutorAgent;
import com.google.gson.Gson;
import jade.gui.GuiEvent;
import jugadores.Jugador;

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

    //private ArrayList<String> compSame = new ArrayList<String>();

    private ExecutorAgent myAgent;

    //Codigo de barras del producto;
    private JTextField nombreJugador;

    //Calificacion del usuario para producto;
    private JTextField salario;

    private JButton darSalario;
    private JButton listaJugadores;

    public JFrameIn(ExecutorAgent a) {
        myAgent = a;

        //Cerramos las ventanas;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setBounds(100, 100, 550, 300);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);

        contentPane.setLayout(null);

        //Etiqueta codigo de barras;
        JLabel lblnombre = new JLabel("Inserte el nombre del jugador sobre el que modificar el salario: ");

        lblnombre.setFont(new Font("Arial", Font.BOLD, 13));
        lblnombre.setBounds(110, 20, 350, 50);
        contentPane.add(lblnombre);

        nombreJugador = new JTextField();
        nombreJugador.setBounds(200, 85, 90, 20);
        contentPane.add(nombreJugador);
        nombreJugador.setColumns(10);

        //Etiqueta calificacion;
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
        darSalario.setBounds(103, 218, 103, 23);

        contentPane.add(darSalario);

        listaJugadores= new JButton("Visualizar stock ");
        listaJugadores.addActionListener(this);
        listaJugadores.setFont(new Font("Arial", Font.BOLD, 12));
        listaJugadores.setBounds(206, 218, 200, 23);

        contentPane.add(listaJugadores);

        nombreJugador.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent AE) {
        String cod= nombreJugador.getText();
        //char[] compCod= cod.toCharArray();
        boolean fail = false;
        if(AE.getSource()== darSalario) {
            for(int i = 0; i<cod.length() && !fail; i++) {
                char c = cod.charAt(i);
                if(!(c>='a' && c<='z' && c>='A' && c<='Z' && c == ' ')) {					//si el nombre del jugador tiene algo diferente a una letra
                    nombreJugador.setBackground(Color.WHITE);
                    salario.setBackground(Color.WHITE);
                    JOptionPane.showMessageDialog(contentPane, "El nombre del jugador solo debe contener letras.", "ERROR", 0);
                    nombreJugador.setBackground(new Color(255,255,0));
                    nombreJugador.requestFocus();

                    fail = true;
                }
            }
            if(!fail) {
                /*if(compCod.length != 4) {
                    codigoBarras.setBackground(Color.WHITE);
                    calification.setBackground(Color.WHITE);

                    JOptionPane.showMessageDialog(contentPane, "El código de barras debe contener 4 números.", "ERROR", 0);

                    codigoBarras.setBackground(new Color(255,255,0));
                    codigoBarras.requestFocus();
                }*/
                if(Double.parseDouble(salario.getText())< 0 || Double.parseDouble(salario.getText())> 100000000) {
                    nombreJugador.setBackground(Color.WHITE);

                    salario.setBackground(Color.WHITE);

                    JOptionPane.showMessageDialog(contentPane, "Calificacion incorrecta, debe contener solo números del 0 a 100 millones.", "ERROR", 0);

                    nombreJugador.setBackground(Color.BLUE);
                    nombreJugador.requestFocus();
                }
                else {
                    ArrayList<Jugador> Jugadores;
                    try {
                        Jugadores = leemosJugadores();
                        for(Jugador p : Jugadores) {									//comprobamos que el codigo de barras existe

                            if(!(cod.equals(p.getNombre()))) {
                                nombreJugador.setBackground(Color.WHITE);
                                salario.setBackground(Color.WHITE);
                                JOptionPane.showMessageDialog(contentPane, "El jugador no existe.", "ERROR", 0);
                                nombreJugador.setBackground(new Color(255,255,0));
                                nombreJugador.requestFocus();

                                fail = true;
                            }
                        }
                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }

                    JOptionPane.showMessageDialog(contentPane, "El nombre se ha insertado con exito.", "OK", 1);

                    GuiEvent GE= new GuiEvent(this, 1);			//"calificar"
                    myAgent.postGuiEvent(GE);

                    //reseteamos los campos
                    nombreJugador.setText("");
                    nombreJugador.setBackground(Color.WHITE);
                    salario.setText("");
                    salario.setBackground(Color.WHITE);
                }
            }
        }
        else if(AE.getSource()== listaJugadores){
            GuiEvent GE= new GuiEvent(this, 2);			//"stock"
            myAgent.postGuiEvent(GE);
        }
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

    public ArrayList<Jugador> leemosJugadores() throws FileNotFoundException, IOException {			//leemos el stock para comprobar si el codigo de barras existe
        ArrayList<Jugador> jugadores;
        FileReader jugadoresFile = new FileReader("jugadores.json");
        java.lang.reflect.Type mapTokenType = new com.google.gson.reflect.TypeToken<ArrayList<Jugador>>(){}.getType();
        jugadores= new Gson().fromJson(jugadoresFile, mapTokenType);
        jugadoresFile.close();
        return jugadores;
    }
}
