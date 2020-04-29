package ru.sbrf.esipov.hw.atm;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.stream.Stream;

public class MyATM implements UserATM, AdminATM {
    private EnumMap<Bill, Integer> bills;

    public MyATM() {
        bills = new EnumMap<>(Bill.class);
    }

    @Override
    public int getBalance() {
        int balance = 0;
        for (var key : bills.keySet()) {
            balance += key.getNominal() * bills.get(key);
        }
        return balance;
    }

    @Override
    public void takeBills(Bill[] bills) {
        for (Bill bill : bills) {
            if (this.bills.containsKey(bill)) {
                this.bills.put(bill, this.bills.get(bill) + 1);
            }
            else {
                this.bills.put(bill, 1);
            }
        }
    }

    @Override
    public Bill[] getMoney(int amount) {
        ArrayList<Bill> resultBills = new ArrayList<>();

        for (var key : bills.keySet()) {
            if (bills.get(key) > 0 && key.getNominal() <= amount) {
                int cntBill = Math.min(amount / key.getNominal(), bills.get(key));
                amount -= cntBill * key.getNominal();
                for (int i = 0; i < cntBill; i++) {
                    resultBills.add(key);
                }
            }
        }

        if (amount == 0) {
            resultBills.forEach(bill -> bills.put(bill, bills.get(bill) - 1));
        }
        else {
            throw new IllegalArgumentException("Not enough money in ATM");
        }

        return resultBills.toArray(new Bill[0]);
    }
}
