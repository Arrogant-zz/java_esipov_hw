package ru.sbrf.esipov.hw.atm;

public class Cassette implements ATMCassette {
    private int count;

    void Cassette() {
        count = 0;
    }

    @Override
    public int getNumberOfBills() {
        return count;
    }

    @Override
    public void putBills(int numberOfBills) {
        if (numberOfBills < 0) {
            throw new IllegalArgumentException("numberOfBills must be greater then zero");
        }
        count += numberOfBills;
    }

    @Override
    public boolean takeBill() {
        if (count > 0) {
            count--;
            return true;
        }
        else {
            return false;
        }
    }
}
