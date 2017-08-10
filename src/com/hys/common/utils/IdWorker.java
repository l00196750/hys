package com.hys.common.utils;

import java.util.UUID;

import org.slf4j.Logger;

import com.google.common.math.LongMath;

public final class IdWorker {

    private static Logger log = Loggers.getLogger(IdWorker.class);

    private static TwitterIdWorker twitterIdWorker;
    static {
        long twitterId = 0;
        try {
            twitterId = 0;
            // Long.valueOf(SeqUtil.getSeq("WFM_TWITTER_ID"))
        }
        catch (Throwable e) {
            twitterId = Math.abs(UUID.randomUUID().getMostSignificantBits());
        }

        twitterId = LongMath.mod(twitterId, 1024);
        long datacenterId = twitterId / 32;
        long workerId = LongMath.mod(twitterId, 32);
        log.debug("twitterId {}, datacenterId {}, workerId {}", twitterId, datacenterId, workerId);

        twitterIdWorker = new TwitterIdWorker(workerId, datacenterId);
    }

    private IdWorker() {

    }

    public static long nextId() {
        return twitterIdWorker.nextId();
    }

    public static String nextIdStr() {
        return String.valueOf(twitterIdWorker.nextId());
    }

    private static class TwitterIdWorker {

        /**
         * 机器标识(实际为线程标识)
         */
        private long workerId;

        /**
         * 数据中心标识(IP地址?)
         */
        private long datacenterId;

        /**
         * 当前毫秒内计数
         */
        private long sequence;

        /**
         * 时间戳基数, 此值越大, 生成的ID越小
         */
        private long twepoch = 1500343979607L;

        /**
         * 机器标识位数
         */
        private long workerIdBits = 5L;

        /**
         * 数据中心标识位数
         */
        private long datacenterIdBits = 5L;

        /**
         * 机器标识最大值
         */
        private long maxWorkerId = -1L ^ (-1L << workerIdBits);

        /**
         * 数据中心标识最大值
         */
        private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

        /**
         * 当前毫秒内计数 位数
         */
        private long sequenceBits = 12L;

        /**
         * 机器标识左移位数
         */
        private long workerIdShift = sequenceBits;

        /**
         * 数据中心标识左移位数
         */
        private long datacenterIdShift = sequenceBits + workerIdBits;

        /**
         * 毫秒级时间左移位数
         */
        private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

        /**
         * 当前毫秒内计数 最大值
         */
        private long sequenceMask = -1L ^ (-1L << sequenceBits);

        /**
         * 上个时间
         */
        private long lastTimestamp = -1L;

        /**
         * 构造函数
         * 
         * @param workerId 机器标识
         * @param datacenterId 数据中心标识
         */
        public TwitterIdWorker(long workerId, long datacenterId) {
            // sanity check for workerId
            if (workerId > maxWorkerId || workerId < 0) {
                throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
            }
            if (datacenterId > maxDatacenterId || datacenterId < 0) {
                throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
            }
            this.workerId = workerId;
            this.datacenterId = datacenterId;
            log.info(String.format(
                "worker starting. timestamp left shift %d, datacenter id bits %d, worker id bits %d, sequence bits %d, workerid %d",
                timestampLeftShift, datacenterIdBits, workerIdBits, sequenceBits, workerId));
        }

        /**
         * [取序列号] <br>
         * 
         * @author [li.yangqi]<br>
         * @taskId <br>
         * @return 序列号<br>
         */
        public synchronized long nextId() {
            long timestamp = timeGen();

            if (timestamp < lastTimestamp) {
                log.error(String.format("clock is moving backwards.  Rejecting requests until %d.", lastTimestamp));
                throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp
                    - timestamp));
            }

            if (lastTimestamp == timestamp) {
                sequence = (sequence + 1) & sequenceMask;
                if (sequence == 0) {
                    timestamp = tilNextMillis(lastTimestamp);
                }
            }
            else {
                sequence = 0L;
            }

            lastTimestamp = timestamp;

            return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
        }

        /**
         * [取系统下一个毫秒级时间] <br>
         * 
         * @author [li.yangqi]<br>
         * @taskId <br>
         * @param lastTimestamp 当前毫秒级时间
         * @return 下一个毫秒级时间<br>
         */
        private long tilNextMillis(long lastTimestamp) {
            long timestamp = timeGen();
            while (timestamp <= lastTimestamp) {
                timestamp = timeGen();
            }
            return timestamp;
        }

        /**
         * [取系统毫秒级时间] <br>
         * 
         * @author [li.yangqi]<br>
         * @taskId <br>
         * @return 系统毫秒级时间<br>
         */
        private long timeGen() {
            return System.currentTimeMillis();
        }
    }
}
