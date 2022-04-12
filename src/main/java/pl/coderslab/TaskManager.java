package pl.coderslab;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


public class TaskManager {

    public static void main(String[] args) throws FileNotFoundException {

        OptionsMenu();

        try {
            Path path = Paths.get("tasks.csv");
            File tasks = new File ("tasks.csv");
            Scanner output = new Scanner(tasks);

            while (output.hasNextLine()) {
                System.out.println(output.nextLine());
            }


        }catch(FileNotFoundException e){
            System.out.println("Nie znaleziono pliku tasks.");
        }

    }

    private static void OptionsMenu() {
        String[] menu = new String[5];
        menu[0]  = ConsoleColors.BLUE_BOLD + "Please select an option:" + ConsoleColors.RESET;
        menu[1] = "add";
        menu[2] = "remove";
        menu[3] = "list";
        menu[4] = ConsoleColors.RED + "exit" + ConsoleColors.RESET;

        for (String option: menu){
            System.out.println(option);
        }
    }
}
