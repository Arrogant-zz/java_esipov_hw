package ru.sbrf.esipov.hw.atm.core;

public interface ATMCassette {
    int getNumberOfBills();
    void putBills(int numberOfBills);
    boolean takeBill();
}
