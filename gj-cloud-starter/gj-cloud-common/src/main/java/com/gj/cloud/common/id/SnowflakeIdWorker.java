package com.gj.cloud.common.id;

import java.time.Instant;

/**
 * @author houby@email.com
 * @date 2025/1/6
 */
public class SnowflakeIdWorker {
    public SnowflakeIdWorker(long workerId) {
        if (workerId > maxWorkerUd) {
            workerId = 0L;
        }

        this.workerId = (workerId == 0L ? (Instant.now().toEpochMilli() % maxWorkerUd) : workerId);
    }


    private final long workerIdBits = 8L; // workerId 取 SSS
    private final long sequenceBits = 5L;

    /*
     * max values of sequence
     */
    private final long maxSequence = -1L ^ (-1L << sequenceBits); // 2^5-1
    private final long maxWorkerUd = -1L ^ (-1L << workerIdBits); // 2^8-1

    /**
     * left shift bits of timeStamp, workerId
     */
    private final long timestampShift = sequenceBits + workerIdBits;
    private final long workerIdShift = sequenceBits;

    /*
     * object status variables
     */

    /**
     * reference material of 'time stamp' is '2019-01-01'. its value can't be
     * modified after initialization.
     */
    private final long epoch = 1546214400000L;

    /**
     * machine or process number, its value can't be modified after
     * initialization.
     * <p>
     * max: 2^8-1 range: [0,255]
     */
    private final long workerId;

    /**
     * the unique and incrementing sequence number scoped in only one
     * period/unit (here is ONE millisecond). its value will be increased by 1
     * in the same specified period and then reset to 0 for next period.
     * <p>
     * max: 2^5-1 range: [0,31]
     */
    private long sequence = 0L;

    /**
     * the time stamp last snowflake ID generated
     */
    private long lastTimestamp = -1L;

    /**
     * generate an unique and incrementing id
     *
     * @return id
     */
    public synchronized long nextId() {
        long currTimestamp = timestampGen();

        if (currTimestamp < lastTimestamp) {
            long diff = lastTimestamp - currTimestamp;

            if (diff > 1) {
                sleep(diff);
            }

            currTimestamp = timestampGen();

            while (currTimestamp < lastTimestamp) { // wait for time correction
                sleep(1);

                currTimestamp = timestampGen();
            }
        }

        if (currTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & maxSequence;
            if (sequence == 0) { // overflow: greater than max sequence
                currTimestamp = waitNextMillis(currTimestamp);
            }

        } else { // reset to 0 for next period/millisecond
            sequence = 0L;
        }

        // track and memo the time stamp last snowflake ID generated
        lastTimestamp = currTimestamp;

        return ((currTimestamp - epoch) << timestampShift) | //
                (workerId << workerIdShift) | // new line for nice looking
                sequence;
    }

    /**
     * running loop blocking until next millisecond
     *
     * @param currTimestamp current time stamp
     * @return current time stamp in millisecond
     */
    protected long waitNextMillis(long currTimestamp) {
        while (currTimestamp <= lastTimestamp) {
            currTimestamp = timestampGen();
        }
        return currTimestamp;
    }

    /**
     * get current time stamp
     *
     * @return current time stamp in millisecond
     */
    protected long timestampGen() {
        return System.currentTimeMillis();
    }

    public long getEpoch() {
        return this.epoch;
    }

    //------------------------------------------------------------------------
    // 私有方法
    //------------------------------------------------------------------------
    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ie) {
            /* ignore */
        }
    }
}
