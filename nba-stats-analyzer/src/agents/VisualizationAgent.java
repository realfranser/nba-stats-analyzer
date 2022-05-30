package agents;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;

/*
Agente escargado de que el usuario visualice la informacion procesada mediante una interfaz de usuario
 */
public class VisualizationAgent extends Agent {

    @Override
    protected void setup() {}

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
}
