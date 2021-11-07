/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Praween Pongpat
 */

package baseline;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainWindowControllerTest {
    private final ListWrapper listWrapper = new ListWrapper();
    private ObservableList<TaskObject> observableList;
    MainWindowController mainWindowController = new MainWindowController();

    //helper function that will be re-use to generate dummy items in to the list
    public void addThingsToList(){
        //add things to the list, uses to test it later
        listWrapper.addToList(new TaskObject("1111-11-11","test description#1"));
        listWrapper.addToList(new TaskObject("1111-11-12","test description#2"));
        listWrapper.addToList(new TaskObject("1111-11-13","test description#3"));
        listWrapper.addToList(new TaskObject("1111-11-14","test description#4"));
        listWrapper.addToList(new TaskObject("1111-11-15","test description#5"));
        //make index3 and 4 as marked completed
        listWrapper.markItemAsCompleted(3);
        listWrapper.markItemAsCompleted(4);
    }

    @Test   //test according to requirements#1
    void atLastHundredItemTest (){
        //the validation of having a unique item will be tested separately
        //add more than 100 items and check if the item 101 or more exists on both actual object and listener object
        for(int i = 0; i<102; i++) {
            listWrapper.addToList(new TaskObject("1111-11-11","test description#"+i));
        }
        observableList = FXCollections.observableArrayList(listWrapper.getList());

        //the item #101 (passed at least 100) in both list should exist
        boolean actualSizeActualObject = listWrapper.getList().size() >= 100;
        assertTrue(actualSizeActualObject);
        boolean tableViewObjectSize = observableList.size()>=100;
        assertTrue(tableViewObjectSize);
    }

    @Test   //test according to requirements#10
    void displayAllTest() {
        //this is the test on observableList (since the actual list will not change)
        //that it displays All items
        //create 5 items in the list, 3 incomplete, 2 completed
        addThingsToList();

        //use the helper function to filter displayAll (used under displayAllRadioButtonPushed)
        List<TaskObject> tempList = mainWindowController.displayAllTasksFilterHelper(listWrapper.getList());
        observableList = FXCollections.observableArrayList(tempList);

        //since we are displaying all,  observable list as expected size of 5
        assertEquals(5,observableList.size());
    }

    @Test   //test according to requirements#12
    void displayCompletedOnlyTest() {
        //this is the test on observableList (since the actual list will not change)
        //that it displays Completed items only
        //create 5 items in the list, 3 incomplete, 2 completed
        addThingsToList();

        //use the helper function to filter displayAll (used under displayAllRadioButtonPushed)
        List<TaskObject> tempList = mainWindowController.displayCompleteTasksFilterHelper(listWrapper.getList());
        observableList = FXCollections.observableArrayList(tempList);

        //since we are displaying completed only,  observable list as expected size of 2
        assertEquals(2,observableList.size());
    }

    @Test   //test according to requirements#11
    void displayIncompleteOnlyTest() {
        //this is the test on observableList (since the actual list will not change)
        //that it displays Completed items only
        //create 5 items in the list, 3 incomplete, 2 completed
        addThingsToList();

        //use the helper function to filter displayAll (used under displayAllRadioButtonPushed)
        List<TaskObject> tempList = mainWindowController.displayIncompleteTasksFilterHelper(listWrapper.getList());
        observableList = FXCollections.observableArrayList(tempList);

        //since we are displaying incomplete only,  observable list as expected size of 3
        assertEquals(3,observableList.size());
    }

    @Test   //test according to requirements#13
    void safeFileToLocalSpaceTest() {
        //this is the test on observableList (since the actual list will not change)
        //that it displays Completed items only
        //create 5 items in the list, 3 incomplete, 2 completed
        addThingsToList();

        //create a file pointing to data folder
        File file = new File("data\\saveTest.txt");
        //test on helper method that is used when SaveButtonPushed
        mainWindowController.saveToLocalDriveHelper(file,listWrapper.getList());

        //expected that the file will exist in data directory
        assertTrue(Files.exists(Path.of("data\\saveTest.txt")));
    }

    @Test   //test according to requirements#14
    void loadFileFromLocalSpaceTest() {
        //to test the load file, first, save another file into data directory
        //then read it back to compare with the original data

        addThingsToList();
        listWrapper.addToList(new TaskObject("1111-11-16","test description#6"));
        //save the file to local storage
        File file = new File("data\\loadTest.txt");
        //save the file first
        mainWindowController.saveToLocalDriveHelper(file,listWrapper.getList());

        //now that the file has been saved, test the helper function, load it back
        //store the list into another tempList
        List<TaskObject> tempList = mainWindowController.loadFromLocalDrive(file);

        //check if both list has the same size
        assertEquals(listWrapper.getList().size(),tempList.size());
        //check if both list has the same information inside
        for(int i = 0; i<tempList.size();i++) {
            assertEquals(listWrapper.getList().get(i).getDueDate(),tempList.get(i).getDueDate());
            assertEquals(listWrapper.getList().get(i).getDescription(),tempList.get(i).getDescription());
            assertEquals(listWrapper.getList().get(i).getStatus(),tempList.get(i).getStatus());
        }
    }
}