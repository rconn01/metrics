package com.codahale.metrics;

import java.util.Random;

class ThreadLocalRandomFactory {

    /**
     * To avoid NoClassDefFoundError during loading {@link ThreadLocalRandomFactory}
     */
    private static class JdkDelegate {

        private static Random get() {
            return java.util.concurrent.ThreadLocalRandom.current();
        }
    }

    private static final boolean IS_JDK_THREAD_LOCAL_AVAILABLE = isIsJdkThreadLocalAvailable();
    private static boolean isIsJdkThreadLocalAvailable() {
        try {
            JdkDelegate.get();
            return true;
        } catch (NoClassDefFoundError e) {
            return false;
        }
    }

    public static Random current() {
        return IS_JDK_THREAD_LOCAL_AVAILABLE ? JdkDelegate.get() : ThreadLocalRandom.current();
    }

}
