package ru.sbrf.esipov.hw.atm;

public interface UserATM {
    void takeBills(Bill[] bills);
    Bill[] getMoney(int amount);
}
