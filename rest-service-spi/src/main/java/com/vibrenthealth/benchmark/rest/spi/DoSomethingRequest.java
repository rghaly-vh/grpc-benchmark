package com.vibrenthealth.benchmark.rest.spi;

public class DoSomethingRequest {

    private String testId;
    private int iterationId;
    private long milliseconds;


    public DoSomethingRequest() {
    }

    public long getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(long milliseconds) {
        this.milliseconds = milliseconds;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public int getIterationId() {
        return iterationId;
    }

    public void setIterationId(int iterationId) {
        this.iterationId = iterationId;
    }
}
