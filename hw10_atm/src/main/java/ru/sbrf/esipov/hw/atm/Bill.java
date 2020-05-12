package ru.sbrf.esipov.hw.atm;

public enum Bill {
    FIVE_THOUSAND (5000),
    THOUSAND      (1000),
    FIVE_HUNDRED  (500),
    HUNDRED       (100),
    FIFTY         (50),
    TEN           (10)
    ;


    private final int nominal;

    Bill(int nominal) {
        this.nominal = nominal;
    }

    public int getNominal() {
        return this.nominal;
    }

}
