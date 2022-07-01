package com.vibrenthealth.benchmark.service.spi;

public class BenchmarkRequest {

    private String serviceType;
    private String testId;
    private int loopCount;
    private int repeats;
    private long sleepMills;
    private Primer primer;

    public BenchmarkRequest() {
    }

    public BenchmarkRequest(String serviceType, String testId, int loopCount, int repeats, long sleepMills, Primer primer) {
        this.serviceType = serviceType;
        this.testId = testId;
        this.loopCount = loopCount;
        this.repeats = repeats;
        this.sleepMills = sleepMills;
        this.primer = primer;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public int getLoopCount() {
        return loopCount;
    }

    public void setLoopCount(int loopCount) {
        this.loopCount = loopCount;
    }

    public int getRepeats() {
        return repeats;
    }

    public void setRepeats(int repeats) {
        this.repeats = repeats;
    }

    public long getSleepMills() {
        return sleepMills;
    }

    public void setSleepMills(long sleepMills) {
        this.sleepMills = sleepMills;
    }

    public Primer getPrimer() {
        return primer;
    }

    public void setPrimer(Primer primer) {
        this.primer = primer;
    }

    public static class Primer {
        private long sleepMills;
        private int iterations;

        public Primer() {
        }

        public Primer(long sleepMills, int iterations) {
            this.sleepMills = sleepMills;
            this.iterations = iterations;
        }

        public long getSleepMills() {
            return sleepMills;
        }

        public void setSleepMills(long sleepMills) {
            this.sleepMills = sleepMills;
        }

        public int getIterations() {
            return iterations;
        }

        public void setIterations(int iterations) {
            this.iterations = iterations;
        }
    }
}
