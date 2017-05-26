package com.gmail.at.rospopa.pavlo.projectmanager.dispatcher;

import java.util.Arrays;
import java.util.stream.Stream;

public enum HttpMethod {
    GET(1), POST(2), DELETE(4), PUT(8);

    private final int mask;

    private static final int ANY_METHOD_MASK;

    static {
        ANY_METHOD_MASK = Arrays.asList(HttpMethod.values()).stream()
                .map(m -> m.mask)
                .reduce((r1, r2) -> r1 | r2)
                .get();
    }

    HttpMethod(int mask) {
        this.mask = mask;
    }

    /**
     * Create a bit mask by combining HTTP methods
     *
     * @return an Object that holds a single int value - the bit mask
     */
    public static HttpMethodMask combine(HttpMethod... methods) {
        return new HttpMethodMask(
                Stream.of(methods).map(m -> m.mask)
                        .reduce((r1, r2) -> r1 | r2)
                        .orElse(0));
    }

    /**
     * Check if the given method matches against the given bit mask. User {@link #combine}
     * for creating bit masks
     */
    public boolean matches(int mask) {
        return (this.mask | mask) != 0;
    }

    /**
     * A bit mask that matches any HTTP methods
     */
    public static HttpMethodMask any() {
        return new HttpMethodMask(ANY_METHOD_MASK);
    }

    /**
     * A bit mask that matches `modifying` HTTP methods: POST, DELETE, PUT
     *
     * @return an instance of HttpMethodMask
     */
    public static HttpMethodMask modifying() {
        return combine(POST, DELETE, PUT);
    }

    /**
     * Helper holder class that contains a single http method bit mask
     * Introduced to get rid of magic numbers
     * <p>
     * To create an HttpMethodMask user one of: {@link #combine} {@link #any} or
     * {@link #modifying}
     */
    public static class HttpMethodMask {
        private int mask;

        private HttpMethodMask(int mask) {
            this.mask = mask;
        }

        public int getMask() {
            return mask;
        }
    }
}