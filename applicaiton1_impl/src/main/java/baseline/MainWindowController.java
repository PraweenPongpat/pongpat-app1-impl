/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Praween Pongpat
 */

package baseline;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MainWindowController {

    @FXML
    private Button addNewTaskButton;
    @FXML
    private TableColumn<TaskObject, String> descriptionColumn;
    @FXML
    private TableColumn<TaskObject, String> dueDateColumn;
    @FXML
    private TableColumn<TaskObject, String> statusColumn;
    @FXML
    private Button clearListButton;
    @FXML
    private Button editTaskButton;
    @FXML
    private Button makeTaskCompleteButton;
    @FXML
    private Button makeTaskIncompleteButton;
    @FXML
    private Button removeTaskButton;
    @FXML
    private TableView<TaskObject> tableView;
    @FXML
    private Button loadButton;
    @FXML
    private Button saveButton;
    @FXML
    private RadioButton displayAllRadioButton;
    @FXML
    private RadioButton displayCompletedRadioButton;
    @FXML
    private RadioButton displayIncompleteRadioButton;
    @FXML
    private ToggleGroup displayModes;
    @FXML
    private Label errorDisplayLabel;

    //needed variables****
    public static Scanner input;
    public FileChooser fileChooser = new FileChooser();
    private ObservableList<TaskObject> observableList;
    //as of now that i can't fully figure out, i will plan according to use static
    //i should be able to NOT use static list, in an actual implementation
    public ListWrapper listWrapper = new ListWrapper();
    public int tempIndex = -1;
    //static list variable 'todoList' to ensure that there will only be one list across the program
    //static taskChecker, used to pass information as a flag for specific tasks

    @FXML
    void addNewTaskButtonPushed(ActionEvent event) throws IOException {
        //this button should always be visible

        //to add a new scene
        //set the 'isAdding' to true. This will be the flag shows the different between Add or Edit
        listWrapper.setAdding(true);
        //call the changeScene function, to change the scene to AddEditWindow.fxml
        //  note that when add a task, the AddEditWindow will not have any initializes value
        changeScene("AddEditWindow.fxml");
    }

    @FXML
    void editTaskButtonPushed(ActionEvent event){
        //this button should only be visible when any task is selected

        //if nothing is selected from the tableView
        if(listWrapper.getIndex()<0) {
            //  set the errorDisplayLabel prompting user "you must select a task to edit its information"
            errorDisplayLabel.setText("you must select a task to edit its information");
            //  exit method, doing nothing else
        } else {
            //  set the errorDisplayLabel to empty String, since no error occur
            errorDisplayLabel.setText("");

            //to edit a task in a new scene
            //set 'isAdding' to false
            listWrapper.setAdding(false);
            //  this means that for editing, index will always be 0 or greater
            //call the changeScene function, to change the scene to AddEditWindow.fxml
            //  note that when edit task, the AddEditWindow will initialize the existed information of the task

            try {
                changeScene("AddEditWindow.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void makeTaskCompleteButtonPushed(ActionEvent event) {
        //this button should only be visible when selected the task that is incomplete

        if(listWrapper.getIndex()<0) {
            //  set the errorDisplayLabel prompting user "you must select a task to proceed..."
            errorDisplayLabel.setText("you must select a task to change its status...");
            //  exit method, doing nothing else
        } else {
            //  set the errorDisplayLabel to empty String, since no error occur
            errorDisplayLabel.setText("");

            //with the current 'index' from ListWrapper class variable
            //access the todoList with that index
            //set the "status" of the taskObject to that index to "completed"
            listWrapper.getList().get(listWrapper.getIndex()).setStatus("completed");

            //refresh this scene again, or call the changeScene function passing the same .fxml
            //(to reload the updated list)
            observableList.clear();
            observableList = FXCollections.observableArrayList(listWrapper.getList());
            tableView.setItems(observableList);
        }

    }

    @FXML
    void makeTaskIncompleteButtonPushed(ActionEvent event) {
        //this button should only be visible when selected the task that is complete

        //if nothing is selected from the tableView
        if(listWrapper.getIndex()<0) {
            //  set the errorDisplayLabel prompting user "you must select a task to proceed..."
            errorDisplayLabel.setText("you must select a task to change its status...");
            //  exit method, doing nothing else
        } else {
            //  set the errorDisplayLabel to empty String, since no error occur
            errorDisplayLabel.setText("");

            //with the current 'index' from ListWrapper class variable
            //access the todoList with that index
            //set the "status" of the taskObject to that index to "incomplete"
            listWrapper.getList().get(listWrapper.getIndex()).setStatus("incomplete");

            //refresh this scene again, or call the changeScene function passing the same .fxml
            //(to reload the updated list)
            observableList.clear();
            observableList = FXCollections.observableArrayList(listWrapper.getList());
            tableView.setItems(observableList);
        }
    }

    @FXML
    void removeTaskButtonPushed(ActionEvent event) {
        //this button should only be visible when any task is selected

        //if nothing is selected from the tableView
        if(listWrapper.getIndex()<0) {
            //  set the errorDisplayLabel prompting user "you must select a task to proceed..."
            errorDisplayLabel.setText("you must select a task to remove something...");
            //  exit method, doing nothing else
        } else {
            //  set the errorDisplayLabel to empty String, since no error occur
            errorDisplayLabel.setText("");

            //with the current 'index' from ListWrapper class variable
            // .remove to remove that task out of the list
            listWrapper.getList().remove(tempIndex);
            observableList.remove(tempIndex);


            //refresh this scene again, or call the changeScene function passing the same .fxml
            //(to reload the updated list)
        }
    }

    @FXML
    void clearListButtonPushed(ActionEvent event) {
        //this button should be visible at all time

        //when the clearListButton is pushed
        //clear the data observableList (clear all data in tableView too)
        observableList.clear();
        //access the list in ListWrapper
        //  clear() the list too
        listWrapper.getList().clear();

        //we may have to refresh the scene (to refresh the table)
    }

    @FXML
    void displayAllRadioButtonPushed(ActionEvent event) {
        //this is the default displayMode when the scene is initialized

        //when displayMode 'all' is selected
        //clear all the data in the tableView
        observableList.clear();
        observableList = FXCollections.observableArrayList(listWrapper.getList());

        //set up the data in the tableView with ALL elements in the list
        //set all the items to display in the tableView
        tableView.setItems(observableList);
    }

    @FXML
    void displayCompletedRadioButtonPushed(ActionEvent event) {
        //when displayMode 'completed' is selected
        //clear all the data in the tableView
        observableList.clear();
        List<TaskObject> tempList = new ArrayList<>();
        //set up the data in the tableView with only the TaskObject that has 'status' = "completed"
        for(int i =0; i<listWrapper.getList().size();i++) {
            if(listWrapper.getList().get(i).getStatus().equals("completed")){
                tempList.add(listWrapper.getList().get(i));
            }
        }
        observableList = FXCollections.observableArrayList(tempList);
        tableView.setItems(observableList);
    }

    @FXML
    void displayIncompleteRadioButtonPushed(ActionEvent event) {
        //when displayMode 'completed' is selected
        //clear all the data in the tableView
        observableList.clear();
        List<TaskObject> tempList = new ArrayList<>();
        //set up the data in the tableView with only the TaskObject that has 'status' = "incomplete"
        for(int i =0; i<listWrapper.getList().size();i++) {
            if(listWrapper.getList().get(i).getStatus().equals("incomplete")){
                tempList.add(listWrapper.getList().get(i));
            }
        }
        observableList = FXCollections.observableArrayList(tempList);
        tableView.setItems(observableList);
    }

    @FXML
    void saveButtonPushed(ActionEvent event) {
        //when saveButton is pushed
        //uses a FileChooser to get the selected address from user
        //create a file that is pointing to the result of file choosing

        //if file is null, means that the user didn't pick the file (cancel the choosing)
        //      end method with return statement

        //otherwise, means the user wants to make a file (saving)
        //create a writer (Formatter, BufferWriter works too, leave as options when implement)
        //write in the following format

        //****************format .txt*********************
        //#number of items in that todoList
        //each item:    dueDate,description,status      ---> use comma to separate the things in the object
        // ...
        //************************************************
    }

    @FXML
    void loadButtonPushed(ActionEvent event) {
        //when loadButton is pushed
        //uses a FileChooser to get the selected address from user
        //set the 'file' for a result from using fileChooser
        //if file is null, means that the user didn't pick the file (cancel the choosing)
        //      end method with return statement

        //otherwise, means the file is NOT null...
        //create a Scanner and link the Scanner to te path received from FileChooser
        //start reading the file
        //loading will reflect in the saving format

        //****************format .txt*********************
        //#number of items in that todoList
        //each item:    dueDate,description,status      ---> use comma to separate the things in the object
        // ...
        //************************************************

        //access the list on ListWrapper class, clear all the data in the list using clear()
        //read one while line at a time
        //  each line read from rawData, parse it with split comma
        //  create a taskObject from the parsed data
        //  add the taskObject into the list

        //clear the data in the tableView
        //call initialize class again, to refresh the scene
        //this is the update the tableView to the loaded list
    }

    //this method may change!!! i haven't figured out yes how controllers are communicating
    private void changeScene(String fileNameFXML) throws IOException {

        //this function is used to navigate/change scene
        //the function has to pre-load the scene information to the other scene to pass parameters to the next scene

        //create a Parent class 'parent', use FXML loader.load(getClass.getResource) just like the textbook
        //  specify the fileNameFXML into it
        //make a new Scene 'scene', set the seen with 'parent'
        //set the 'window' by setScene with 'scene'
        //show the window (run the actual scene changing)

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fileNameFXML));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("something is wrong, parent is null.");
        }

        AddEditWindowController controller = loader.getController();
        controller.initializeListWrapper(listWrapper,tempIndex);
        assert root != null;
        Scene mainWindowScene = new Scene(root);

        Stage window = (Stage) errorDisplayLabel.getScene().getWindow();
        window.setScene(mainWindowScene);
        window.show();

    }

    public void initializeListWrapper(ListWrapper listWrapper) {
        //this method will be used (as a receiving side) between scene changing
        this.listWrapper = listWrapper;
        observableList.clear();
        observableList = FXCollections.observableArrayList(listWrapper.getList());
        tableView.setItems(observableList);
    }

    public void initialize() {
        //the scene will always be re-initialize every time
        //this is needed, because the tableView needs to refresh for add/remove/edit

        //always initialize the errorDisplayLabel by set it to ""
        errorDisplayLabel.setText("");

        //initialize the 'fileChooser' is optional, maybe do it here if wanted to

        //set the observableList to the list that we have in the ListWrapper (same structure, same data)
        //      if list is used as reference, may not be able to set directly
        //      linearly traverse through the list, copy the data if needed
        //the observableList will be the list that communicates with the tableView, not the list in ListWrapper
        for(int i = 1; i<102;i++)
            listWrapper.getList().add(new TaskObject("1111-01-11","object#"+i));

        observableList = FXCollections.observableArrayList(listWrapper.getList());

        //need to add listener here, this is to ALWAYS detect the change in the tableView
        //when an object in the table is clicked, make those buttons visible (as needed)
        //if the selectedObject has 'status' to "completed"
        //      set makeTaskIncompleteButton to visible
        //if the selectedObject has 'status' to "incomplete"
        //      set makeTaskCompleteButton to visible
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                tempIndex = observableList.indexOf(tableView.getSelectionModel().getSelectedItem());
                listWrapper.setIndex(tempIndex);
            }
        });

        //set the toggle group for radioButtons (should already init displaying 'all')
        //add 'displayAllRadioButton' to the toggleGroup 'displayModes'
        //add 'displayCompletedRadioButton' to the toggleGroup 'displayModes'
        //add 'displayIncompleteRadioButton' to the toggleGroup 'displayModes'

        //must initialize the tableView (use to regularly display tasks in the observableList)
        //set the column to display each object accordingly
        //column name "dueDate" is set to look for 'dueDate' of the taskObject
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        //column name "description" is set to look for 'description' of the taskObject
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        //column name "status" is set to look for 'status' of the taskObject
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        //set all the items to display in the tableView
        tableView.setItems(observableList);
    }
}
