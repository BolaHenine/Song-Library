//Bola Henine
//Roshan Seth

package app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import view.songController;

public class SongLib extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		
		FileWriter csvWriter;
		File temp;
		try {
			
			File f = new File("new.csv");
			
	        boolean exists = f.exists();
	       
        	if(!exists) {
        		csvWriter = new FileWriter("new.csv");
        		csvWriter.flush();
        		csvWriter.close();
        	}
	        
	        
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				getClass().getResource("/view/songviewer.fxml"));
		AnchorPane root = (AnchorPane)loader.load();

		songController songController = 
				loader.getController();
		
		
		songController.start(primaryStage);

		Scene scene = new Scene(root, 527, 441);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);

	}

}