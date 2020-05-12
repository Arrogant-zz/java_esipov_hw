package ru.sbrf.esipov.hw.atm;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;

public class MyATM implements UserATM, AdminATM {
    private EnumMap<Bill, Cassette> bills;

    public MyATM() {
        bills = new EnumMap<>(Bill.class);
        EnumSet.allOf(Bill.class).forEach(bill -> bills.put(bill, new Cassette()));
    }

    @Override
    public int getBalance() {
        int balance = 0;
        for (var key : bills.keySet()) {
            balance += key.getNominal() * bills.get(key).getNumberOfBills();
        }
        return balance;
    }

    @Override
    public void takeBills(Bill[] bills) {
        for (Bill bill : bills) {
            this.bills.get(bill).putBills(1);
        }
    }

    @Override
    public Bill[] getMoney(int amount) {
        ArrayList<Bill> resultBills = new ArrayList<>();

        for (var key : bills.keySet()) {
            if (bills.get(key).getNumberOfBills() > 0 && key.getNominal() <= amount) {
                int cntBill = Math.min(amount / key.getNominal(), bills.get(key).getNumberOfBills());
                amount -= cntBill * key.getNominal();
                for (int i = 0; i < cntBill; i++) {
                    resultBills.add(key);
                }
            }
        }

        if (amount == 0) {
            resultBills.forEach(bill -> bills.get(bill).takeBill());
        }
        else {
            throw new IllegalArgumentException("Not enough money in ATM");
        }

        return resultBills.toArray(new Bill[0]);
    }
}
