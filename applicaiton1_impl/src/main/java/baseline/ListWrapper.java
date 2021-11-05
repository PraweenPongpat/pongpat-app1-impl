/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Praween Pongpat
 */

package baseline;

import java.util.ArrayList;
import java.util.List;

public class ListWrapper {
    //this class hold a whole object that will be initialized once, and pass around Controllers

    //index, to store index of selected items of the tableView
    private int index;
    //isAdding uses as a flag, determine the difference between Add/Edit window
    private boolean isAdding;
    //a list that hold all the task objects
    // after i can figure out how to pass information around and NOT use static list mentioned
    private List<TaskObject> list = new ArrayList<>();

    public void setIndex(int index) {
        this.index = index;
    }

    public void setAdding(boolean adding) {
        isAdding = adding;
    }


    public int getIndex() {
        return index;
    }

    public boolean getIsAdding() {
        return isAdding;
    }
}
