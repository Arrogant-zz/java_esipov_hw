package ru.sbrf.esipov.hw.tester;

import ru.sbrf.esipov.hw.annotations.After;
import ru.sbrf.esipov.hw.annotations.Before;
import ru.sbrf.esipov.hw.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestRunner {
    private Class<?> classTest;
    private TestResult tResult;

    public TestRunner(String className) throws ClassNotFoundException {
        this.classTest = Class.forName(className);
    }

    public TestResult run() throws Exception {
        tResult = new TestResult();

        for (Method method : classTest.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                runTest(method);
            }
        }

        return tResult;
    }

    private void runTest(Method methodTest) throws Exception {
        Constructor<?> constructor = classTest.getConstructor();
        Object classTestObject = constructor.newInstance();

        invokeMethodsWithAnnotation(Before.class, classTestObject);

        try {
            methodTest.invoke(classTestObject);
            tResult.addResult(methodTest.getName(), TestResultStatus.SUCCESS);
        }
        catch(Exception e) {
            tResult.addResult(methodTest.getName(), TestResultStatus.FAIL);
        }

        invokeMethodsWithAnnotation(After.class, classTestObject);
    }

    private void invokeMethodsWithAnnotation(Class<? extends Annotation> annotation, Object classTestObject) throws InvocationTargetException, IllegalAccessException {
        for (Method method : classTest.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                method.invoke(classTestObject);
            }
        }
    }
}
