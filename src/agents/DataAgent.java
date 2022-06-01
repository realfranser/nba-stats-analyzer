package agents;

import behaviour.CyclicBehaviourReceiveMessage;
import Jugadores.Jugador;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

/*
Agente de percepcion de informacion. Recibe datos externos de las estadisticas de NBA que queremos procesar,
en este caso se hace mediante metodos de recuperacion de informacion de una API web
 */
public class DataAgent extends Agent {

    private static final long serialVersionUID = 1L;
    private static final String SERVICE_NAME = "Data Communicator";
    private static final String SERVER_TYPE = "Raw Data Communication";


    private ArrayList<Jugador> listaJugadores = new ArrayList<Jugador>();
    private String filePath = "";
    /*
    TODO: investigar esta clase (no parece estar en la libreria
    private ChatJFrame charJFrame;
     */
    protected CyclicBehaviourReceiveMessage receiveMessageBehaviour;

    public  DataAgent () {
        super();

        /*
        this.charJFrame = new ChatJFrame(this);
         */
        this.receiveMessageBehaviour = new CyclicBehaviourReceiveMessage();
    }

    @Override
    protected void setup() {

        final DFAgentDescription agentDescription = new DFAgentDescription();
        agentDescription.setName(getAID());

        final ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setName(SERVICE_NAME);
        serviceDescription.setType(SERVER_TYPE);
        serviceDescription.addLanguages(new SLCodec().getName());

        agentDescription.addServices(serviceDescription);

        try {
            DFService.register(this, agentDescription);
        } catch (FIPAException e) {
            this.loge(e.getMessage());
        }

        /*
        this.getChatJFrame().setVisible(true);
         */
        this.addBehaviour(this.receiveMessageBehaviour);
    }

    @Override
    public void doDelete() {
        super.doDelete();
        loge(": Exit!!!");
    }

    private void sendMessage(final String text) {
        ACLMessage message = new ACLMessage(ACLMessage.CONFIRM);
        /*
        Add receiver agents of the message
        message.addReceiver();

        Add message content (if necessary)
        message.setContent();
         */
        send(message);
    }

    private void log(String s){
        System.out.println(System.currentTimeMillis() + ": " + getLocalName() + "(" + getClass().getSimpleName() + ") " + s);
    }

    private void loge(String s) {
        System.err.println(System.currentTimeMillis() + ": " + getLocalName() + "(" + getClass().getSimpleName() + ") " + s);
    }

    public ArrayList<Jugador> leerlista() throws FileNotFoundException, IOException{
        ArrayList<Jugador> jugadores;
        FileReader jugadoresFile = new FileReader(filePath);
        Type tokenType = new TypeToken<ArrayList<Jugador>>(){}.getType();
        jugadores = new Gson().fromJson(jugadoresFile, tokenType);
        jugadoresFile.close();
        return jugadores;
    }

    public void escribirLista(ArrayList<Jugador> jugadores) throws IOException{
        FileWriter jugadoresFile = new FileWriter(filePath);
        Gson gson = new Gson();
        gson.toJson(jugadores,jugadoresFile);
        jugadoresFile.close();
    }

    public void actualizarNota(String nombre, String apellido, float nota) throws IOException{

        if(0 <= nota && nota <= 10){
            for(int x = 0 ; x < listaJugadores.size(); x++){
                Jugador j = listaJugadores.get(x);
                if(j.getNombre().equals(nombre) && j.getApellido().equals(apellido)){
                    j.setNota(nota);
                }
            }
            escribirLista(listaJugadores);
        }else{
            System.err.println("Error: La nota debe estar comprendida entre 0 y 10");
        }
    }
}
