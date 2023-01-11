package agents;
import jade.core.Agent;
public class MyAgent extends Agent {  private String texte;
// Initialisation de l'Agent
protected void setup() {  super.setup();
texte = "Bonjour les ENSAfiens !";
System.out.println("De l'agent " + getLocalName() + " " +  texte);
System.out.println("Mon adresse est " + getAID());
//arrêter l'agent 
//doDelete();
}
//Nettoyage de l'agent
protected void takeDown() {
super.takeDown();
System.out.println("Agent " + getLocalName() + " quitte  la plateforme ");
}

}
