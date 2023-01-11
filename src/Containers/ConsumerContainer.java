package Containers;


import agents.AgentConsumer;
import jade.core.ProfileImpl;  import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConsumerContainer extends Application {
	
	protected AgentConsumer agentConsumer;
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
         AgentController agentController=agentContainer.createNewAgent("Consumer",
        		 "agents.AgentConsumer", new Object[]{this});
        		 agentController.start();

	     }
         catch (Exception e)
	    { e.printStackTrace(); }
}



public void start(Stage primaryStage) throws Exception {
	startContainer();
	primaryStage.setTitle("Consommateur");
	HBox hbox=new HBox();
	hbox.setPadding(new Insets(10));
	hbox.setSpacing(10);
	Label label = new Label("Livre :");
	TextField textFieldLivre=new TextField();
	Button buttonAcheter=new Button("Acheter");
	hbox.getChildren().addAll(label,textFieldLivre,buttonAcheter);
	VBox vbox=new VBox();
	vbox.setPadding(new Insets(10));
	observableList=FXCollections.observableArrayList();
	ListView<String> listViewMessages= new ListView<String>(observableList);
	vbox.getChildren().add(listViewMessages);
	BorderPane borderpane = new BorderPane();
	borderpane.setTop(hbox);
	borderpane.setCenter(vbox);
	Scene scene=new Scene(borderpane,600,400);
	primaryStage.setScene(scene);
	primaryStage.show();
	
	buttonAcheter.setOnAction(evt->{
	   String livre=textFieldLivre.getText();
	   //observableList.add(livre);
	  GuiEvent event =new GuiEvent(this, 1);
	  event.addParameter(livre);
	  agentConsumer.onGuiEvent(event);  
		
	});
	
}

public void setAgentConsumer(AgentConsumer agentConsumer) {
	this.agentConsumer = agentConsumer;
}

public void logMessage(ACLMessage aclMessage) {
	Platform.runLater(()->{
		observableList.add(aclMessage.getContent()+ "  ,  " +aclMessage.getSender().getName());
	});
	
}

}

