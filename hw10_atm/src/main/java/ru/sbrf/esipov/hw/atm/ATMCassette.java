package ru.sbrf.esipov.hw.atm;

public interface ATMCassette {
    int getNumberOfBills();
    void putBills(int numberOfBills);
    boolean takeBill();
}
