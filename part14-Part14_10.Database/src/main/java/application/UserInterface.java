package application;

import java.sql.SQLException;
import java.util.Scanner;

public class UserInterface {

    private Scanner scanner;
    private TodoDao database;

    public UserInterface(Scanner scanner, TodoDao database) {
        this.scanner = scanner;
        this.database = database;
    }

    public void start() throws SQLException {
        while (true) {
            System.out.println("");
            System.out.println("Enter command:");
            System.out.println("1) list");
            System.out.println("2) add");
            System.out.println("3) mark as done");
            System.out.println("4) remove");
            System.out.println("x) quit");

            System.out.print("> ");
            String command = this.scanner.nextLine();
            if (command.equals("x")) {
                break;
            } else if (command.equals("1")){
                System.out.println("Listing the data base contents");
                database.list().forEach(item -> {
                    System.out.println(item);
                });
            } else if (command.equals("2")){
                System.out.println("Adding a new todo");
                System.out.println("Enter name");
                String addName = this.scanner.nextLine();
                System.out.println("Enter Description");
                String addDesc = this.scanner.nextLine();
                database.add(new Todo(addName, addDesc, Boolean.FALSE));
            } else if (command.equals("3")){
                System.out.println("Which todo should be marked as done (give the id)?1");
                int markDone = Integer.valueOf(scanner.nextLine());
                database.markAsDone(markDone);
            } else if (command.equals("4")){
                System.out.println("Which todo should be removed (give the id)?");
                int removed = Integer.valueOf(scanner.nextLine());
                database.remove(removed);
            }

            
        }

        System.out.println("Thank you!");
    }

}
