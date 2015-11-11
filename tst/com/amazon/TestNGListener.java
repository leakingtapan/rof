package com.amazon;

import org.apache.commons.lang3.StringUtils;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/**
 * Reports TestNG test result.
 */
public final class TestNGListener extends TestListenerAdapter {

    @Override
    public void onTestFailure(final ITestResult tr) {
        System.out.println("Failure: " + tr.getTestClass().getName());
        System.out.println("\t - " + tr.getName() + formatParameter(tr.getParameters()));
    }

    @Override
    public void onTestSkipped(final ITestResult tr) {
        System.out.println("Skipped: " + tr.getTestClass().getName());
        System.out.println("\t - " + tr.getName() + formatParameter(tr.getParameters()));
    }

    @Override
    public void onTestSuccess(final ITestResult tr) {

    }

    /**
     * Formats input parameters for test method.
     *
     * @param array parameter array.
     * @return formatted string.
     */
    private String formatParameter(final Object[] array) {
        assert array != null : "array cannot be null";

        return "(" + StringUtils.join(array, ", ") + ")";
    }

}
