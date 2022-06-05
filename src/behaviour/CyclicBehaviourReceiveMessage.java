package behaviour;

import agents.DataAgent;
import jade.core.AID;
import jugadores.Jugador;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class CyclicBehaviourReceiveMessage extends CyclicBehaviour {

    private static final long serialVersionUID = 1L;
    private DataAgent agente;
    private ArrayList<Jugador> listaJugadores;

    public CyclicBehaviourReceiveMessage (DataAgent agent){
        this.agente = agent;

        try{
            agente.setListaJugadores(agente.leerlista());
            this.listaJugadores = agente.getListaJugadores();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public void action() {
        ACLMessage message = this.myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
        if (message != null){
            if(message.getContent().equals("Mostrar")){
                ACLMessage msg1 = new ACLMessage(7);
                try {
                    this.listaJugadores = DataAgent.leerlista();
                    msg1.setContentObject(this.listaJugadores);
                }catch (IOException e){
                    e.printStackTrace();
                }
                msg1.addReceiver(new AID("VisualizationAgent",false));
                this.agente.send(msg1);

            } else if(message.getContent().equals("Cambiar")){
                try{
                    this.listaJugadores = this.agente.leerlista() ;
                }catch (IOException e){
                    e.printStackTrace();
                }

                try {
                    final Jugador nuevoJugador = (Jugador)message.getContentObject();

                    try {
                        this.agente.actualizarSalario(nuevoJugador);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }catch (UnreadableException e){
                    e.printStackTrace();
                }
            }
        } else {
            this.block();
        }

    };
}
