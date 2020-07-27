package ru.sbrf.esipov.hw.atm;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "cassette")
public class Cassette implements ATMCassette {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty
    private int count;

    private Bill bill;

    @JsonBackReference
    @ManyToOne
    private MyATM atm;

    public Cassette() {
        count = 0;
    }

    public void setATM(MyATM atm) {
        this.atm = atm;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
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

    public Bill getBill() {
        return bill;
    }
}
