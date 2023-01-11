package agents;

import Containers.ContainerAcheteur;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.ArrayList;
import java.util.List;

public class AgentAcheteur extends GuiAgent{
	
	protected ContainerAcheteur gui;
	protected AID[] sellerAgents;
	
	@Override
	protected void setup() {
	if (getArguments().length==1) {
		gui=(ContainerAcheteur) getArguments()[0];
		gui.setAgentAcheteur(this);
	}
		ParallelBehaviour parallelBehaviour=new ParallelBehaviour();
		addBehaviour(parallelBehaviour);
		
		parallelBehaviour.addSubBehaviour(new TickerBehaviour(this,10000) {
			
			@Override
			protected void onTick() {
				// Update the list of seller agents
				DFAgentDescription template = new DFAgentDescription(); 
				ServiceDescription sd = new ServiceDescription();  
				sd.setType("Book-selling"); 
				sd.setName("Vente-livres");
				template.addServices(sd);
				try { 
				DFAgentDescription[] result = DFService.search(myAgent,  template); 
				sellerAgents = new AID[result.length];
				for (int i = 0; i < result.length; ++i) {
					sellerAgents[i] = result[i].getName();  
					//System.out.println(sellerAgents[i]);	
					}
				} catch (FIPAException e) {e.printStackTrace();} } });

		
		parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
			
			private int counter=0;
			private List<ACLMessage> replies=new ArrayList<ACLMessage>();
			ACLMessage meilleurOffre;
			
			@Override 
			public void action() {
				/*MessageTemplate mt = MessageTemplate.or  
				(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),  
				MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.PROPOSE),
				MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.AGREE), 
				MessageTemplate.MatchPerformative(ACLMessage.REFUSE))));*/
				ACLMessage aclMessage=receive();
				if(aclMessage!=null) {
					switch (aclMessage.getPerformative()) {
					case ACLMessage.REQUEST:
						String livre=aclMessage.getContent();
						ACLMessage aclMessage2=new ACLMessage(ACLMessage.CFP);
						aclMessage2.setContent(livre);
						for(AID aid:sellerAgents){
							aclMessage2.addReceiver(aid);
						}
						
						
						send(aclMessage2);
						
						break; 
                    case ACLMessage.PROPOSE:
                     /* ++counter; 
                      replies.add(aclMessage);
                      if (counter==sellerAgents.length) {
                    	  meilleurOffre=replies.get(0);
                    	   double min=Double.parseDouble(replies.get(0).getContent());
                    	   
                       for (ACLMessage offre:replies) {
                    	   double price=Double.parseDouble(replies.get(0).getContent());	
                    	    if(price<min) {
                    	    	meilleurOffre=offre;
                    	    	min=price;
                    	     }
                    	    
                    	}
                    		
                    		ACLMessage aclMessageaccept=meilleurOffre.createReply();
                    		aclMessageaccept.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                    		aclMessageaccept.setContent(meilleurOffre.getContent());
                    		send(aclMessageaccept);
                    	}
						break;
                   case ACLMessage.AGREE:
                    	ACLMessage aclMessage3=new ACLMessage(ACLMessage.CONFIRM);
                    	aclMessage3.addReceiver(new AID("Consumer",AID.ISLOCALNAME));
                    	aclMessage3.setContent(meilleurOffre.getContent());
                    	send(aclMessage3);
                    	
                    	break;*/
                    case ACLMessage.REFUSE:
						
						break;
					default:
						break;
					}
				    String livre=aclMessage.getContent();
				    gui.logMessage(aclMessage);
					ACLMessage reply=aclMessage.createReply();
					reply.setContent("ok "+aclMessage.getContent());
					send(reply);
					/*ACLMessage aclMessage3=new ACLMessage(ACLMessage.CFP);
					aclMessage3.setContent(livre);
					for(AID aid:sellerAgents) {
					aclMessage4.addReceiver(aid);
					}
					send(aclMessage3);*/
				}
				
				else block();
				
			}
		});
		
		
	}
		
@Override
protected void onGuiEvent(GuiEvent event) {
	// TODO Auto-generated method stub
	
}
}
