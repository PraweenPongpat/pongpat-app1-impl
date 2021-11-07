/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Praween Pongpat
 */

package baseline;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListWrapperTest {
    ListWrapper listWrapper = new ListWrapper();

    @Test   //test according to requirements#4
    void addItemToListTest() {
        //this is a helper method used when adding a task to the list
        //used in AddEditWindowController, when doneButtonPushed
        //the data is already validated
        listWrapper.addToList(new TaskObject("1111-11-11","test1"));
        assertEquals(1,listWrapper.getList().size());
        listWrapper.addToList(new TaskObject("1111-11-12","test2"));
        assertEquals(2,listWrapper.getList().size());
    }

    @Test   //test according to requirements#5
    void removeItemFromList() {
        //this is a helper method used when removing a data from list
        //used in MainWindowController, when removeButtonPushed
        listWrapper.addToList(new TaskObject("1111-11-11","test1"));
        listWrapper.addToList(new TaskObject("1111-11-12","test2"));

        //the expected size 2 before removing
        assertEquals(2,listWrapper.getList().size());

        //now, remove index1 from the list
        listWrapper.removeItemFromList(1);
        //the expected size is now 1
        assertEquals(1,listWrapper.getList().size());
    }

    @Test   //test according to requirements#6
    void clearAllItemsFromListTest() {
        //this is a helper method used when clearing All data in the list
        //used in MainWindowController, when clearButtonPushed
        listWrapper.addToList(new TaskObject("1111-11-11","test1"));
        listWrapper.addToList(new TaskObject("1111-11-12","test2"));

        //the size is expected not to be 0, expected size 2
        assertEquals(2,listWrapper.getList().size());

        //now, clear the list
        listWrapper.clearList();
        //expected size is 0
        assertEquals(0,listWrapper.getList().size());
    }

    @Test   //test according to requirements#8
    void editDueDateOfItemsTest() {
        //this is a helper method used when editing a dueDate
        //used in AddEditWindowController, when doneButtonPushed
        listWrapper.addToList(new TaskObject("1111-11-11","test1"));
        listWrapper.addToList(new TaskObject("1111-11-12","test2"));

        //edit a dueDate of index0 to 2222-22-22
        listWrapper.editDueDate(0,"2222-22-22");
        //test with the expected value
        assertEquals("2222-22-22",listWrapper.getList().get(0).getDueDate());
    }

    @Test    //test according to requirements#7
    void editDescriptionOfItemsTest() {
        //this is a helper method used when editing a description
        //used in AddEditWindowController, when doneButtonPushed
        listWrapper.addToList(new TaskObject("1111-11-11","test1"));
        listWrapper.addToList(new TaskObject("1111-11-12","test2"));

        //edit a dueDate of index1 to test2test2test2
        listWrapper.editDueDate(1,"test2test2test2");
        //test with the expected value
        assertEquals("test2test2test2",listWrapper.getList().get(1).getDueDate());
    }

    @Test   //test according to requirements#9
    void changeStatusOfItemTest() {
        //this is a helper method used when changing a status of item in the list
        //used in MainWindowController, when mark completed button is pushed
        listWrapper.addToList(new TaskObject("1111-11-11","test1"));

        //default is expected as "incomplete", we will mark it as complete
        listWrapper.markItemAsCompleted(0);
        assertEquals("completed",listWrapper.getList().get(0).getStatus());

        //now, make it incomplete (from being completed previously)
        listWrapper.markItemAsIncomplete(0);
        assertEquals("incomplete",listWrapper.getList().get(0).getStatus());
    }
}