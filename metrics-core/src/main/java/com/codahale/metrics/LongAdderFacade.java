package com.codahale.metrics;

interface LongAdderFacade {

    void add(long x);

    long sum();

    void increment();

    void decrement();

    long sumThenReset();
}
