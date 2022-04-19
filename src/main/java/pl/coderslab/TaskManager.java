package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {

    public static void main(String[] args) {

        try {
            Scanner input = new Scanner(System.in);

            OptionsMenu();

            while (input.hasNextLine()) {
                String inputOption = input.nextLine();

                int i = getFileLength();

                String[][] tasksArray = new String[i][3];

                elementsToTasksArray(tasksArray);

                switch (inputOption) {
                    case "add":
                        AddTask(tasksArray);
                        OptionsMenu();
                        break;
                    case "remove":
                        RemoveTask(input, i, tasksArray);
                        OptionsMenu();
                        break;
                    case "list":
                        ListTasks(tasksArray);
                        OptionsMenu();
                        break;
                    case "exit":
                        ExitApp(tasksArray);
                        break;
                    default:
                        OptionsMenu();

                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Nie znaleziono pliku tasks.");
            System.exit(0);
        }
    }

    private static void ExitApp(String[][] tasksArray) throws FileNotFoundException {
        RewriteTasksToFile(tasksArray);
        System.out.println(ConsoleColors.RED_BOLD + "Farewell." + ConsoleColors.RESET);
        System.exit(0);
    }

    private static void RemoveTask(Scanner input, int i, String[][] tasksArray) throws FileNotFoundException {
        System.out.println("Please provide task's identification number to remove it:");
        while (input.hasNextLine()) {
            String number = input.nextLine();
            if (!NumberUtils.isParsable(number)) {
                System.out.println("Please provide a number in a correct format:");
            }
            else if (Integer.parseInt(number) < 0) {
                System.out.println("Position does not exist. Please provide a correct ID number:");
            }
            else if (Integer.parseInt(number) > tasksArray.length) {
                System.out.println("Position does not exist. Please provide a correct ID number:");
            }else{
                tasksArray = ArrayUtils.remove(tasksArray, Integer.parseInt(number));
                System.out.println("Task successfully removed.");
                break;
            }
        }


    }

    private static void RewriteTasksToFile(String[][] tasksArray) throws FileNotFoundException {
        Path location = Paths.get("tasks.csv");

        String[] tasksInLines = new String[tasksArray.length];
        for (int i = 0; i < tasksArray.length; i++) {
            tasksInLines[i] = String.join(",", tasksArray[i]);
        }

        try {
            Files.write(location, Arrays.asList(tasksInLines));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

      /*  try {
            FileWriter fileWriter = new FileWriter("tasks.csv", true);
            for (int p = 0; p < tasksArray.length; p++) {
                for (int e = 0; e < 3; e++) {
                    fileWriter.append(Arrays.toString(new String[]{tasksArray[p][e]})).append(", ");
                }
                fileWriter.append("\n");
            }
        } catch (IOException e) {
            System.out.println("Nie ma czego plądrować");
        }*/
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
        Scanner input = new Scanner(System.in);

        System.out.println("Please add task description:");
        String newTaskDescription = input.nextLine();

        //String pattern = "YYYY-MM-DD";

        //Date date = new Date(input.next());
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        //String stringDate = simpleDateFormat.format(date);

        //while (!date.equals(simpleDateFormat)) {
        //    System.out.println("Please enter a date in a specified pattern (YYYY-MM-DD).");
        //}
        //String newTaskDate = simpleDateFormat.format(input.nextLine());

        String stringDate = GetDate(input);

        System.out.println("Is your task important (true/false):");
        while (!input.hasNextBoolean()) {
            System.out.println("Please enter true or false.");
        }
        boolean newTaskImportance = input.nextBoolean();

        tasksArray = Arrays.copyOf(tasksArray, tasksArray.length + 1);
        tasksArray[tasksArray.length - 1] = new String[3];
        tasksArray[tasksArray.length - 1][0] = newTaskDescription;
        tasksArray[tasksArray.length - 1][1] = stringDate;
        tasksArray[tasksArray.length - 1][2] = Boolean.toString(newTaskImportance);

        System.out.println("New task created.");

        //try {
        //    FileWriter fileWriter = new FileWriter("tasks.csv", true);
        //    fileWriter.append(tasksArray[tasksArray.length - 1][0]).append(", ");
        //   fileWriter.append(tasksArray[tasksArray.length - 1][1]).append(", ");
        //    fileWriter.append(tasksArray[tasksArray.length - 1][2]);
        //} catch (IOException e) {
        //   System.out.println("Nie ma czego plądrować");
        //}
    }

    private static String GetDate(Scanner input) {

        System.out.println("Please add task due date (Pattern: YYYY-MM-DD):");

        while (input.hasNextLine()) {

            String stringDate = input.nextLine();
            char[] dateToChar = stringDate.toCharArray();
            try {
                if (dateToChar[4] != '-') {
                    System.out.println("Given date does not match the pattern. Try again:");
                } else if (dateToChar[7] != '-') {
                    System.out.println("Given date does not match the pattern. Try again:");
                } else if (!NumberUtils.isParsable(String.valueOf(dateToChar[0]))) {
                    System.out.println("Given date does not match the pattern. Try again:");
                } else if (!NumberUtils.isParsable(String.valueOf(dateToChar[1]))) {
                    System.out.println("Given date does not match the pattern. Try again:");
                } else if (!NumberUtils.isParsable(String.valueOf(dateToChar[2]))) {
                    System.out.println("Given date does not match the pattern. Try again:");
                } else if (!NumberUtils.isParsable(String.valueOf(dateToChar[3]))) {
                    System.out.println("Given date does not match the pattern. Try again:");
                } else if (!NumberUtils.isParsable(String.valueOf(dateToChar[5]))) {
                    System.out.println("Given date does not match the pattern. Try again:");
                } else if (!NumberUtils.isParsable(String.valueOf(dateToChar[6]))) {
                    System.out.println("Given date does not match the pattern. Try again:");
                } else if (!NumberUtils.isParsable(String.valueOf(dateToChar[8]))) {
                    System.out.println("Given date does not match the pattern. Try again:");
                } else if (!NumberUtils.isParsable(String.valueOf(dateToChar[9]))) {
                    System.out.println("Given date does not match the pattern. Try again:");
                } else {
                    return stringDate;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Given date does not match the pattern. Try again:");
            }

        }
        return "";
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

    /*private static String GetDate(Scanner input) {
        while (input.hasNextLine()) {
            String stringDate = input.nextLine();
            char[] dateToChar = stringDate.toCharArray();
            if (dateToChar[4] != '-') {
                System.out.println("Given date does not match the pattern. Try again:");
                stringDate = input.nextLine();
                dateToChar = stringDate.toCharArray();
            }
            else if (dateToChar[7] != '-') {
                System.out.println("Given date does not match the pattern. Try again:");
                stringDate = input.nextLine();
                dateToChar = stringDate.toCharArray();
            }
            else if (NumberUtils.isParsable(String.valueOf(dateToChar[0]))) {
                System.out.println("Given date does not match the pattern. Try again:");
                stringDate = input.nextLine();
                dateToChar = stringDate.toCharArray();
            }
            else if (NumberUtils.isParsable(String.valueOf(dateToChar[1]))) {
                System.out.println("Given date does not match the pattern. Try again:");
                stringDate = input.nextLine();
                dateToChar = stringDate.toCharArray();
            }
            else if (NumberUtils.isParsable(String.valueOf(dateToChar[2]))) {
                System.out.println("Given date does not match the pattern. Try again:");
                stringDate = input.nextLine();
                dateToChar = stringDate.toCharArray();
            }
            else if (NumberUtils.isParsable(String.valueOf(dateToChar[3]))) {
                System.out.println("Given date does not match the pattern. Try again:");
                stringDate = input.nextLine();
                dateToChar = stringDate.toCharArray();
            }
            else if (NumberUtils.isParsable(String.valueOf(dateToChar[5]))) {
                System.out.println("Given date does not match the pattern. Try again:");
                stringDate = input.nextLine();
                dateToChar = stringDate.toCharArray();
            }
            else if (NumberUtils.isParsable(String.valueOf(dateToChar[6]))) {
                System.out.println("Given date does not match the pattern. Try again:");
                stringDate = input.nextLine();
                dateToChar = stringDate.toCharArray();
            }
            else if (NumberUtils.isParsable(String.valueOf(dateToChar[8]))) {
                System.out.println("Given date does not match the pattern. Try again:");
                stringDate = input.nextLine();
                dateToChar = stringDate.toCharArray();
            }
            else if (NumberUtils.isParsable(String.valueOf(dateToChar[9]))) {
                System.out.println("Given date does not match the pattern. Try again:");
                stringDate = input.nextLine();
                dateToChar = stringDate.toCharArray();
            }
            else{
                return stringDate;
            }

        }
        return "";
    }*/
}
