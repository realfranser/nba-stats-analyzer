package behaviour;

import agents.DataAgent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.awt.*;

public class CyclicBehaviourReceiveMessage extends CyclicBehaviour {

    private static final long serialVersionUID = 1L;

    public void action() {
        ACLMessage message = this.myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));

        if ((message != null)) {
            ((DataAgent) this.myAgent).getChatJFrame().setMinimumSize(new Dimension());
        } else {
            this.block();
        }

    };
}
