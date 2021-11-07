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
    private int index = -1;
    //isAdding uses as a flag, determine the difference between Add/Edit window
    private boolean isAdding;
    //a list that hold all the task objects
    private List<TaskObject> list = new ArrayList<>();

    public void setIndex(int index) {
        this.index = index;
    }

    public void setAdding(boolean adding) {
        isAdding = adding;
    }

    public void setList(List<TaskObject> list) {
        this.list = list;
    }

    public int getIndex() {
        return index;
    }

    public boolean getIsAdding() {
        return isAdding;
    }

    public List<TaskObject> getList() {
        return list;
    }

    public void addToList(TaskObject task) {
        list.add(task);
    }

    public void clearList() {
        list.clear();
    }

    public void removeItemFromList (int index) {
        list.remove(index);
    }

    public void editDescription (int index,String newDescription) {
        list.get(index).setDescription(newDescription);
    }

    public void editDueDate (int index,String newDate) {
        list.get(index).setDueDate(newDate);
    }

    public void markItemAsCompleted (int index) {
        list.get(index).setStatus("completed");
    }

    public void markItemAsIncomplete (int index) {
        list.get(index).setStatus("incomplete");
    }
}