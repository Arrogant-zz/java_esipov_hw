package ru.sbrf.esipov.hw.atm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Entity
@Table(name = "atm")
public class MyATM implements UserATM, AdminATM {
    private static Logger logger = LoggerFactory.getLogger(MyATM.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy="atm", cascade = CascadeType.ALL)
    @MapKey(name = "bill")
    @MapKeyEnumerated(EnumType.STRING)
    @JsonProperty
    @JsonManagedReference
    private Map<Bill, Cassette> bills;

    public MyATM() {
        bills = new EnumMap<>(Bill.class);
        EnumSet.allOf(Bill.class).forEach(bill -> {
            Cassette cassette = new Cassette();
            cassette.setATM(this);
            cassette.setBill(bill);
            bills.put(bill, cassette);
        });
    }

    @JsonIgnore
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
        log("Put money. Bills:", Arrays.toString(bills));

        for (Bill bill : bills) {
            this.bills.get(bill).putBills(1);
        }
    }

    @Override
    public Bill[] getMoney(int amount) {
        log("Getting money. Amount:", String.valueOf(amount));

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

    public String toString() {
        return "ATM{" +
                "id=" + id +
                ", balance='" + getBalance() + '\'' +
                '}';
    }

    private void log(String message, String param) {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        logger.info("Log {}: {} {}", df.format(new Date()), message, param);
    }
}
