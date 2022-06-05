package behaviour;

import agents.DataAgent;
import agents.VisualizationAgent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jugadores.Jugador;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class CyclicVisualizationMessageReceiver extends CyclicBehaviourReceiveMessage {

    private static final long serialVersionUID = 1L;
    private ArrayList<Jugador> listaJugadores;

    public void action() {
        ACLMessage message = this.myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));

        if (message != null) {
            try {
                message.setContentObject(this.listaJugadores);
                this.listaJugadores = (ArrayList<Jugador>) message.getContentObject();
                ((VisualizationAgent) this.myAgent).getChatJFrame().setText(this.listaJugadores);
                ((VisualizationAgent) this.myAgent).setListaJugadores(this.listaJugadores);
            } catch (IOException | UnreadableException e) {
                e.printStackTrace();
            }
        } else {
            this.block();
        }
    }
}
