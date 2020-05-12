package ru.sbrf.esipov.hw.tester;

import ru.sbrf.esipov.hw.annotations.After;
import ru.sbrf.esipov.hw.annotations.Before;
import ru.sbrf.esipov.hw.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class TestRunner {
    private Class<?> classTest;
    private TestResult tResult;
    private ArrayList<Method> beforeMethods;
    private ArrayList<Method> testMethods;
    private ArrayList<Method> afterMethods;

    public TestRunner(String className) throws ClassNotFoundException {
        this.classTest = Class.forName(className);

        beforeMethods = findMethodsWithAnnotation(Before.class);
        testMethods = findMethodsWithAnnotation(Test.class);
        afterMethods = findMethodsWithAnnotation(After.class);
    }

    public void run() throws Exception {
        tResult = new TestResult();

        for (Method method : testMethods) {
            runTest(method);
        }
    }

    public void printResult() {
        System.out.println("All tests: " + tResult.getAll() + " | Fail tests: " + tResult.getFail() + " | Success tests: " + tResult.getSuccess());
        tResult.printResults();
    }

    private void runTest(Method methodTest) throws Exception {
        Constructor<?> constructor = classTest.getConstructor();
        Object classTestObject = constructor.newInstance();

        invokeMethods(beforeMethods, classTestObject);

        try {
            methodTest.invoke(classTestObject);
            tResult.addResult(methodTest.getName(), TestResultStatus.SUCCESS);
        }
        catch(Exception e) {
            tResult.addResult(methodTest.getName(), TestResultStatus.FAIL);
        }

        invokeMethods(afterMethods, classTestObject);
    }

    private ArrayList<Method> findMethodsWithAnnotation(Class<? extends Annotation> annotation) {
        ArrayList<Method> methods = new ArrayList<>();
        for (Method method : classTest.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                methods.add(method);
            }
        }
        return methods;
    }

    private void invokeMethods(ArrayList<Method> methods, Object classTestObject) throws InvocationTargetException, IllegalAccessException {
        for (Method method : methods) {
            method.invoke(classTestObject);
        }
    }
}
