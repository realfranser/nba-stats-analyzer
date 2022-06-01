package agents;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;

/*
Este agente se encarga de realizar calculos para procesar los datos recibidos por el DataAgent
 */
public class ExecutorAgent extends Agent {

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
