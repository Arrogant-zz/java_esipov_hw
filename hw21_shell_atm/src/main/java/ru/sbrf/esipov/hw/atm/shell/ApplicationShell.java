package ru.sbrf.esipov.hw.atm.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.sbrf.esipov.hw.atm.core.Bill;
import ru.sbrf.esipov.hw.atm.core.MyATM;

import java.util.Arrays;

@ShellComponent
public class ApplicationShell {
    private MyATM atm = new MyATM();;

    @ShellMethod(value = "Get money command", key = {"g", "get"})
    public String get(int amount) {
        try {
            Bill[] bills = atm.getMoney(amount);


            StringBuilder sb = new StringBuilder();
            for (Bill bill : bills) {
                sb.append(bill.toString() + " ");
            }

            return sb.toString();
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    @ShellMethod("Add bills.")
    public void add(String billArg) {
        String[] bills = billArg.split("\\s+");

        atm.takeBills(
                Arrays.stream(bills)
                        .map(Integer::parseInt)
                        .map(Bill::getByNominal)
                        .toArray(Bill[]::new)
        );
    }

    @ShellMethod("Balance")
    public int balance() {
        return atm.getBalance();
    }
}
