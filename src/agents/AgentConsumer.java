package agents;
import javax.swing.JOptionPane;

import Containers.ConsumerContainer;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
public class AgentConsumer extends GuiAgent {
	
private transient ConsumerContainer gui;
@Override
protected void setup() {
    
	if(getArguments().length==1) {
	gui=(ConsumerContainer) getArguments()[0];
	gui.setAgentConsumer(this);
	}
 
ParallelBehaviour parallelbehaviour= new ParallelBehaviour();
addBehaviour(parallelbehaviour);

parallelbehaviour.addSubBehaviour(new CyclicBehaviour() {
	
	@Override
	public void action() {
		ACLMessage aclmessage = receive();
		if(aclmessage!=null) {
			
			switch (aclmessage.getPerformative()) {
			case ACLMessage.CONFIRM:
				gui.logMessage(aclmessage);
				break;

			default:
				break;
			}
		
		}
		else block();
		
	}
});
}



@Override
public void onGuiEvent(GuiEvent params) {
    if(params.getType()==1) {
    	String livre = params.getParameter(0).toString();
    	ACLMessage aclMessage= new ACLMessage(ACLMessage.REQUEST);
    	aclMessage.setContent(livre);
    	aclMessage.addReceiver(new AID("acheteur1",AID.ISLOCALNAME));
    	send(aclMessage);
    	
	
    }
}
}
