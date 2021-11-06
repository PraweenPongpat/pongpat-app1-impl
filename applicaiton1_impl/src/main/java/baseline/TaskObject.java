/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Praween Pongpat
 */

package baseline;

public class TaskObject {
    private String dueDate;
    private String description;
    private String status;

    public TaskObject(String dueDate, String description) {
        setDueDate(dueDate);
        setDescription(description);

        //default, isComplete must be false, it is a nature of todoList
        status = "incomplete";
    }

    //setters for the class object
    public void setDescription(String description) {
        this.description = description;
    }
    public void setDueDate(String dueDate) {
        //the due date of the task (if any) will be verified in the AddEditWindowController
        this.dueDate = dueDate;
    }
    public void setStatus(String completed) {
        status = completed;
    }

    //getters for the class object
    public String getDueDate() {
        return dueDate;
    }
    public String getDescription() {
        return description;
    }
    public String getStatus() {
        return status;
    }

}
