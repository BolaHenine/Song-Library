//Bola Henine
//Roshan Seth


package view;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class songController {
  @FXML Button delete;
  @FXML Button add;
  @FXML Button edit;
  @FXML TextArea nameField;
  @FXML TextArea artistField;
  @FXML TextArea yearField;
  @FXML TextArea albumField;
  @FXML ListView < Song > songView;

  private static ObservableList < Song > songsList;


  public void start(Stage mainStage) {
	  
	  mainStage.setOnCloseRequest((EventHandler<WindowEvent>) new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent e) {
	    	mainStage.close();
	    	
	    		FileWriter csvWriter;
				try {
					csvWriter = new FileWriter("new.csv");
					
		    		for(int i = 0; i < songsList.size(); i ++) {
		    			csvWriter.append(songsList.get(i).getName());
			    		csvWriter.append(",");
			    		csvWriter.append(songsList.get(i).getArtist());
			    		csvWriter.append(",");
			    		csvWriter.append(songsList.get(i).getAlbum());
			    		csvWriter.append(",");
			    		csvWriter.append(songsList.get(i).getYear());
			    		csvWriter.append("\n");
		    		}

		    		csvWriter.flush();
		    		csvWriter.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    		
		    }
		  });
	  
	  songsList = FXCollections.observableArrayList();
	  String line = "";
	  String splitBy = ",";
	 

	      try {
	    	  BufferedReader br = new BufferedReader(new FileReader("new.csv"));
			while ((line = br.readLine()) != null)
			      {
			        String[] song = line.split(splitBy);
			        Song a = null;
			        
			      
			        if(song.length <= 2) {
			        	a = new Song(song[0], song[1], "", "");
			        }
			        else if(song.length <= 3){
			        	a = new Song(song[0], song[1], song[2], "");
			        }
			        else {
			        	a = new Song(song[0], song[1], song[2], song[3]);
			        }
			        
			   
					songsList.add(a);
			        
			      }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        
	  
    songsList.sort(Comparator.comparing(Song::getName, String.CASE_INSENSITIVE_ORDER).thenComparing(Song::getArtist, String.CASE_INSENSITIVE_ORDER));
 
    songView.setItems(songsList);

    songView.setCellFactory(lv -> new ListCell < Song > () {
      @Override
      public void updateItem(Song item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
          setText(null);
        } else {
          String name = item.getName();
          String artist = item.getArtist(); // get text from item
          setText("Song: " + name + " --- " + "Artist: " + artist);
        }
      }
    });

    // set listener for the items

    songView.getSelectionModel().selectedIndexProperty().addListener((obs) -> showItemInputDialog(mainStage));
    songView.getSelectionModel().select(0);
  }

  private void showItemInputDialog(Stage mainStage) {
    Song item = songView.getSelectionModel().getSelectedItem();
    int index = songView.getSelectionModel().getSelectedIndex();
    if (songsList.size() == 0) {
      nameField.setText("");
      artistField.setText("");
      albumField.setText("");
      yearField.setText("");
    } else if (item == null) {

    } else {
      nameField.setText(item.getName());
      artistField.setText(item.getArtist());
      albumField.setText(item.getAlbum());
      yearField.setText(item.getYear());
    }
  }

  public static boolean isNumeric(String str) {
    try {
      Double.parseDouble(str);
      double x =  Double.parseDouble(str);
      if(x > 0 ) {
    	  return true;
      }
      else {
    	  return false;
      }
     
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public void buttonClick(ActionEvent e) {
    Button b = (Button) e.getSource();
    int index = songView.getSelectionModel().getSelectedIndex();
    Song selected = songView.getSelectionModel().getSelectedItem();

    Dialog < Song > emptyName = new Dialog < > ();
    emptyName.setTitle("Not Valid");
    emptyName.setHeaderText("Please enter a valid song name");
    DialogPane emptyNameDialogPane = emptyName.getDialogPane();
    emptyNameDialogPane.getButtonTypes().addAll(ButtonType.OK);

    Dialog < Song > emptyArtist = new Dialog < > ();
    emptyArtist.setTitle("Not Valid");
    emptyArtist.setHeaderText("Please enter a valid artist name");
    DialogPane emptyArtistDialogPane = emptyArtist.getDialogPane();
    emptyArtistDialogPane.getButtonTypes().addAll(ButtonType.OK);

    Dialog < Song > invalidYear = new Dialog < > ();
    invalidYear.setTitle("Not Valid");
    invalidYear.setHeaderText("Please enter a valid year");
    DialogPane invalidYearDialogPane = invalidYear.getDialogPane();
    invalidYearDialogPane.getButtonTypes().addAll(ButtonType.OK);
    
    Dialog < Song > exists = new Dialog < > ();
    exists.setTitle("Already Exists");
    exists.setHeaderText("This song already exists");
    DialogPane existsDialogPane = exists.getDialogPane();
    existsDialogPane.getButtonTypes().addAll(ButtonType.OK);

    if (b == delete) {
      Dialog < ButtonType > dialog = new Dialog < > ();
      dialog.setTitle("Confirmation required");
      dialog.setHeaderText("Are you sure you want to delete the song");
      DialogPane dialogPane = dialog.getDialogPane();
      dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("Empty Lib");
      alert.setHeaderText("Nothing to delete");

      Alert noSelect = new Alert(AlertType.INFORMATION);
      noSelect.setTitle("Nothing Selected");
      noSelect.setHeaderText("Please select something to delete");

      if (songsList.size() == 0) {
        alert.show();
      } else if (index == -1) {
        noSelect.show();
      } else {
    	  Optional<ButtonType> result = dialog.showAndWait();
      	if (result.isPresent() && result.get() == ButtonType.OK) {
            songsList.remove(index);
            if (index != songsList.size()) {
              songView.getSelectionModel().select(index);
            } else {
              songView.getSelectionModel().select(index - 1);
            }
          }

      }

    } else if (b == edit) {
    	
    	Dialog < ButtonType > dialog = new Dialog < > ();
        dialog.setTitle("Confirmation required");
        dialog.setHeaderText("Are you sure you want to Edit the song");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

    	
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("Empty Lib");
      alert.setHeaderText("Nothing to Edit");

      Alert noSelect = new Alert(AlertType.INFORMATION);
      noSelect.setTitle("Nothing Selected");
      noSelect.setHeaderText("Please select something to edit");
      
      

      if (index == -1) {
        noSelect.show();
      } else if (songsList.size() == 0) {
        alert.show();
      } else {
              if (nameField.getText().trim().isEmpty()) {

                emptyName.show();
                emptyName.setResultConverter((ButtonType btn) -> {
                  if (btn == ButtonType.OK) {
                    emptyName.close();
                  }
                  return null;

                });
              } else if (artistField.getText().trim().isEmpty()) {
                emptyArtist.show();
                emptyArtist.setResultConverter((ButtonType btn) -> {
                  if (btn == ButtonType.OK) {
                    emptyName.close();
                  }
                  return null;

                });
              } else if (!isNumeric(yearField.getText()) && !yearField.getText().equals("")) {
                invalidYear.show();
                invalidYear.setResultConverter((ButtonType btn) -> {
                  if (btn == ButtonType.OK) {
                    invalidYear.close();
                  }
                  return null;

                });
              } else {
            	if(nameField.getText().equals(selected.getName()) &&  artistField.getText().equals(selected.getArtist())) {
            		Optional<ButtonType> result = dialog.showAndWait();
                	if (result.isPresent() && result.get() == ButtonType.OK) {
		            		Song song = new Song(nameField.getText().trim(), artistField.getText().trim(),  albumField.getText().trim(), yearField.getText().trim());
		            		songsList.set(index, song);
							songsList.sort(Comparator.comparing(Song::getName, String.CASE_INSENSITIVE_ORDER).thenComparing(Song::getArtist, String.CASE_INSENSITIVE_ORDER));
            			}
            			else if(result.get() == ButtonType.CANCEL ) {
            				nameField.setText(selected.getName());
            				albumField.setText(selected.getAlbum());
            				yearField.setText(selected.getYear());
            				artistField.setText(selected.getArtist());         				
        				}
            	}
            	else if(songsList.stream().anyMatch(a -> a.getName().equalsIgnoreCase(nameField.getText()) && a.getArtist().equalsIgnoreCase(artistField.getText()))) {
            		exists.show();
            	}
            	else {
            		Optional<ButtonType> result = dialog.showAndWait();
                	if (result.isPresent() && result.get() == ButtonType.OK) {
	            		
	                    Song song = new Song(nameField.getText().trim(), artistField.getText().trim(),  albumField.getText().trim(), yearField.getText().trim());
	                  songsList.set(index, song);
	                  songsList.sort(Comparator.comparing(Song::getName, String.CASE_INSENSITIVE_ORDER).thenComparing(Song::getArtist, String.CASE_INSENSITIVE_ORDER));
	                  
            			}
                	else if(result.get() == ButtonType.CANCEL ) {
            				nameField.setText(selected.getName());
            				albumField.setText(selected.getAlbum());
            				yearField.setText(selected.getYear());
            				artistField.setText(selected.getArtist());
            				}
            	}
            	

              }
           

      }
   

    } else if (b == add) {
    	
    	Dialog < ButtonType > dialog = new Dialog < > ();
        dialog.setTitle("Confirmation required");
        dialog.setHeaderText("Are you sure you want to Add the song");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);


    	
          if (songsList.stream().anyMatch(a -> a.getName().equalsIgnoreCase(nameField.getText()) && a.getArtist().equalsIgnoreCase(artistField.getText()))) {
      		exists.show();
          } else {
            if (nameField.getText().trim().isEmpty()) {
              emptyName.show();
              emptyName.setResultConverter((ButtonType btn) -> {
                if (btn == ButtonType.OK) {
                  emptyName.close();
                }
                return null;

              });
            } else if (artistField.getText().trim().isEmpty()) {
              emptyArtist.show();
              emptyArtist.setResultConverter((ButtonType btn) -> {
                if (btn == ButtonType.OK) {
                  emptyName.close();
                }
                return null;

              });
            } else if (!isNumeric(yearField.getText()) && !yearField.getText().equals("")) {
              invalidYear.show();
              invalidYear.setResultConverter((ButtonType btn) -> {
                if (btn == ButtonType.OK) {
                  invalidYear.close();
                }
                return null;

              });
            } else {
//            	dialog.show();
            	Optional<ButtonType> result = dialog.showAndWait();
            	if (result.isPresent() && result.get() == ButtonType.OK) {
            		Song song = new Song(nameField.getText().trim(), artistField.getText().trim(), albumField.getText().trim(), yearField.getText().trim());
	                songsList.add(song);
	                songsList.sort(Comparator.comparing(Song::getName, String.CASE_INSENSITIVE_ORDER).thenComparing(Song::getArtist, String.CASE_INSENSITIVE_ORDER));
	                songView.getSelectionModel().select(songsList.indexOf(song));
            	 }
            	
            }
          }
    }
  }


  }
