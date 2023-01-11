package agentsImpl;

import agents.MyAgent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.StaleProxyException;
import jade.core.ProfileImpl;  
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController; 

public class AgentImpl {

public static void main(String[] args) throws StaleProxyException {
Runtime runtime = Runtime.instance();  
ProfileImpl pi = new ProfileImpl(false);  
pi.setParameter(Profile.MAIN_HOST, "localhost");
AgentContainer ac = runtime.createAgentContainer(pi);
AgentController agentController =  ac.createNewAgent("MyAgent",  MyAgent.class.getName(), new Object[]{});  
agentController.start();
}

}
