/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Praween Pongpat
 */

package baseline;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AddEditWindowController {
    @FXML
    private Button cancelButton;
    @FXML
    private Button doneButton;
    @FXML
    private Label topicLabel;
    @FXML
    private Label clearDateLabel;
    @FXML
    private Label errorDisplayLabel;
    @FXML
    private Label promptDateLabel;
    @FXML
    private TextArea taskDescriptionTextArea;
    @FXML
    private DatePicker taskDueDateDatePicker;

    //declare variables  as needed
    private LocalDate date;
    private ListWrapper listWrapper = new ListWrapper();
    private int index = -1;

    @FXML
    void cancelButtonPushed() {
        //if cancel button is pushed, not return new thing to the MainWindow.fxml scene
        // call method changeScene to change the Scene
        changeScene();
    }

    @FXML
    void doneButtonPushed() {
        //when the doneButton is pushed
        //need to validate 'dueDate' before returning as editing/adding to a taskObject (if exists)
        //read the text in textField, or datePicker (will be onAction)
        //      LocalDate from datePicker will already be tested from the API, to test this, use the data in label
        String newDate;
        if (date == null){
            newDate = "";
        } else {
             newDate = date.toString();
        }
         //make sure the format of the date is correct
        if(!validateDateFormat(newDate)) {
            errorDisplayLabel.setText("the date format is wrong!");
            return;
        }
        //  read the text in the textArea
        String readTextArea = taskDescriptionTextArea.getText();
        //  we also need to validate the string length (1-256 chars), cannot be empty
        //  validate using validator method
        if(!descriptionValidator(readTextArea)) {
            //if the validation failed, display the error through the label, don't do anything else
            errorDisplayLabel.setText("description must be filled with 1-256 characters");
            return;
        }
        //the description cannot have Newline, because the save/load system will be hard to keep up
        //check if the string read from textArea has newLine
        if (readTextArea.contains("\n"))
        {
            errorDisplayLabel.setText("description cannot have NewLine");
            return;
        }
        //***this point, all validation must be valid***

        //#########################################
        //check if the item already existed
        //if the item existed, display error message and don't do anything else
        if(itemExistingValidation(listWrapper.getList(),newDate,readTextArea)){
            errorDisplayLabel.setText("this exact task already existed in your list");
            return;
        }

        //check the ListWrapper, for isAdding
        //  if isAdding is true, means we are adding a new taskObject to the list
        if(listWrapper.getIsAdding()) {
            //      create a new taskObject and add the task to the list
            listWrapper.addToList(new TaskObject(newDate,readTextArea));
        }
        //  else, means we are editing
        else {
            //      access the 'index' in the ListWrapper (represent the index if the list we are editing)
            //      use setDueDate to set an edited date as a String in that index
            listWrapper.editDueDate(index,newDate);
            //      use setDescription to set edited description as a String in that index
            listWrapper.editDescription(index,readTextArea);
        }
        // call method changeScene to change the Scene
        changeScene();
    }

    @FXML
    void datePickerSelected() {
        //the onAction event when user selected a date from date picker
        //set date to the value
        date = taskDueDateDatePicker.getValue();
    }

    public boolean descriptionValidator (String string) {
        //description must be 1-256 chars, cannot be empty
        //first, check if the string is empty string
        if(string.equals("")) {
            return false;
        } //check the length of the string, must be 1-256 (inclusive)
        else return string.length() >= 1 && string.length() <= 256;
    }

    private void changeScene() {
        //this function is used to navigate/change scene, (as a sender side)
        //the function has to pre-load the scene information to the other scene to pass parameters to the next scene

        //create a new FXMLLoader according to the fileNameFXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        //surround try/catch to .load
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            System.out.println("something is wrong, parent is null.");
        }
        //create a controller to pointing to the receiving class, @initialize will partially set up the scene
        MainWindowController controller = loader.getController();
        //pass the listWrapper object to the receiving class
        controller.initializeListWrapper(listWrapper);
        //create a new scene
        assert root != null;
        Scene mainWindowScene = new Scene(root);
        //get this stage information from (any control in the scene), set the stage and show it
        Stage window = (Stage) errorDisplayLabel.getScene().getWindow();
        window.setScene(mainWindowScene);
        window.show();

    }

    public void initializeListWrapper(ListWrapper listWrapper) {
        //this method will be used (as a receiving side) between scene changing
        //set the listWrapper object
        this.listWrapper = listWrapper;
        //set the index
        this.index = listWrapper.getIndex();

        //check if operating Adding
        //when isAdding is true, mean we are adding, set no initial text
        //set topicLabel to "Add a task Information"
        if(listWrapper.getIsAdding()) {
            promptDateLabel.setText("");
            topicLabel.setText("Add a task Information");
        } else {
            //otherwise, means we are editing, set the initial text to the existed data
            //set topicLabel to "Edit a task Information"
            //set visibility of clearDateLabel to be visible
            setUpExistedText(listWrapper.getList().get(index).getDueDate(),
                    listWrapper.getList().get(index).getDescription());
            topicLabel.setText("Edit a task Information");
            clearDateLabel.setVisible(true);
        }
    }
    //a helper method used to set up existed data
    public void setUpExistedText(String dueDateString, String descriptionString) {
        //set up the text in the boxes when editing
        //set up existed description
        //prompt the label wheat is a current date selected
        taskDescriptionTextArea.setText(descriptionString);
        promptDateLabel.setText(dueDateString);
    }

    //this method is used for testing purposes
    public boolean validateDateFormat(String date) {
        //if the date is not given, date will be empty string, which is fine
        if(date.equals(""))
            return true;
        //if the date existed, is has to be all numbers, xxxx-xx-xx format
        String[] result = date.split("-");
        if (result[0].length() == 4 && result[1].length() == 2 && result[2].length() == 2) {
            try {
                for (String s : result) {
                    Integer.parseInt(s);
                }
                return true;
            } catch (NumberFormatException e){
                return false;
            }
        }
        return false;
    }

    //this method is to validate if the item with exact same dueDate and description already exists
    public boolean itemExistingValidation(List<TaskObject> list, String dueDateString, String descriptionString) {
        //check if the item already existed
        //if the item existed, display error message and don't do anything else
        for (TaskObject taskObject : list) {
            if (taskObject.getDueDate().equals(dueDateString)
                    && taskObject.getDescription().equals(descriptionString)) {
                return true;
            }
        }
        return false;
    }

    public void initialize() {
        //always initialize errorDisplayLabel to ""
        errorDisplayLabel.setText("");
        //always initialize promptDateLabel to ""
        promptDateLabel.setText("");
        //always initialize clearDateLabel to not visible, assuming adding is prior (visible it when ensured is editing)
        clearDateLabel.setVisible(false);

        //initialize the datePicker format to YYYY-MM-DD as required
        taskDueDateDatePicker.setConverter(new StringConverter<>() {
            //set the pattern to DateTimeFormatter
            private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null)
                    return "";
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString, dateTimeFormatter);
            }
        });
    }
}
