import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;

public class CreateIndexCard extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Enter Index Card Details");

        // Create the registration form grid pane
        GridPane gridPane = createRegistrationFormPane();
        // Add UI controls to the registration form grid pane
        addUIControls(gridPane);
        // Create a scene with registration form grid pane as the root node
        Scene scene = new Scene(gridPane, 800, 500);
        // Set the scene in primary stage	
        primaryStage.setScene(scene);
        
        primaryStage.show();
    }


    private GridPane createRegistrationFormPane() {
        // Instantiate a new Grid Pane
        GridPane gridPane = new GridPane();

        // Position the pane at the center of the screen, both vertically and horizontally
        gridPane.setAlignment(Pos.CENTER);

        // Set a padding of 20px on each side
        gridPane.setPadding(new Insets(40, 40, 40, 40));

        // Set the horizontal gap between columns
        gridPane.setHgap(10);

        // Set the vertical gap between rows
        gridPane.setVgap(10);

        // Add Column Constraints

        // columnOneConstraints will be applied to all the nodes placed in column one.
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
        // Add Header
        Label headerLabel = new Label("Index Card");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));

        // Add Title Label
        Label titleLabel = new Label("Title : ");
        gridPane.add(titleLabel, 0,1);

        // Add Title Text Field
        TextField titleField = new TextField();
        titleField.setPrefHeight(40);
        gridPane.add(titleField, 1,1);


        // Add Topics Label
        Label topicLabel = new Label("Topics : ");
        gridPane.add(topicLabel, 0, 2);

        // Add Topics Text Field
        TextField topicField = new TextField();
        topicField.setPrefHeight(40);
        gridPane.add(topicField, 1, 2);

        // Add Idea Label
        Label ideaLabel = new Label("Idea : ");
        gridPane.add(ideaLabel, 0, 3);

        // Add Idea Field
        TextArea ideaField = new TextArea();
        ideaField.setPrefHeight(100);
        gridPane.add(ideaField, 1, 3);

        // Add Save Button
        Button submitButton = new Button("Save Index Card");
        submitButton.setPrefHeight(40);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(100);
        gridPane.add(submitButton, 0, 4, 2, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        GridPane.setMargin(submitButton, new Insets(20, 0,20,0));

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(titleField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter the title");
                    return;
                }
                if(topicField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter the topics of your index card");
                    return;
                }
                if(ideaField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter the idea you wish to save");
                    return;
                }

                showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Success!", "Index Card Saved");
               
                // ADD CODE TO SAVE THE INDEX CARD IN A FILE HERE 
                
                
                 String title = titleField.getText();
                 String topics = topicField.getText();
                 String idea = ideaField.getText();
                 
                 saveIndexCard(title, topics, idea);           

            }

            
            
            private void saveIndexCard(String title, String topics, String idea) {
                BufferedWriter bw = null;                
                      try {
	 String mycontent =  
                            "Title : " + titleField.getText() + " \r\n" + 
                            "Topics : " + topicField.getText() + "\r\n" +
                            "Idea : " + ideaField.getText() + "\r\n" + "~" + "\r\n"  ;
         //Specify the file name and path here
	 File file = new File("allIndexCards.txt");

	 /* This logic will make sure that the file 
	  * gets created if it is not present at the
	  * specified location*/
	  if (!file.exists()) {
	     file.createNewFile();
	  }

	  FileWriter fw = new FileWriter(file,true);
	  bw = new BufferedWriter(fw);
	  bw.append(mycontent);
          System.out.println("File written Successfully");
          
          // set all the text fields to empty for entering more index cards
          titleField.setText("");
          topicField.setText("");
          ideaField.setText("");
          // focus on the title to make it easier to enter the next index card
          titleField.requestFocus();
      } catch (IOException ioe) {
	   ioe.printStackTrace();
	}
	finally
	{ 
	   try{
	      if(bw!=null)
		 bw.close();
	   }catch(Exception ex){
	       System.out.println("Error in closing the BufferedWriter"+ex);
	    }
	}
            }   // end of saveIndexCard Method   
        
        
        });  // end of button submit handler
    }
    

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
    

    public static void main(String[] args) {
        launch(args);
    }
}
