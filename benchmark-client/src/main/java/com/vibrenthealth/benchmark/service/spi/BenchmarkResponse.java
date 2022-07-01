package com.vibrenthealth.benchmark.service.spi;

import java.util.ArrayList;
import java.util.List;

public class BenchmarkResponse {

    private String serviceType;
    private String testId;
    private int loopCount;
    private int repeats;
    private long sleepMills;
    private SummaryStats primerResults;
    private Summary resultsSummary;
    private List<RawResults> rawMetrics;

    public BenchmarkResponse() {
    }

    public BenchmarkResponse(String serviceType, String testId, int loopCount, int repeats, long sleepMills, SummaryStats primerResults, Summary resultsSummary, List<RawResults> rawMetrics) {
        this.serviceType = serviceType;
        this.testId = testId;
        this.loopCount = loopCount;
        this.repeats = repeats;
        this.sleepMills = sleepMills;
        this.primerResults = primerResults;
        this.resultsSummary = resultsSummary;
        this.rawMetrics = rawMetrics;
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

    public SummaryStats getPrimerResults() {
        return primerResults;
    }

    public void setPrimerResults(SummaryStats primerResults) {
        this.primerResults = primerResults;
    }

    public Summary getResultsSummary() {
        return resultsSummary;
    }

    public void setResultsSummary(Summary resultsSummary) {
        this.resultsSummary = resultsSummary;
    }

    public List<RawResults> getRawMetrics() {
        if(rawMetrics == null) rawMetrics = new ArrayList<RawResults>();
        return rawMetrics;
    }

    public void setRawMetrics(List<RawResults> rawMetrics) {
        this.rawMetrics = rawMetrics;
    }

    public static class SummaryStats {
        private long min;
        private long max;
        private float avg;
        private float std;

        public SummaryStats() {
        }

        public SummaryStats(long min, long max, float avg, float std) {
            this.min = min;
            this.max = max;
            this.avg = avg;
            this.std = std;
        }

        public long getMin() {
            return min;
        }

        public void setMin(long min) {
            this.min = min;
        }

        public long getMax() {
            return max;
        }

        public void setMax(long max) {
            this.max = max;
        }

        public float getAvg() {
            return avg;
        }

        public void setAvg(float avg) {
            this.avg = avg;
        }

        public float getStd() {
            return std;
        }

        public void setStd(float std) {
            this.std = std;
        }
    }

    public static class Summary {
        private SummaryStats serviceSideDuration;
        private SummaryStats clientSideDuration;
        private SummaryStats requestLatency;
        private SummaryStats responseLatency;

        public Summary() {
        }

        public Summary(SummaryStats serviceSideDuration, SummaryStats clientSideDuration, SummaryStats requestLatency, SummaryStats responseLatency) {
            this.serviceSideDuration = serviceSideDuration;
            this.clientSideDuration = clientSideDuration;
            this.requestLatency = requestLatency;
            this.responseLatency = responseLatency;
        }

        public SummaryStats getServiceSideDuration() {
            return serviceSideDuration;
        }

        public void setServiceSideDuration(SummaryStats serviceSideDuration) {
            this.serviceSideDuration = serviceSideDuration;
        }

        public SummaryStats getClientSideDuration() {
            return clientSideDuration;
        }

        public void setClientSideDuration(SummaryStats clientSideDuration) {
            this.clientSideDuration = clientSideDuration;
        }

        public SummaryStats getRequestLatency() {
            return requestLatency;
        }

        public void setRequestLatency(SummaryStats requestLatency) {
            this.requestLatency = requestLatency;
        }

        public SummaryStats getResponseLatency() {
            return responseLatency;
        }

        public void setResponseLatency(SummaryStats responseLatency) {
            this.responseLatency = responseLatency;
        }
    }

    public static class RawResults {

        private int repeatId;
        private int loopId;
        private long clientSent;
        private long serviceReceived;
        private long serviceResponded;
        private long clientReceived;
        private long serviceDuration;
        private long clientDuration;

        public RawResults() {
        }

        public RawResults(int repeatId, int loopId, long serviceReceived, long serviceResponded, long clientReceived, long serviceDuration, long clientDuration) {
            this.repeatId = repeatId;
            this.loopId = loopId;
            this.serviceReceived = serviceReceived;
            this.serviceResponded = serviceResponded;
            this.clientReceived = clientReceived;
            this.serviceDuration = serviceDuration;
            this.clientDuration = clientDuration;
        }

        public int getRepeatId() {
            return repeatId;
        }

        public void setRepeatId(int repeatId) {
            this.repeatId = repeatId;
        }

        public int getLoopId() {
            return loopId;
        }

        public void setLoopId(int loopId) {
            this.loopId = loopId;
        }

        public long getServiceReceived() {
            return serviceReceived;
        }

        public void setServiceReceived(long serviceReceived) {
            this.serviceReceived = serviceReceived;
        }

        public long getServiceResponded() {
            return serviceResponded;
        }

        public void setServiceResponded(long serviceResponded) {
            this.serviceResponded = serviceResponded;
        }

        public long getClientReceived() {
            return clientReceived;
        }

        public void setClientReceived(long clientReceived) {
            this.clientReceived = clientReceived;
        }

        public long getServiceDuration() {
            return serviceDuration;
        }

        public void setServiceDuration(long serviceDuration) {
            this.serviceDuration = serviceDuration;
        }

        public long getClientDuration() {
            return clientDuration;
        }

        public void setClientDuration(long clientDuration) {
            this.clientDuration = clientDuration;
        }

        public long getClientSent() {
            return clientSent;
        }

        public void setClientSent(long clientSent) {
            this.clientSent = clientSent;
        }
    }
}
