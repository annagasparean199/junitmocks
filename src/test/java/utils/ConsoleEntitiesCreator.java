package utils;


import org.example.dbdatacreation.databaseinsertioncsv.GenericDbInserter;
import org.example.dbdatacreation.scannerdatacreation.ConsoleCreditBuilder;
import org.example.dbdatacreation.scannerdatacreation.ConsoleDeliveryBuilder;
import org.example.dbdatacreation.scannerdatacreation.ConsoleProductBuilder;
import org.example.dbdatacreation.scannerdatacreation.ConsoleSalesBuilder;
import org.example.dbdatacreation.scannerdatacreation.ConsoleUserBuilder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ConsoleEntitiesCreator {

    ConsoleCreditBuilder consoleCreditBuilder = new ConsoleCreditBuilder();
    ConsoleDeliveryBuilder consoleDeliveryBuilder = new ConsoleDeliveryBuilder();
    ConsoleProductBuilder consoleProductBuilder = new ConsoleProductBuilder();
    ConsoleUserBuilder consoleUserBuilder = new ConsoleUserBuilder();
    ConsoleSalesBuilder consoleSalesBuilder = new ConsoleSalesBuilder();


    public void createEntitiesFromConsole() {
        Scanner scanner = new Scanner(System.in);
        List<GenericDbInserter> daos = new ArrayList<>();
        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    daos.add(consoleProductBuilder);
                    break;
                case 2:
                    daos.add(consoleUserBuilder);
                    break;
                case 3:
                    daos.add(consoleUserBuilder);
                    daos.add(consoleProductBuilder);
                    daos.add(consoleSalesBuilder);
                    break;
                case 4:
                    daos.add(consoleProductBuilder);
                    daos.add(consoleDeliveryBuilder);
                    break;
                case 5:
                    daos.add(consoleUserBuilder);
                    daos.add(consoleProductBuilder);
                    daos.add(consoleCreditBuilder);
                    break;
                case 0:
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }

            daos.sort(Comparator.comparingInt(GenericDbInserter::getPriorityOfInsertionInDatabase));

            for (GenericDbInserter dao : daos) {
                dao.processCreatedDataAndInsertIntoDB();
            }
            daos.clear();
        }
    }

    private static void displayMenu() {
        System.out.println("===== Entity Menu =====");
        System.out.println("1. Product");
        System.out.println("2. User");
        System.out.println("3. Sales");
        System.out.println("4. Delivery");
        System.out.println("5. Credit");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }
}