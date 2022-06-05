package agents;

import behaviour.CyclicVisualizationMessageReceiver;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jugadores.Jugador;
import ui.JFrameOut;

import java.util.ArrayList;


/*
Agente escargado de que el usuario visualice la informacion procesada mediante una interfaz de usuario
 */
public class VisualizationAgent extends Agent {

    private static final long serialVersionUID = 1L;
    private JFrameOut chatJFrame;
    private ArrayList<Jugador> listaJugadores;
    protected CyclicVisualizationMessageReceiver receiveMessageBehaviour;

    public VisualizationAgent(){
        super();

        this.chatJFrame = new JFrameOut(this);
        this.listaJugadores = new ArrayList<>();
        this.receiveMessageBehaviour = new CyclicVisualizationMessageReceiver();
    }

    @Override
    protected void setup() {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setName("Visualization");
        sd.setType("Agente");
        sd.addLanguages(new SLCodec().getName());
        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);
        } catch(FIPAException e) {
            System.err.println("Agent " + this.getLocalName() + ": " + e.getMessage());
        }

        this.addBehaviour(this.receiveMessageBehaviour);

        this.getChatJFrame().setVisible(true);
    }

    @Override
    public void doDelete() {
        super.doDelete();
        loge(": Exit!!!");
    }

    private void receiveMessage() {
        CyclicVisualizationMessageReceiver behaviour = new CyclicVisualizationMessageReceiver();
        addBehaviour(behaviour);
    }

    public JFrameOut getChatJFrame() { return this.chatJFrame; }

    public ArrayList<Jugador> getListaJugadores() { return this.listaJugadores; }

    public void setListaJugadores(ArrayList<Jugador> listaJugadores) { this.listaJugadores = listaJugadores; }

    private void log(String s){
        System.out.println(System.currentTimeMillis() + ": " + getLocalName() + "(" + getClass().getSimpleName() + ") " + s);
    }

    private void loge(String s) {
        System.err.println(System.currentTimeMillis() + ": " + getLocalName() + "(" + getClass().getSimpleName() + ") " + s);
    }
}
