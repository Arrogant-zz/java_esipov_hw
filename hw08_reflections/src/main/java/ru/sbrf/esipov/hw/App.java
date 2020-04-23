package ru.sbrf.esipov.hw;

import ru.sbrf.esipov.hw.tester.TestResult;
import ru.sbrf.esipov.hw.tester.TestRunner;

public class App {
    public static void main(String[] args) throws Exception {
        TestRunner tRunner = new TestRunner("ru.sbrf.esipov.hw.test.MyClassTest");
        TestResult tResult = tRunner.run();

        System.out.println("All tests: " + tResult.getAll() + " | Fail tests: " + tResult.getFail() + " | Success tests: " + tResult.getSuccess());
        tResult.printResults();
    }
}
