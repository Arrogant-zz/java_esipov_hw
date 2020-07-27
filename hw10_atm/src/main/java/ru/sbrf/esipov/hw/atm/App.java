package ru.sbrf.esipov.hw.atm;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws IOException {
        MyATM atm;
        ObjectMapper mapper = new ObjectMapper();
        Scanner scanner = new Scanner(System.in);

        if (args.length == 0) {
            atm = new MyATM();
        } else {
            System.out.println("Reading ATM from " + args[0] + " ...");
            atm = mapper.readValue(new File(args[0]), MyATM.class);
        }

        System.out.println("Enter command:");

        boolean endProcess = false;
        while(!endProcess) {
            String commandLine = scanner.nextLine();
            String[] commandArgs = commandLine.split("\\s+", 2);

            switch (commandArgs[0].toLowerCase()) {
                case "put":
                    if (commandArgs.length != 2) {
                        continue;
                    }

                    String[] bills = commandArgs[1].split("\\s+");

                    atm.takeBills(
                            Arrays.stream(bills)
                            .map(Integer::parseInt)
                            .map(Bill::getByNominal)
                            .toArray(Bill[]::new)
                    );

                    break;

                case "get":
                    if (commandArgs.length != 2) {
                        continue;
                    }

                    int amount = Integer.parseInt(commandArgs[1]);

                    try {
                        Arrays.stream(atm.getMoney(amount)).forEach(System.out::println);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }

                    break;

                case "exit":
                default:
                    endProcess = true;
                    break;
            }
        }
    }
}
