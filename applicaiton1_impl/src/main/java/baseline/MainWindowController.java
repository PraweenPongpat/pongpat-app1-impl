/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Praween Pongpat
 */

package baseline;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

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
    @FXML
    private RadioButton displayAllSortByDateRadioButton;

    //needed variables
    private final FileChooser fileChooser = new FileChooser();
    private ObservableList<TaskObject> observableList;
    private ListWrapper listWrapper = new ListWrapper();
    private int tempIndex = -1;
    private File file;

    @FXML
    void addNewTaskButtonPushed() throws IOException {
        //this button should always be visible

        //to add a new scene
        //set the 'isAdding' to true. This will be the flag shows the different between Add or Edit
        listWrapper.setAdding(true);
        //  the actual adding/editing is in AddEditWindowController class
        //call the changeScene function, to change the scene to AddEditWindow.fxml
        //  note that when add a task, the AddEditWindow will not have any initializes value
        changeScene();
    }

    @FXML
    void editTaskButtonPushed() {
        //this button should only be visible when any task is selected

        //if nothing is selected from the tableView, the index will be less than zero
        if (listWrapper.getIndex() < 0 || tempIndex < 0) {
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
            //  the actual adding/editing is in AddEditWindowController class
            //call the changeScene function, to change the scene to AddEditWindow.fxml
            //  note that when edit task, the AddEditWindow will initialize the existed information of the task
            try {
                changeScene();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void makeTaskCompleteButtonPushed() {
        //this button should only be visible when selected the task that is incomplete

        if (listWrapper.getIndex() < 0 || tempIndex < 0) {
            //  set the errorDisplayLabel prompting user "you must select a task to proceed..."
            errorDisplayLabel.setText("you must select a task to change its status...");
            //  exit method, doing nothing else
        } else {
            //  set the errorDisplayLabel to empty String, since no error occur
            errorDisplayLabel.setText("");

            //with the current 'index' from ListWrapper class variable
            //access the todoList with that index
            //set the "status" of the taskObject to that index to "completed"
            listWrapper.markItemAsCompleted(tempIndex);

            //refresh this scene again, or call the changeScene function passing the same .fxml
            //(to reload the updated list)
            observableList.clear();
            observableList = FXCollections.observableArrayList(listWrapper.getList());
            tableView.setItems(observableList);
        }
    }

    @FXML
    void makeTaskIncompleteButtonPushed() {
        //this button should only be visible when selected the task that is complete

        //if nothing is selected from the tableView
        if (listWrapper.getIndex() < 0 || tempIndex < 0) {
            //  set the errorDisplayLabel prompting user "you must select a task to proceed..."
            errorDisplayLabel.setText("you must select a task to change its status...");
            //  exit method, doing nothing else
        } else {
            //  set the errorDisplayLabel to empty String, since no error occur
            errorDisplayLabel.setText("");

            //with the current 'index' from ListWrapper class variable
            //access the todoList with that index
            //set the "status" of the taskObject to that index to "incomplete"
            listWrapper.markItemAsIncomplete(tempIndex);

            //refresh this scene again, or call the changeScene function passing the same .fxml
            //(to reload the updated list)
            observableList.clear();
            observableList = FXCollections.observableArrayList(listWrapper.getList());
            tableView.setItems(observableList);
        }
    }

    @FXML
    void removeTaskButtonPushed() {
        //this button should only be visible when any task is selected

        //if nothing is selected from the tableView
        if (listWrapper.getIndex() < 0 || tempIndex < 0) {
            //  set the errorDisplayLabel prompting user "you must select a task to proceed..."
            errorDisplayLabel.setText("you must select a task to remove something...");
            //  exit method, doing nothing else
        } else {
            //  set the errorDisplayLabel to empty String, since no error occur
            errorDisplayLabel.setText("");

            //with the current 'index' from ListWrapper class variable
            // .remove to remove that task out of the list
            listWrapper.removeItemFromList(tempIndex);
            observableList.remove(tempIndex);


            //refresh this scene again, or call the changeScene function passing the same .fxml
            //(to reload the updated list)
        }
    }

    @FXML
    void clearListButtonPushed() {
        //this button should be visible at all time

        //when the clearListButton is pushed
        //clear the data observableList (clear all data in tableView too)
        observableList.clear();
        //access the list in ListWrapper and clear it with
        listWrapper.clearList();

        //we may have to refresh the scene (to refresh the table)
    }

    @FXML
    void sortByDateButtonPushed() {
        //when displayMode 'all' but the data in the list will be sorted by date from min_to_max
        //clear the data in teh tableView
        observableList.sort(Comparator.comparing(TaskObject::getDueDate));
    }

    @FXML
    void displayAllRadioButtonPushed() {
        //this is the default displayMode when the scene is initialized

        //when displayMode 'all' is selected
        //clear all the data in the tableView
        List<TaskObject> tempList = displayAllTasksFilterHelper(listWrapper.getList());
        observableList = FXCollections.observableArrayList(tempList);

        //set up the data in the tableView with ALL elements in the list
        //set all the items to display in the tableView
        tableView.setItems(observableList);
    }

    @FXML
    void displayCompletedRadioButtonPushed() {
        //when displayMode 'completed' is selected
        //clear all the data in the tableView
        observableList.clear();
        //set up the data in the tableView with only the TaskObject that has 'status' = "completed"
        List<TaskObject> tempList = displayCompleteTasksFilterHelper(listWrapper.getList());
        observableList = FXCollections.observableArrayList(tempList);
        tableView.setItems(observableList);
    }

    @FXML
    void displayIncompleteRadioButtonPushed() {
        //when displayMode 'completed' is selected
        //clear all the data in the tableView
        observableList.clear();
        //set up the data in the tableView with only the TaskObject that has 'status' = "incomplete"
        List<TaskObject> tempList = displayIncompleteTasksFilterHelper(listWrapper.getList());
        observableList = FXCollections.observableArrayList(tempList);
        tableView.setItems(observableList);
    }

    @FXML
    void saveButtonPushed() {
        //when saveButton is pushed
        //uses a FileChooser to get the selected address from user
        //create a file that is pointing to the result of file choosing
        fileChooser.setTitle("save file");
        fileChooser.setInitialFileName("testFile.txt");
        file = fileAddressFromFileChooserToSave();
        //if file is null, means that the user didn't pick the file (cancel the choosing)
        if (file == null) {
            //      end method with return statement
            //display error that file name cannot be null
            errorDisplayLabel.setText("save failed, file name must be filled properly");
            //otherwise, means the user wants to make a file (saving)
        } else {
            //set the error to be empty string, since no error occur
            errorDisplayLabel.setText("");
            saveToLocalDriveHelper(file, listWrapper.getList());
            //create a writer (Formatter, BufferWriter works too, leave as options when implement)
            System.out.println("file is saved");
        }
    }

    @FXML
    void loadButtonPushed() {
        //when loadButton is pushed
        //uses a FileChooser to get the selected address from user
        //set the 'file' for a result from using fileChooser
        fileChooser.setTitle("load file");
        file = fileAddressFromFileChooserToLoad();
        //if file is null, means that the user didn't pick the file (cancel the choosing)
        if (file == null) {
            //      end method with return statement
            errorDisplayLabel.setText("load failed, try again");
        }
        //otherwise, means the file is NOT null...
        else {
            //create a Scanner and link the Scanner to te path received from FileChooser
            //clear the actual list
            listWrapper.getList().clear();
            observableList.clear();
            //set the actual list to the newly read data
            List<TaskObject> tempList = loadFromLocalDrive(file);
            if(tempList.isEmpty()){
                errorDisplayLabel.setText("the file is empty, or contaminated: file cannot be opened");
                return;
            }
            for (TaskObject taskObject : tempList) {
                listWrapper.addToList(taskObject);
            }
            //set the errorLabel back to emptyString
            errorDisplayLabel.setText("");
            //call initialize class again, to refresh the scene
            observableList = FXCollections.observableArrayList(listWrapper.getList());
            //this is the update the tableView to the loaded list
            tableView.setItems(observableList);
            System.out.println("file is loaded, and replaced the current GUI already");
        }
    }

    public File fileAddressFromFileChooserToSave() {
        //open another stage to get the address
        Stage stage = (Stage) errorDisplayLabel.getScene().getWindow();
        return fileChooser.showSaveDialog(stage);
    }

    public File fileAddressFromFileChooserToLoad() {
        //open another stage to get the address
        Stage stage = (Stage) errorDisplayLabel.getScene().getWindow();
        return fileChooser.showOpenDialog(stage);
    }

    //this method may change!!! i haven't figured out yes how controllers are communicating
    private void changeScene() throws IOException {
        //this function is used to navigate/change scene, (as a sender side)
        //the function has to pre-load the scene information to the other scene to pass parameters to the next scene

        //create a new FXMLLoader according to the fileNameFXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddEditWindow.fxml"));
        //surround try/catch to .load
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("something is wrong, parent is null.");
        }
        //create a controller to pointing to the receiving class, @initialize will partially set up the scene
        AddEditWindowController controller = loader.getController();
        //pass the listWrapper object to the receiving class
        listWrapper.setIndex(tempIndex);
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
        this.listWrapper = listWrapper;
        tempIndex = -1;
        //clear the observableList to clear the tableView (to refresh the tableView)
        observableList.clear();
        //set an observableList to the actual listWrapper and display it
        observableList = FXCollections.observableArrayList(listWrapper.getList());
        tableView.setItems(observableList);
    }

    public void initialize() {
        //the scene will always be re-initialize every time
        //this is needed, because the tableView needs to refresh for add/remove/edit

        //always initialize the errorDisplayLabel by set it to ""
        errorDisplayLabel.setText("");

        //initialize the 'fileChooser' is optional, maybe do it here if wanted to
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("text files", "*.txt"));

        //set the observableList to the list that we have in the ListWrapper (same structure, same data)
        //      if list is used as reference, may not be able to set directly
        //      linearly traverse through the list, copy the data if needed
        //the observableList will be the list that communicates with the tableView, not the list in ListWrapper
        observableList = FXCollections.observableArrayList(listWrapper.getList());

        //need to add listener here, this is to ALWAYS detect the change in the tableView
        //when an object in the table is clicked, make those buttons visible (as needed)
        //if the selectedObject has 'status' to "completed"
        //      set makeTaskIncompleteButton to visible
        //if the selectedObject has 'status' to "incomplete"
        //      set makeTaskCompleteButton to visible
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    tempIndex = observableList.indexOf(tableView.getSelectionModel().getSelectedItem());
                    listWrapper.setIndex(tempIndex);
                });

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

    //method helpers extracted to test the displayALL
    //display all will display everything in the list without any condition
    public List<TaskObject> displayAllTasksFilterHelper(List<TaskObject> list) {
        return new ArrayList<>(list);
    }

    //method helpers extracted to test the displayComplete Only
    //this will display only ones that has status as "completed"
    public List<TaskObject> displayCompleteTasksFilterHelper(List<TaskObject> list) {
        List<TaskObject> res = new ArrayList<>();
        for (TaskObject taskObject : list) {
            if (taskObject.getStatus().equals("completed")) {
                res.add(taskObject);
            }
        }
        return res;
    }

    //method helpers extracted to test the displayIncomplete Only
    //this will display only ones that has status as "incomplete"
    public List<TaskObject> displayIncompleteTasksFilterHelper(List<TaskObject> list) {
        List<TaskObject> res = new ArrayList<>();
        for (TaskObject taskObject : list) {
            if (taskObject.getStatus().equals("incomplete")) {
                res.add(taskObject);
            }
        }
        return res;
    }

    //method helpers extracted to test the saveToLocalDrive
    public void saveToLocalDriveHelper(File file, List<TaskObject> list) {
        try (Formatter writer = new Formatter(file.toString())) {
            //write in the following format
            //****************format .txt*********************
            //each item:    dueDate ### description ### status ###N ewLine      ---> use "###" to separate the things in the object
            // ...
            //************************************************
            for (TaskObject taskObject : list) {
                writer.format("%s ### %s ### %s ### %n",
                        taskObject.getDueDate(),
                        taskObject.getDescription(),
                        taskObject.getStatus());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("FILE CANNOT BE SAVED! something is wrong");
        }
    }

    //method helpers extracted to test to loadFromLocalDrive
    public List<TaskObject> loadFromLocalDrive(File file) {
        List<TaskObject> res = new ArrayList<>();

        try (Scanner input = new Scanner(file)) {
            //start reading the file
            //loading will reflect in the saving format
            //****************format .txt*********************
            //each item:    dueDate### description### status### NewLine      ---> use " ### " to separate the things in the object
            // ...
            //************************************************
            //access the list on ListWrapper class, clear all the data in the list using clear()
            //read one while line at a time
            //  each line read from rawData, parse it with split comma
            //  create a taskObject from the parsed data
            //  add the taskObject into the list
            String temp;
            while (input.hasNext()) {
                temp = input.nextLine();
                String[] tempArr = temp.split(" ### ");
                if(tempArr.length!=3) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                res.add(new TaskObject(tempArr[0], tempArr[1], tempArr[2]));
            }
            return res;
        } catch (FileNotFoundException | ArrayIndexOutOfBoundsException e) {
            System.out.println("something is wrong, load unsuccessfully");
        }
        return Collections.emptyList();
    }
}
