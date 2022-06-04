package agents;

import behaviour.CyclicBehaviourReceiveMessage;
import jugadores.Jugador;
import ui.JFramePrincipal;

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

    private static final float SALARIO_MINIMO = 5;
    private static final float SALARIO_MAXIMO = 150;

    private ArrayList<Jugador> listaJugadores = new ArrayList<Jugador>();
    private String filePath = "";
    private JFramePrincipal chatJFrame;
    public String texto = "PAX";
    protected CyclicBehaviourReceiveMessage receiveMessageBehaviour;

    public  DataAgent () {
        super();

        /*
        this.chatJFrame = new ChatJFrame(this);
         */
        this.receiveMessageBehaviour = new CyclicBehaviourReceiveMessage(this);
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

    public JFramePrincipal getChatJFrame() { return this.chatJFrame; }

    public void setListaJugadores(ArrayList<Jugador> lista) { this.listaJugadores = lista;}
    public ArrayList<Jugador> getListaJugadores(){ return this.listaJugadores;}
    public ArrayList<Jugador> leerlista() throws IOException{
        final ArrayList<Jugador> jugadores;
        final FileReader jugadoresFile = new FileReader(filePath);
        final Type tokenType = new TypeToken<ArrayList<Jugador>>(){}.getType();

        jugadores = new Gson().fromJson(jugadoresFile, tokenType);
        jugadoresFile.close();

        return jugadores;
    }

    public void actualizarSalario(final String nombre, final String apellido, final float salario) throws IOException{

        if (salario < SALARIO_MINIMO || salario > SALARIO_MAXIMO)
            loge(String.format("El salario debe estar comprendida entre %s y %s", SALARIO_MAXIMO, SALARIO_MINIMO));

        for (Jugador jugador : listaJugadores) {
            if(jugador.getNombre().equals(nombre) && jugador.getApellido().equals(apellido)){
                jugador.setSalario(salario);
                break;
            }
        }

        escribirLista(listaJugadores);
    }

    public void escribirLista(final ArrayList<Jugador> jugadores) throws IOException{
        final FileWriter jugadoresFile = new FileWriter(filePath);
        final Gson gson = new Gson();

        gson.toJson(jugadores,jugadoresFile);

        jugadoresFile.close();
    }

    private void log(String s){
        System.out.println(System.currentTimeMillis() + ": " + getLocalName() + "(" + getClass().getSimpleName() + ") " + s);
    }

    private void loge(String s) {
        System.err.println(System.currentTimeMillis() + ": " + getLocalName() + "(" + getClass().getSimpleName() + ") " + s);
    }
}
