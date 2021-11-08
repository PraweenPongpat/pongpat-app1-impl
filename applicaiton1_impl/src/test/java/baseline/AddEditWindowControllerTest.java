/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Praween Pongpat
 */

package baseline;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddEditWindowControllerTest {

    private final ListWrapper listWrapper = new ListWrapper();
    AddEditWindowController addEditWindowController = new AddEditWindowController();

    @Test   //test according to requirements#2
    void descriptionTest () {
        //this is a helper method just to validate the description length
        //i used this in within the doneButtonPushed

        //empty string, expected false
        assertFalse(addEditWindowController.descriptionValidator(""));
        //between 1-256, expected true
        assertTrue(addEditWindowController.descriptionValidator("AAA"));
        //longer than 256, expected false
        assertFalse(addEditWindowController.descriptionValidator("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
    }

    @Test   //test according to requirements#3
    void dateFormatTest () {
        //this is a helper method just to validate the format of the date
        //input date will automatically be tested valid from using datePicker API
        //only need to test how the information is parsed and stored
        //I used this in within the doneButtonPushed
        //expected false, wrong format
        assertFalse(addEditWindowController.validateDateFormat("AA-AA-AAAA"));
        assertFalse(addEditWindowController.validateDateFormat("01-11-1111"));
        //format is correct, but not numbers, expected false
        assertFalse(addEditWindowController.validateDateFormat("AAAA-AA-AA"));
        //correct format, all numbers, expected true
        assertTrue(addEditWindowController.validateDateFormat("1111-11-11"));
        //empty String (given in the program when datePicker is not picked), expected true
        assertTrue(addEditWindowController.validateDateFormat(""));
    }

    @Test   //test according to requirements#1
    void uniqueItemTest() {
        //this is a helper method just to validate that items must be unique
        //I used this in within the doneButtonPushed
        //add a dummy item in to a list
        listWrapper.addToList(new TaskObject("1111-11-11","sample task1"));
        listWrapper.addToList(new TaskObject("1111-11-12","sample task2"));

        //attempt to add an existing item to the list
        //the expected value will be true, since the item already existed
        assertTrue(addEditWindowController.itemExistingValidation(
                listWrapper.getList(),"1111-11-11","sample task1",0,true));

        //the validation for non-existed item will have expected value to be false
        assertFalse(addEditWindowController.itemExistingValidation(
                listWrapper.getList(),"1111-11-13","sample task3",0,true));
    }

}