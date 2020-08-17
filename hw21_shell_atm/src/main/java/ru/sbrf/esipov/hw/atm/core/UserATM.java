package ru.sbrf.esipov.hw.atm.core;

public interface UserATM {
    void takeBills(Bill[] bills);
    Bill[] getMoney(int amount);
}
