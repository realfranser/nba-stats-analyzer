package behaviour;

import agents.VisualizationAgent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jugadores.Jugador;

import java.io.IOException;
import java.util.ArrayList;

public class CyclicVisualizationMessageReceiver extends CyclicBehaviour {

    private static final long serialVersionUID = 1L;
    private ArrayList<Jugador> listaJugadores;

    public CyclicVisualizationMessageReceiver(VisualizationAgent agent) {
        super(agent);
    }

    public void action() {
        ACLMessage message = this.myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));

        System.out.println("Se recibio mostrar: Mostrando...");

        if (message != null) {
            try {
                this.listaJugadores = (ArrayList<Jugador>) message.getContentObject();
                ((VisualizationAgent) this.myAgent).getChatJFrame().setText(this.listaJugadores);
                ((VisualizationAgent) this.myAgent).setListaJugadores(this.listaJugadores);
            } catch (UnreadableException e) {
                e.printStackTrace();
            }
        } else {
            this.block();
        }
    }
}
