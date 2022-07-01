package com.vibrenthealth.benchmark.rest.spi;

public class DoSomethingResponse {

    private String testId;
    private int iterationId;
    private long requestReceived;
    private long responseSent;
    private long localDuration;
    private boolean allWas200;

    public DoSomethingResponse() {
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

    public long getRequestReceived() {
        return requestReceived;
    }

    public void setRequestReceived(long requestReceived) {
        this.requestReceived = requestReceived;
    }

    public long getResponseSent() {
        return responseSent;
    }

    public void setResponseSent(long responseSent) {
        this.responseSent = responseSent;
    }

    public long getLocalDuration() {
        return localDuration;
    }

    public void setLocalDuration(long localDuration) {
        this.localDuration = localDuration;
    }

    public boolean isAllWas200() {
        return allWas200;
    }

    public void setAllWas200(boolean allWas200) {
        this.allWas200 = allWas200;
    }
}
