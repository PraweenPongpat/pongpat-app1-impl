/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Praween Pongpat
 */

package baseline;

public class TaskObject {
    private String dueDate;
    private String description;
    private boolean isCompleted;

    public TaskObject(String dueDate, String description) {
        setDueDate(dueDate);
        setDescription(description);

        //default, isComplete must be false, it is a nature of todoList
        isCompleted = false;
    }

    //setters for the class object
    public void setDescription(String description) {
        this.description = description;
    }
    public void setDueDate(String dueDate) {
        //the due date of the task (if any) will be verified in the AddEditWindowController
        this.dueDate = dueDate;
    }
    public void seIstCompleted(boolean completed) {
        isCompleted = completed;
    }

    //getters for the class object
    public String getDueDate() {
        return dueDate;
    }
    public String getDescription() {
        return description;
    }
    public boolean getIsCompleted() {
        return isCompleted;
    }
}
