package ru.sbrf.esipov.hw.atm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Cassette implements ATMCassette {
    @JsonProperty
    private int count;

    public Cassette() {
        count = 0;
    }

    @JsonIgnore
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
