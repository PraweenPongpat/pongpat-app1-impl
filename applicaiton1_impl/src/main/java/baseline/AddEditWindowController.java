/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Praween Pongpat
 */

package baseline;

import javafx.event.ActionEvent;
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

public class AddEditWindowController {
    @FXML
    private Button cancelButton;
    @FXML
    private Button doneButton;
    @FXML
    private Label topicLabel;
    @FXML
    private Label errorDisplayLabel;
    @FXML
    private Label promptDateLabel;
    @FXML
    private TextArea taskInfoTextArea;
    @FXML
    private DatePicker taskDueDateDatePicker = new DatePicker();

    private LocalDate date;

    private ListWrapper listWrapper = new ListWrapper();
    private int index;

    @FXML
    void cancelButtonPushed(ActionEvent event) {
        //if cancel button is pushed, not return new thing to the MainWindow.fxml scene
        // call method changeScene to change the Scene
        changeScene("MainWindow.fxml");
    }

    @FXML
    void doneButtonPushed(ActionEvent event) {
        //when the doneButton is pushed

        //need to validate 'dueDate' before returning as editing/adding to a taskObject (if exists)

        //read the text in textField, or datePicker
        //  read due dueDate <<datePicker takes over taskDueDateTextField>>
        //  the LocalDate from datePicker is NOT null
        //      read the LocalDate from datePicker
        //      convert the local date into string format as required
        //      format is YYYY-MM-DD as a string (will be stored in the ListWrapper object as a string)
        //      Local date from datePicker will already be tested from the API, to test this, use the data in label
        String newDate;
         if(date == null){
             newDate = "";
         } else {
             newDate = date.toString();
         }

        //  read the text in the textArea
        String readTextArea = taskInfoTextArea.getText();
        //      we also need to validate the string length (1-256 chars)
        if(!descriptionValidator(readTextArea)) {
            //      if string length is greater than 256 char
            //          set errorDisplayLabel prompting user "maximum description is 256 characters"
            //          exit the method without doing anything else
            //      if the string is empty
            //          set errorDisplayLabel prompting user "description area must be filled"
            //          exit the method without doing anything else
            //      otherwise, keep proceeding
            errorDisplayLabel.setText("description must be filled with 1-256 characters");
            return;
        }
        //***this point, all validation must be valid***

        //check the ListWrapper, for isAdding
        //  if isAdding is true, means we are adding a new taskObject to the list
        if(listWrapper.getIsAdding()) {
            //      create a new taskObject and add the task to the list
            listWrapper.getList().add(new TaskObject(newDate,readTextArea));
        }
        //  else, means we are editing
        else {
            //      access the 'index' in the ListWrapper (represent the index if the list we are editing)
            //      use setDueDate to set an edited date as a String in that index
            listWrapper.getList().get(index).setDueDate(newDate);
            //      use setDescription to set edited description as a String in that index
            listWrapper.getList().get(index).setDescription(readTextArea);
        }
        // call method changeScene to change the Scene
        changeScene("MainWindow.fxml");
    }

    @FXML
    void datePickerSelected(ActionEvent event) {
        //the onAction event when user selected a date from date picker
        //set date to the value
        date = taskDueDateDatePicker.getValue();
    }

    public boolean descriptionValidator (String string) {
        //description must be 1-256 chars, cannot be empty
        //first, check if the string is null or empty
        if(string.equals("")) {
            return false;
        } else return string.length() >= 1 && string.length() <= 256;
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fileNameFXML));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("something is wrong, parent is null.");
        }

        MainWindowController controller = loader.getController();
        controller.initializeListWrapper(listWrapper);
        assert root != null;
        Scene mainWindowScene = new Scene(root);

        Stage window = (Stage) errorDisplayLabel.getScene().getWindow();
        window.setScene(mainWindowScene);
        window.show();

    }

    public void initializeListWrapper(ListWrapper listWrapper, int index) {
        //this method will be used (as a receiving side) between scene changing
        this.listWrapper = listWrapper;
        this.index = index;
        if(listWrapper.getIsAdding()) {
            setUpExistedText("","");
        } else {
            setUpExistedText(listWrapper.getList().get(index).getDueDate(),
                    listWrapper.getList().get(index).getDescription());
        }
    }

    public void setUpExistedText(String dueDateString, String descriptionString) {
        //set up the text in the boxes when editing
        if(!dueDateString.equals("")){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate tempDate = LocalDate.parse(dueDateString,dateTimeFormatter);
            taskDueDateDatePicker.setValue(tempDate);
        }

        if(!descriptionString.equals("")) {
            taskInfoTextArea.setText(descriptionString);
        }
        promptDateLabel.setText(dueDateString);
    }

    public void initialize() {
        //always initialize errorDisplayLabel to ""
        errorDisplayLabel.setText("");
        promptDateLabel.setText("");
        //always initialize the topic label
        //  check isAdding
        //  if true     :   set 'topicLabel' as "Add a task Information"
        if(listWrapper.getIsAdding()) {
            topicLabel.setText("Add a task Information");
        }
        //  if false    :   set 'topicLabel' as "Edit a task Information"
        else {
            topicLabel.setText("Edit a task Information");
        }
        taskDueDateDatePicker.setConverter(new StringConverter<LocalDate>()
        {
            private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate localDate)
            {
                if(localDate==null)
                    return "";
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString)
            {
                if(dateString==null || dateString.trim().isEmpty())
                {
                    return null;
                }
                return LocalDate.parse(dateString,dateTimeFormatter);
            }
        });
    }
}
