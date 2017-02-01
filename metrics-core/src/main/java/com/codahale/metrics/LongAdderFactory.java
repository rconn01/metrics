package com.codahale.metrics;

public class LongAdderFactory {

    private static class JdkDelegate {
        private static LongAdderFacade get() {
            return new LongAdderFacade() {
                private final java.util.concurrent.atomic.LongAdder longAdder =
                        new java.util.concurrent.atomic.LongAdder();

                @Override
                public void add(long x) {
                    longAdder.add(x);
                }

                @Override
                public long sum() {
                    return longAdder.sum();
                }

                @Override
                public void increment() {
                    longAdder.increment();
                }

                @Override
                public void decrement() {
                    longAdder.decrement();
                }

                @Override
                public long sumThenReset() {
                    return longAdder.sumThenReset();
                }
            };
        }
    }

    private static LongAdderFacade internalLongAdder() {
        return new LongAdderFacade() {
            private final LongAdder longAdder = new LongAdder();

            @Override
            public void add(long x) {
                longAdder.add(x);
            }

            @Override
            public long sum() {
                return longAdder.sum();
            }

            @Override
            public void increment() {
                longAdder.increment();
            }

            @Override
            public void decrement() {
                longAdder.decrement();
            }

            @Override
            public long sumThenReset() {
                return longAdder.sumThenReset();
            }
        };
    }

    private static final boolean IS_JDK_LONG_ADDER_AVAILABLE = isIsJdkLongAdderAvailable();
    private static boolean isIsJdkLongAdderAvailable() {
        try {
            JdkDelegate.get();
            return true;
        } catch (NoClassDefFoundError e) {
            return false;
        }
    }

    public static LongAdderFacade create() {
        return IS_JDK_LONG_ADDER_AVAILABLE ? JdkDelegate.get() : internalLongAdder();
    }
}
