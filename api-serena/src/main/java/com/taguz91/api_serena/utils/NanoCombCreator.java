package com.taguz91.api_serena.utils;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * Utility class that creates a COMB GUID with nanoseconds resolution.
 *
 * It borrows the main idea from ULID and COMB generators: a concatenation of
 * time and random bytes. It is composed of 64 bits for time and 64 for random
 * bits.
 *
 * A Nano COMB has two components:
 *
 * 1. Time camponent (64 bits): nanoseconds since 1970
 *
 * 2. Random component (64 bits): a value generated by a secure random
 * generator.
 *
 * Maximum time component year is ~2262 A.D. (2^63/10^9/60/60/24/365.25 + 1970)
 *
 * @author: Fabio Lima 2020
 */
public final class NanoCombCreator {

    private long prevTime = 0;
    private long prevNano = 0;

    private static final long ONE_MILLION_NANOSECONDS = 1_000_000L;

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    /**
     * Returns a time component in nanoseconds.
     *
     * It uses `System.currentTimeMillis()` to get the system time in milliseconds
     * accuracy. The nanoseconds resolution is simulated by calling
     * `System.nanoTime()` between subsequent calls within the same millisecond.
     * It's not precise, but it provides some monotonicity to the values generates.
     *
     * @return the current time in nanoseconds
     */
    private synchronized long getTimeComponent() {

        final long time = System.currentTimeMillis();
        final long nano = System.nanoTime();
        final long elapsed; // nanoseconds since last call

        if (time == prevTime) {
            elapsed = (nano - prevNano);
            if (elapsed > ONE_MILLION_NANOSECONDS) {
                try {
                    // make the clock to catch up
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    System.err.println("something went wrong...");
                }
            }
        } else {
            prevTime = time;
            prevNano = nano;
            elapsed = 0;
        }

        return (time * ONE_MILLION_NANOSECONDS) + elapsed;
    }

    /**
     * Returns the random component using a secure random generator.
     *
     * @return a random value.
     */
    private synchronized long getRandomComponent() {
        return SECURE_RANDOM.nextLong();
    }

    /**
     * Returns a Nano COMB.
     *
     * A Nano COMB is inspired on ULID and COMB generators.
     *
     * It is composed of 64 bits for time and 64 for random bits.
     *
     * @return a UUID
     */
    public synchronized UUID create() {

        final long timeBits = getTimeComponent();
        final long randomBits = getRandomComponent();

        return new UUID(timeBits, randomBits);
    }
}
