package agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Client2 extends Agent {  


@Override
protected void setup() {
System.out.println("Je suis l'acheteur !");  
System.out.println("Mon nom est : " + getAID().getName());  
System.out.println("Je me prépare ...");

SequentialBehaviour sequentialBehaviour = new  SequentialBehaviour();
addBehaviour(sequentialBehaviour);


sequentialBehaviour.addSubBehaviour(new OneShotBehaviour() {

@Override
public void action() {
System.out.println("Le premier sous-comportement");
}
});

sequentialBehaviour.addSubBehaviour(new OneShotBehaviour() { 

@Override
public void action() {
System.out.println("Le deuxième sous-comportement");
}
});
sequentialBehaviour.addSubBehaviour(new OneShotBehaviour(){  

@Override
public void action() {
System.out.println("le dernier sous-comportement");

}
});


sequentialBehaviour.addSubBehaviour(new CyclicBehaviour() {  
private int compteur;
@Override
public void action() { 
++compteur;  
System.out.println("Cyclic Behaviour " + compteur); 
MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM); 
ACLMessage msg = myAgent.receive(mt);
if (msg != null) { 
System.out.println("Message reçu");  
System.out.println("Sender : " + msg.getSender().getName());  
System.out.println("Acte de communication : " +  ACLMessage.getPerformative(msg.getPerformative())); 
System.out.println("Content : " + msg.getContent());  
System.out.println("Langue : " + msg.getLanguage());  
System.out.println("Ontology : " + msg.getOntology());  

}else block(); } });





}
}


