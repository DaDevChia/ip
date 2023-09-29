package duke;

import duke.exception.DukeException;
import duke.ui.Ui;

import duke.parser.Check;
import duke.storage.FileRW;

import duke.tasks.*;

import java.util.ArrayList; // Import the ArrayList class

public class Duke {

    //Initialize create a list of tasks
    public static ArrayList<Task> tasks = new ArrayList<>();

    //Initialize create a ui object
    public static Ui ui = new Ui();

    /**
     * @param args
     */
    public static void main(String[] args) {

        FileRW.readFromFile(tasks);

        //Print out the logo
        ui.showWelcome();

        String userInput = ui.readCommand();

        //Check the arguments provided unless it is "bye" which quits the program
        while( !(Check.isBye(userInput)) ){
            
            try{
                //If userInput is "list" print all tasks
                if(Check.isList(userInput)){
                    //Print out the list of tasks
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                    //Get user input again
                    userInput = ui.readCommand();
                }

                //Add delete keyword and function
                else if(Check.isDelete(userInput)){
                    //get the task number
                    int taskNumber = Integer.parseInt(userInput.substring(7));
                    //if the task number is greater than the task count throw an exception
                    if(taskNumber>tasks.size()){
                        throw new IllegalArgumentException("The task number is greater than the number of tasks.");
                    }
                    //if the task number is less than 1 throw an exception
                    if(taskNumber<1){
                        throw new IllegalArgumentException("The task number is less than 1.");
                    }
                    //if there is no task at the task number throw an exception
                    if(tasks.get(taskNumber-1)==null){
                        throw new IllegalArgumentException("There is no task at the task number.");
                    }
                    //print out the task that was deleted
                    System.out.println("Noted. I've removed this task:");
                    System.out.println(tasks.get(taskNumber-1));
                    //delete the task
                    tasks.remove(taskNumber-1);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    //Get user input again
                    userInput = ui.readCommand();
                }

                //If userInput is "unmark" get the task number and unmark the task as done
                else if(Check.isUnmark(userInput)){
                    //get the task number
                    int taskNumber = Integer.parseInt(userInput.substring(7));
                    //if the task number is greater than the task count throw an exception
                    if(taskNumber>tasks.size()){
                        throw new IllegalArgumentException("The task number is greater than the number of tasks.");
                    }
                    //if the task number is less than 1 throw an exception
                    if(taskNumber<1){
                        throw new IllegalArgumentException("The task number is less than 1.");
                    }
                    //if there is no task at the task number throw an exception
                    if(tasks.get(taskNumber-1)==null){
                        throw new IllegalArgumentException("There is no task at the task number.");
                    }
                    //if the task is already not done throw an exception
                    if(tasks.get(taskNumber-1).isDone()==false){
                        throw new IllegalArgumentException("The task is already not done.");
                    }
                    //mark the task as done
                    tasks.get(taskNumber-1).markAsNotDone();
                    //print out the task that was marked as done
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(tasks.get(taskNumber-1));
                    //Get user input again
                    userInput = ui.readCommand();      
                }

                //If userInput is "mark" get the task number and mark the task as done
                else if(Check.isMark(userInput)){
                    //get the task number
                    int taskNumber = Integer.parseInt(userInput.substring(5));
                    //if the task number is greater than the task count throw an exception
                    if(taskNumber>tasks.size()){
                        throw new IllegalArgumentException("The task number is greater than the number of tasks.");
                    }
                    //if the task number is less than 1 throw an exception
                    if(taskNumber<1){
                        throw new IllegalArgumentException("The task number is less than 1.");
                    }
                    //if there is no task at the task number throw an exception
                    if(tasks.get(taskNumber-1)==null){
                        throw new IllegalArgumentException("There is no task at the task number.");
                    }
                    //if the task is already done throw an exception
                    if(tasks.get(taskNumber-1).isDone()==true){
                        throw new IllegalArgumentException("The task is already done.");
                    }
                    //mark the task as done
                    tasks.get(taskNumber-1).markAsDone();
                    //print out the task that was marked as done
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(tasks.get(taskNumber-1));
                    //Get user input again
                    userInput = ui.readCommand();             
                }

                //If userInput is "todo" add a todo task to the list
                else if(Check.isTodo(userInput)){
                    //get the task name
                    String taskName = userInput.substring(5);
                    //If the task name is empty throw an exception
                    if(taskName.equals("")){
                        throw new IllegalArgumentException("The description of a todo cannot be empty.");
                    }
                    //create a todo task
                    tasks.add(new Todo (taskName));
                    //print out the task that was added
                    System.out.println("Got it. I've added this task:");
                    System.out.println(tasks.get(tasks.size()-1));
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    //Get user input again
                    userInput = ui.readCommand();
                }

                //If userInput is "deadline" add a deadline task to the list
                else if(Check.isDeadline(userInput)){
                    //get the task name
                    String taskName = userInput.substring(9,userInput.indexOf("/"));
                    //get the deadline string without any "/"
                    String deadline = userInput.substring(userInput.indexOf("/")).replace("/","");
                    //If the task name is empty throw an exception
                    if(taskName.equals("")){
                        throw new IllegalArgumentException("The description of a deadline cannot be empty.");
                    }
                    //If the deadline is empty throw an exception
                    if(deadline.equals("")){
                        throw new IllegalArgumentException("The deadline of a deadline cannot be empty. Add a / argument to specify time.");
                    }
                    //create a deadline task
                    tasks.add(new Deadline(taskName,deadline));
                    //print out the task that was added
                    System.out.println("Got it. I've added this task:");
                    System.out.println(tasks.get(tasks.size()-1));
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    userInput = ui.readCommand();
                }

                //If userInput is "event" add an event task to the list
                else if(Check.isEvent(userInput)){
                    //get the task name
                    String taskName = userInput.substring(6,userInput.indexOf("/"));
                    //get the event time without any "/"
                    String eventTime = userInput.substring(userInput.indexOf("/")).replace("/","");
                    //if the taskname is empty throw an exception
                    if(taskName.equals("")){
                        throw new IllegalArgumentException("The description of an event cannot be empty.");
                    }
                    //if the event time is empty throw an exception
                    if(eventTime.equals("")){
                        throw new IllegalArgumentException("The event time of an event cannot be empty. Add a / argument to specify time.");
                    }
                    //create an event task
                    tasks.add(new Event(taskName,eventTime));
                    //print out the task that was added
                    System.out.println("Got it. I've added this task:");
                    System.out.println(tasks.get(tasks.size()-1));
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    //Get user input again
                    userInput = ui.readCommand();
                }

                //If userInput is not any of the commands throw an illegal argument exception
                else{
                    //throw an exception
                    throw new IllegalArgumentException("Invalid command. Please try again.");     
                }

            }
            catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
                //Get user input again
                userInput = ui.readCommand();
            }
            catch (StringIndexOutOfBoundsException e){
               System.out.println("Invalid date entered. Please try again and enter / before the date.");
                //Get user input again
                userInput = ui.readCommand();
            }
            
        }

        //Print out a goodbye message
        ui.showGoodbye();
        
        

        //Write the tasks to the file
        FileRW.writeToFile(tasks);
        
    }

}