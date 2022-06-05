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
        System.out.println("Recibido Mostrar");
        if (message != null){
            if(message.getContent().equals("Mostrar")){
                ACLMessage msg1 = new ACLMessage(7);
                try {
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
                    ArrayList<String> data = (ArrayList)message.getContentObject();
                    // Suponiendo 0 = nombre 1 = apellido 2 = salario
                    String nombre = data.get(0);
                    float salario = Float.parseFloat(data.get(1));

                    try {
                        this.agente.actualizarSalario(nombre,salario);
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
