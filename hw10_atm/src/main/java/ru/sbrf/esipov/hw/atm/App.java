package ru.sbrf.esipov.hw.atm;

import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        MyATM atm = new MyATM();
        atm.takeBills(new Bill[]{Bill.THOUSAND, Bill.TEN});
        atm.takeBills(new Bill[]{Bill.THOUSAND, Bill.TEN});
        atm.takeBills(new Bill[]{Bill.TEN, Bill.FIFTY, Bill.FIFTY, Bill.HUNDRED});
        atm.takeBills(new Bill[]{Bill.FIFTY, Bill.FIFTY, Bill.HUNDRED});
        System.out.println(atm.getBalance());
        System.out.println(Arrays.toString(atm.getMoney(230)));
        System.out.println(atm.getBalance());
    }
}
