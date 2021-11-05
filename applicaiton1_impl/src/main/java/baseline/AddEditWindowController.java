/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Praween Pongpat
 */

package baseline;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEditWindowController implements Initializable {
    @FXML
    private Button cancelButton;
    @FXML
    private Button doneButton;
    @FXML
    private Label topicLabel;
    @FXML
    private Label errorDisplayLabel;
    @FXML
    private TextField taskDueDateTextField;
    @FXML
    private TextArea taskInfoTextArea;
    @FXML
    private DatePicker taskDueDateDatePicker;

    @FXML
    void cancelButtonPushed(ActionEvent event) {
        //if cancel button is pushed, not return new thing to the MainWindow.fxml scene
        // call method changeScene to change the Scene
    }

    @FXML
    void doneButtonPushed(ActionEvent event) {
        //when the doneButton is pushed

        //need to validate 'dueDate' before returning as editing/adding to a taskObject (if exists)

        //read the text in textField, or datePicker
        //  read due dueDate <<datePicker takes over taskDueDateTextField>>
        //  if the LocalDate from datePicker is NOT null
        //      read the LocalDate from datePicker
        //      convert the local date into string format as required
        //      format is YYYY-MM-DD as a string (will be stored in the ListWrapper object as a string)
        //      Local date from datePicker will already be tested from the API
        //  else if the LocalDate from datePicker is null, and the taskDueDateTextField is NOT null
        //      we need to do validation on the string using the validator method
        //      the method takes in a string read from textField, return a boolean represent validation result
        //  else if both datePicker is null, textField is null
        //      this is okay, the dueDate is optional

        //  if validationResult is false
        //      set errorDisplayLabel prompting user the format date "date format must be YYYY-MM-DD, including '-'"
        //      exit method without doing anything else
        //  otherwise, keep proceeding

        //  read the text in the textArea
        //      we also need to validate the string length (1-256 chars)
        //      if string length is greater than 256 char
        //          set errorDisplayLabel prompting user "maximum description is 256 characters"
        //          exit the method without doing anything else
        //      if the string is empty
        //          set errorDisplayLabel prompting user "description area must be filled"
        //          exit the method without doing anything else
        //      otherwise, keep proceeding
        //***this point, all validation must be valid***

        //check the ListWrapper, for isAdding
        //  if isAdding is true, means we are adding a new taskObject to the list
        //      create a new taskObject and add the task to the list
        //  else, means we are editing
        //      access the 'index' in the ListWrapper (represent the index if the list we are editing)
        //      use setDueDate to set an edited date as a String in that index
        //      use setDescription to set edited description as a String in that index

        // call method changeScene to change the Scene
    }

    public boolean taskDueDateTextFieldValidator(String validatingDate) {
        //as prompted in the textField, user need to put '-' as well

        //from validatingDate, split it with '-' to Strings[]
        //element 0, MUST be a 4 digit positive integer that is less than 2022 (next year)
        //element 1, MUST be a positive integer that is from 1 to 12 (months)
        //element 2, MUST be a positive integer, check element2 (number of days depend on the month)
        //      if element 2 is 1,3,5,7,8,10,12     element 3 will be from 1-31
        //      if element 2 is 4,6,9,11            element 3 will be from 1-30
        //      if element 2 is 2                   element 3 will be from 1-28 (max at 28 for simplicity)

        //if all validating passed, return true
        //otherwise, return false
        return false;
    }

    //this method may change!!! I haven't figured out yes how controllers are communicating
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
        //always initialize errorDisplayLabel to ""

        //always initialize the topic label
        //  check isAdding
        //  if true     :   set 'topicLabel' as "Add a TodoList Information"
        //  if false    :   set 'topicLabel' as "Edit a TodoList Information"

        //these initializations will occur when 'edit' portion is passed.
        //check isAdding is false (to make sure that it is editing), in the ListWrapper
        //  always set the date in date picker to NULL
        //  set the text in taskDueDateTextField using 'dueDate' in the taskObject that was passed in
        //  set the text in taskInfoTextArea using 'description' in the taskObject that was passed in
    }
}
