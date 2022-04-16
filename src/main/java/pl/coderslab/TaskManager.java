package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;


public class TaskManager {

    public static void main(String[] args) {

        try {
            File tasks = new File("tasks.csv");
            Scanner input = new Scanner(System.in);

            int i = getFileLength();

            String[][] tasksArray = new String[i][3];

            elementsToTasksArray(tasksArray);

            OptionsMenu();

            switch (input.next()) {
                case "add":
                    AddTask(tasksArray);
                    break;
                case "remove":
                    RemoveTask(input, i, tasksArray);
                    break;
                case "list":
                    ListTasks(tasksArray);
                    break;
                case "exit":
                    ExitApp(tasksArray);
                    break;
                default:
                    OptionsMenu();

            }
        } catch (FileNotFoundException e) {
            System.out.println("Nie znaleziono pliku tasks.");
        }
    }

    private static void ExitApp(String[][] tasksArray) throws FileNotFoundException {
        RewriteTasksToFile(tasksArray);
        System.out.println(ConsoleColors.RED_BOLD + "Farewell." + ConsoleColors.RESET);
    }

    private static void RemoveTask(Scanner input, int i, String[][] tasksArray) throws FileNotFoundException {
        System.out.println("Please provide task's identification number to remove it:");

        while (!input.hasNextInt()) {
            System.out.println("Please provide a number in a correct format:");
        }
        while (input.nextInt() < 0) {
            System.out.println("Position does not exist. Please provide a correct ID number:");
        }
        while (input.nextInt() > i) {
            System.out.println("Position does not exist. Please provide a correct ID number:");
        }
        int elementToRemove = input.nextInt();
        ArrayUtils.remove(tasksArray, elementToRemove);

        RewriteTasksToFile(tasksArray);

        System.out.println("Task successfully removed.");
    }

    private static void RewriteTasksToFile(String[][] tasksArray) throws FileNotFoundException {
        int i = getFileLength();

        try {
            FileWriter fileWriter = new FileWriter("tasks.csv", false);
            for (int p = 0; p < i; p++) {
                for (int e = 0; e < 3; e++) {
                    fileWriter.append(tasksArray[p][e] + ", ");
                }
                System.out.print("\n");
            }
        } catch (IOException e) {
            System.out.println("Nie ma czego plądrować");
        }
    }

    private static void ListTasks(String[][] tasksArray) {
        for (int p = 0; p < tasksArray.length; p++) {
            System.out.print(p + " : ");
            for (int e = 0; e < tasksArray[p].length; e++) {
                System.out.print(tasksArray[p][e] + " ");
            }
            System.out.print("\n");
        }
    }

    private static void AddTask(String[][] tasksArray) {
        File tasks = new File("tasks.csv");
        Scanner input = new Scanner(System.in);

        System.out.println("Please add task description:");
        String newTaskDescription = input.nextLine();

        System.out.println("Please add task due date (Pattern: Y-M-D):");
        String pattern = "YYYY-MM-DD";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Locale locale = new Locale("en", "PL");
        while (!input.equals(simpleDateFormat)) {
            System.out.println("Please enter a date in a specified pattern (YYYY-MM-DD).");
        }
        String newTaskDate = simpleDateFormat.format(input.nextLine());

        System.out.println("Is your task important (true/false):");
        while (!input.hasNextBoolean()) {
            System.out.println("Please enter true or false.");
        }
        boolean newTaskImportance = input.nextBoolean();

        tasksArray = Arrays.copyOf(tasksArray, tasksArray.length + 1);
        tasksArray[tasksArray.length - 1][0] = newTaskDescription;
        tasksArray[tasksArray.length - 1][1] = newTaskDate;
        tasksArray[tasksArray.length - 1][2] = Boolean.toString(newTaskImportance);

        try {
            FileWriter fileWriter = new FileWriter("tasks.csv", true);
            fileWriter.append(tasksArray[tasksArray.length - 1][0] + ", ");
            fileWriter.append(tasksArray[tasksArray.length - 1][1] + ", ");
            fileWriter.append(tasksArray[tasksArray.length - 1][2]);
        } catch (IOException e) {
            System.out.println("Nie ma czego plądrować");
        }
    }

    private static void elementsToTasksArray(String[][] tasksArray) throws FileNotFoundException {
        File tasks = new File("tasks.csv");
        Scanner output = new Scanner(tasks);
        while (output.hasNextLine()) {
            for (int j = 0; j < tasksArray.length; j++) {
                String[] elementsFromTasks = output.nextLine().split(",");
                for (int k = 0; k < elementsFromTasks.length; k++) {
                    tasksArray[j][k] = elementsFromTasks[k];
                }
            }
        }
    }

    private static int getFileLength() throws FileNotFoundException {

        File tasks = new File("tasks.csv");
        Scanner output = new Scanner(tasks);
        int i = 0;
        while (output.hasNextLine()) {
            i++;
            output.nextLine();
        }
        return i;
    }

    private static void OptionsMenu() {
        String[] menu = new String[5];
        menu[0] = ConsoleColors.BLUE_BOLD + "Please select an option:" + ConsoleColors.RESET;
        menu[1] = "add";
        menu[2] = "remove";
        menu[3] = "list";
        menu[4] = ConsoleColors.RED + "exit" + ConsoleColors.RESET;

        for (String option : menu) {
            System.out.println(option);
        }
    }
}
