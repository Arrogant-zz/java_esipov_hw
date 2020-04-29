package ru.sbrf.esipov.hw.tester;

import java.util.ArrayList;

public class TestResult {
    private int all;
    private int fail;
    private int success;
    private ArrayList<Result> results;

    public TestResult() {
        all = 0;
        fail = 0;
        success = 0;
        results = new ArrayList<>();
    }

    public void addResult(String testName, TestResultStatus status) {
        all++;

        switch (status) {
            case FAIL:
                fail++;
                break;
            case SUCCESS:
                success++;
                break;
        }

        results.add(new Result(testName, status));
    }

    public int getAll() {
        return all;
    }

    public int getFail() {
        return fail;
    }

    public int getSuccess() {
        return success;
    }

    public void printResults() {
        for (Result result : results) {
            System.out.println(result.testName + " " + result.status);
        }
    }

    private class Result {
        public String testName;
        public TestResultStatus status;

        public Result(String testName, TestResultStatus status) {
            this.testName = testName;
            this.status = status;
        }
    }
}
