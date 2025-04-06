package com.gj.cloud.common.id;

/**
 * @author houby@email.com
 * @date 2025/1/6
 */
public class GjIdHelper {
    private static SnowflakeIdWorker worker;

    public static synchronized void init(long workerId) {
        if (worker == null) {
            worker = new SnowflakeIdWorker(workerId);
        }
    }

    public static final long nextId() {
        if (worker == null) {
            init(0L);
        }

        return worker.nextId();
    }

    public static final String nextStringId() {
        return Long.toString(nextId());
    }
}
