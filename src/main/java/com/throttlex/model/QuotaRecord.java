package com.throttlex.model;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

public class QuotaRecord {
    private AtomicInteger requestCount;
    private Instant windowStart;

    public QuotaRecord(int initialCount, Instant windowStart){
        this.requestCount = new AtomicInteger((initialCount));
        this.windowStart = windowStart;
    }

    public AtomicInteger getRequestCount() {
        return requestCount;
    }

    public Instant getWindowStart() {
        return windowStart;
    }

    public void setWindowStart(Instant windowStart) {
        this.windowStart = windowStart;
    }
}
