package Containers;

import agents.AgentAcheteur;
import agents.AgentVendeur;
import jade.core.ProfileImpl;  import jade.core.Runtime;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
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

public class ContainerVendeur extends Application{
	
	protected AgentVendeur agentVendeur;
	protected ObservableList<String> observableList;
	AgentContainer agentContainer;
		
	public static void main(String[] args) { 
		launch(args);
	}

	public void startContainer() {
		
		try {
	        Runtime runtime=Runtime.instance();  
	        ProfileImpl profileImpl=new ProfileImpl(false);
	        profileImpl.setParameter(ProfileImpl.MAIN_HOST,"localhost"); 
	        agentContainer=runtime.createAgentContainer(profileImpl);  
	        agentContainer.start();
		     }
	        catch (Exception e)
		    { e.printStackTrace(); }
		
		
	}
	
	
		
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		startContainer();
		primaryStage.setTitle("Vendeur");
		HBox hbox =new HBox();
		hbox.setPadding(new Insets(10));
		hbox.setSpacing(10);
		Label label=new Label("Agent name :");
		TextField textFieldAgentNme= new TextField();
		Button buttonDeploy= new Button("Deploy");
		hbox.getChildren().addAll(label,textFieldAgentNme,buttonDeploy);
		BorderPane borderPane=new BorderPane();
		VBox vbox=new VBox();
		observableList=FXCollections.observableArrayList();
		ListView<String> listView=new ListView<String>(observableList);
		vbox.getChildren().add(listView);
		borderPane.setCenter(vbox);
		borderPane.setTop(hbox);
		Scene scene= new Scene(borderPane,400,300);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
		buttonDeploy.setOnAction((evt)->{
			 AgentController agentController;
			try {
				String name = textFieldAgentNme.getText();
				agentController = agentContainer.createNewAgent(name, "agents.AgentVendeur", new Object[] {this});
				agentController.start();
			} catch (StaleProxyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     
			
		});
	}

	public void setAgentVendeur(AgentVendeur agentVendeur) {
		this.agentVendeur = agentVendeur;
	}
	public void logMessage(ACLMessage aclMessage) {
		Platform.runLater(()->{
			observableList.add(aclMessage.getContent()+ "  ,  " +aclMessage.getSender().getName());
			
		});
		
	}


}

