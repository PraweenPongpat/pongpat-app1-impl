/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Praween Pongpat
 */

package baseline;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MainWindowController implements Initializable {

    public static Scanner input;
    public FileChooser fileChooser = new FileChooser();
    private ObservableList<TaskObject> observableList;

    @FXML
    private Button addNewTaskButton;
    @FXML
    private TableColumn<?, ?> descriptionColumn;
    @FXML
    private TableColumn<?, ?> dueDateColumn;
    @FXML
    private TableColumn<?, ?> isCompletedColumn;
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
    private TableView<?> tableView;
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
    //as of now that i can't fully figure out, i will plan according to use static
    //i should be able to NOT use static list, in an actual implementation

    //static list variable 'todoList' to ensure that there will only be one list across the program
    //static taskChecker, used to pass information as a flag for specific tasks

    @FXML
    void addNewTaskButtonPushed(ActionEvent event) {
        //this button should always be visible

        //to add a new scene
        //set the 'isAdding' to true. This will be the flag shows the different between Add or Edit
        //call the changeScene function, to change the scene to AddEditWindow.fxml
        //  note that when add a task, the AddEditWindow will not have any initializes value
    }

    @FXML
    void editTaskButtonPushed(ActionEvent event) {
        //this button should only be visible when any task is selected

        //if nothing is selected from the tableView
        //  set the errorDisplayLabel prompting user "you must select a task to proceed..."
        //  exit method, doing nothing else

        //to edit a task in a new scene
        //set 'isAdding' to false
        //set 'index' to getIndexOfSelectedItemInTableView function
        //  this means that for editing, index will always be 0 or greater
        //call the changeScene function, to change the scene to AddEditWindow.fxml
        //  note that when edit task, the AddEditWindow will initialize the existed information of the task
    }

    @FXML
    void makeTaskCompleteButtonPushed(ActionEvent event) {
        //this button should only be visible when selected the task that is incomplete

        //if nothing is selected from the tableView
        //  set the errorDisplayLabel prompting user "you must select a task to proceed..."
        //  exit method, doing nothing else

        //set 'index' to getIndexOfSelectedItemInTableView function
        //access the todoList with that index
        //set the isComplete of the taskObject to that index to true

        //refresh this scene again, or call the changeScene function passing the same .fxml
        //(to reload the updated list)
    }

    @FXML
    void makeTaskIncompleteButtonPushed(ActionEvent event) {
        //this button should only be visible when selected the task that is complete

        //if nothing is selected from the tableView
        //  set the errorDisplayLabel prompting user "you must select a task to proceed..."
        //  exit method, doing nothing else

        //set 'index' to getIndexOfSelectedItemInTableView function
        //access the todoList with that index
        //set the isComplete of the taskObject to that index to false

        //refresh this scene again, or call the changeScene function passing the same .fxml
        //(to reload the updated list)
    }

    @FXML
    void removeTaskButtonPushed(ActionEvent event) {
        //this button should only be visible when any task is selected

        //if nothing is selected from the tableView
        //  set the errorDisplayLabel prompting user "you must select a task to proceed..."
        //  exit method, doing nothing else

        //set 'index' to getIndexOfSelectedItemInTableView function
        //use .remove to remove that task out of the list

        //refresh this scene again, or call the changeScene function passing the same .fxml
        //(to reload the updated list)
    }

    @FXML
    void clearListButtonPushed(ActionEvent event) {
        //this button should be visible at all time

        //when the clearListButton is pushed
        //clear the data observableList (clear all data in tableView too)
        //access the list in ListWrapper
        //  clear() the list too
        //  we may have to refresh the scene (to refresh the table)

    }

    @FXML
    void displayAllRadioButtonPushed(ActionEvent event) {
        //this is the default displayMode when the scene is initialized

        //when displayMode 'all' is selected
        //clear all the data in the tableView
        //set up the data in the tableView with ALL elements in the list
    }

    @FXML
    void displayCompletedRadioButtonPushed(ActionEvent event) {
        //when displayMode 'completed' is selected
        //clear all the data in the tableView
        //set up the data in the tableView with only the TaskObject that has 'isCompleted' = true
    }

    @FXML
    void displayIncompleteRadioButtonPushed(ActionEvent event) {
        //when displayMode 'incomplete' is selected
        //clear all the data in the tableView
        //set up the data in the tableView with only the TaskObject that has 'isCompleted' = false
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
        //each item:    dueDate,description,<T/F>isCompleted      ---> use comma to separate the things in the object
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
        //each item:    dueDate,description,<T/F>isCompleted      ---> use comma to separate the things in the object
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

    private int getIndexOfSelectedItemInTableView(){
        //a todoList is static, we can access directly

        //to get the index of todoList from a selected item in tableView
        //since tableView has Selected Model classes already
        //use getSelectedItem on it

        //return the index
        return 0;
    }

    //this method may change!!! i haven't figured out yes how controllers are communicating
    private void changeScene(String fileNameFXML) {
        //this function is used to navigate/change scene
        //the function has to pre-load the scene information to the other scene to pass parameters to the next scene

        //create a Parent class 'parent', use FXML loader.load(getClass.getResource) just like the textbook
        //  specify the fileNameFXML into it
        //make a new Scene 'scene', set the seen with 'parent'

        //create a stage class 'window'
        //set the 'window' by setScene with 'scene'
        //show the window (run the actual scene changing)
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //the scene will always be re-initialize every time
        //this is needed, because the tableView needs to refresh for add/remove/edit

        //always initialize the errorDisplayLabel by set it to ""

        //hide buttons, until user pick/clicked at the item in the list
        //      this is optional, maybe I will just leave it, and display error through a label for user instead
        //set editTaskButton to not visible
        //set removeTaskButton to not visible
        //set makeTaskCompleteButton to not visible
        //set makeTaskIncompleteButton to not visible

        //initialize the 'fileChooser' is optional, maybe do it here if wanted to

        //set the observableList to the list that we have in the ListWrapper (same structure, same data)
        //      if list is used as reference, may not be able to set directly
        //      linearly traverse through the list, copy the data if needed
        //the observableList will be the list that communicates with the tableView, not the list in ListWrapper

        //need to add listener here, this is to ALWAYS detect the change in the tableView
        //when an object in the table is clicked, make those buttons visible (as needed)
        //if the selectedObject has 'isCompleted' to false (task is completed)
        //      set makeTaskIncompleteButton to visible
        //if the selectedObject has 'isCompleted' to true (task is incomplete)
        //      set makeTaskCompleteButton to visible

        //set the toggle group for radioButtons (should already init displaying 'all')
        //add 'displayAllRadioButton' to the toggleGroup 'displayModes'
        //add 'displayCompletedRadioButton' to the toggleGroup 'displayModes'
        //add 'displayIncompleteRadioButton' to the toggleGroup 'displayModes'

        //must initialize the tableView (use to regularly display tasks in the observableList)
        //set the column to display each object accordingly
        //column name "dueDate" is set to look for 'dueDate' of the taskObject
        //column name "description" is set to look for 'description' of the taskObject
        //column name "status" is set to look for 'isCompleted' of the taskObject
        //      if isCompleted is true, put "completed"
        //      if isCompleted is false, put "incomplete"
        //set all the items to display in the tableView
    }
}
