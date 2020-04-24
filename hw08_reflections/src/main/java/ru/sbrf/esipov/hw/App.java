package ru.sbrf.esipov.hw;

import ru.sbrf.esipov.hw.tester.TestRunner;

public class App {
    public static void main(String[] args) throws Exception {
        (new TestRunner("ru.sbrf.esipov.hw.test.MyClassTest")).run();
    }
}
