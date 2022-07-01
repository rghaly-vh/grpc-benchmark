package com.vibrenthealth.benchmark.rest.spi;

public interface DoSomethingServiceSpi {

    public DoSomethingResponse doSomething(String testId, int iterationId, long sleepMills);
}
