package ru.sbrf.esipov.hw.test;

import ru.sbrf.esipov.hw.annotations.After;
import ru.sbrf.esipov.hw.annotations.Before;
import ru.sbrf.esipov.hw.annotations.Test;

public class MyClassTest {
    public MyClassTest() {

    }

    @Before
    public void initMethod1() {
        System.out.println("Before 1");
    }

    @Test
    public void testSuccess1() {

    }

    @Test
    public void testSuccess2() {

    }

    @Test
    public void testFail1() {
        throw new NullPointerException("Bad Test");
    }

    @After
    public void closeMethod1() {
        System.out.println("After 1");
    }

    @After
    public void closeMethod2() {
        System.out.println("After 2");
    }
}
