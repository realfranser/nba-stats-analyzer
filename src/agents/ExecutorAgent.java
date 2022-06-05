package agents;

import jade.core.Agent;
import jugadores.Jugador;
import ui.*;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;

import jade.gui.GuiAgent;
import jade.gui.GuiEvent;

import jade.lang.acl.ACLMessage;

import java.util.ArrayList;

import java.io.IOException;

/*
Este agente se encarga de realizar calculos para procesar los datos recibidos por el DataAgent
 */
public class ExecutorAgent extends GuiAgent {

    private static final long serialVersionUID = 1L;
    static final int WAIT = -1;
    static final int QUIT = 0;

    private int button = WAIT;

    public JFrameIn myGui;

    protected void setup() {
        myGui = new JFrameIn(this);
        myGui.setVisible(true);
    }


    protected void onGuiEvent(GuiEvent ev) {
        button = ev.getType();

        if (button == 1) {
            final Jugador nuevoJugador = myGui.getNuevoJugador();

            addBehaviour(new OneShotBehaviour() {
                private static final long serialVersionUID = 1L;

                public void action() {
                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

                    try {
                        msg.setContentObject(nuevoJugador);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    msg.addReceiver(new AID("DataAgent",AID.ISLOCALNAME));

                    send(msg);
                }
            });
        } else if (button == 2) {
            System.out.println("reciviendo llamada lista de jugadores");
            addBehaviour(new OneShotBehaviour() {
                private static final long serialVersionUID = 1L;

                public void action() {
                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

                    msg.setContent("Mostrar");
                    msg.addReceiver(new AID("DataAgent",AID.ISLOCALNAME));

                    send(msg);
                    System.out.println("Mostrar");
                }
            });

        }
    }
}
