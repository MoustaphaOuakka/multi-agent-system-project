package Containers;

import agents.AgentAcheteur;
import jade.core.ProfileImpl;  import jade.core.Runtime;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage; 
public class ContainerAcheteur extends Application {
	
	protected AgentAcheteur agentAcheteur;
	protected ObservableList<String> observableList;


public static void main(String[] args) { 
	launch(args);
	
}

public void startContainer() {
	try {
        Runtime runtime=Runtime.instance();  
        ProfileImpl profileImpl=new ProfileImpl(false);
        profileImpl.setParameter(ProfileImpl.MAIN_HOST,"localhost"); 
        AgentContainer agentContainer=runtime.createAgentContainer(profileImpl);  
        AgentController agentController= agentContainer.createNewAgent("acheteur1", "agents.AgentAcheteur", new Object[] {this});
        agentController.start();
	     }
        catch (Exception e)
	    { e.printStackTrace(); }
}


	
	


@Override
public void start(Stage primaryStage) throws Exception {
	startContainer();
	primaryStage.setTitle("Acheteur");
	BorderPane borderPane=new BorderPane();
	VBox vbox=new VBox();
	observableList=FXCollections.observableArrayList();
	ListView<String> listView=new ListView<String>(observableList);
	vbox.getChildren().add(listView);
	borderPane.setCenter(vbox);
	Scene scene= new Scene(borderPane,400,300);
	primaryStage.setScene(scene);
	primaryStage.show();
	
	
	
}

public void setAgentAcheteur(AgentAcheteur agentAcheteur) {
	this.agentAcheteur = agentAcheteur;
}

public void logMessage(ACLMessage aclMessage) {
	Platform.runLater(()->{
		observableList.add(aclMessage.getContent()+ "  ,  " +aclMessage.getSender().getName());
		
	});
	
}
}

