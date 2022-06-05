package agents;

import jade.core.Agent;
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

    public  JFrameIn myGui;

    protected void setup() {
        myGui = new JFrameIn(this);
        myGui.setVisible(true);
    }


    protected void onGuiEvent(GuiEvent ev) {
        button = ev.getType();

        if (button== 1) {
            ArrayList <String> data = new ArrayList <String>();

            data.add(myGui.getTextJugador().getText());
            data.add(myGui.getTextSalario().getText());

            addBehaviour(new OneShotBehaviour() {
                private static final long serialVersionUID = 1L;

                public void action() {
                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

                    try {
                        msg.setContentObject(data);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    msg.setContent("Cambiar");
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
                    System.out.println("llamo a mostrar");
                }
            });

        }
    }

    /*
    public void doDelete() {
        super.doDelete();
        loge(": Exit!!!");
    }



    private void sendMessage(final String text) {
        ACLMessage message = new ACLMessage(ACLMessage.CONFIRM);

        Add receiver agents of the message
        message.addReceiver();

        Add message content (if necessary)
        message.setContent();

        send(message);
    }

    private void log(String s){
        System.out.println(System.currentTimeMillis() + ": " + getLocalName() + "(" + getClass().getSimpleName() + ") " + s);
    }

    private void loge(String s) {
        System.err.println(System.currentTimeMillis() + ": " + getLocalName() + "(" + getClass().getSimpleName() + ") " + s);
    }
    */
}
