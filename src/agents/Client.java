package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.lang.acl.ACLMessage;

public class Client extends Agent {  
	
private String article;

@Override
protected void setup() {
System.out.println("Je suis l'acheteur !");  
System.out.println("Mon nom est : " + getAID().getName());  
System.out.println("Je me prépare ...");

Object[] args = getArguments();
if(args.length == 1) {
article = (String) args[0];
}else {
System.out.println("j'ai besoin du nom de l'article à acheter ...");
}

ParallelBehaviour parallelbehaviour=new ParallelBehaviour();
addBehaviour(parallelbehaviour);

parallelbehaviour.addSubBehaviour(new CyclicBehaviour() {
	
	@Override
	public void action() {
		ACLMessage aclMessage=receive();
		if(aclMessage!=null) {
			System.out.println("************************");
			System.out.println("Réception de message");
			System.out.println(aclMessage.getContent());
			System.out.println(aclMessage.getSender().getName());
			System.out.println(aclMessage.getPerformative());
			System.out.println(aclMessage.getLanguage());
			System.out.println(aclMessage.getOntology());
			System.out.println("************************");
			
			ACLMessage reply=aclMessage.createReply();
			reply.setContent("Bon weekend");
		    send(reply);
		}
		else block();
		
	}
});






addBehaviour(new OneShotBehaviour() {
  //private static final long serialVersionUID = 1L;

@Override
public void action() {
System.out.println("Cette action est exécutée une seule  fois");
if (article == null)
System.out.println("pas d'article à acheter");
else
System.out.println("article = " + article);
}
});

}

}