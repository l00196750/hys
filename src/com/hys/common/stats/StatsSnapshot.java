package com.hys.common.stats;

import com.google.common.base.MoreObjects;

public class StatsSnapshot {

    private String snapshotTime;

    private String topic;

    private long successCount;

    private long successTime;

    private long failCount;

    private long failTime;

    public long getFailCount() {
        return failCount;
    }

    public long getFailTime() {
        return failTime;
    }

    public String getSnapshotTime() {
        return snapshotTime;
    }

    public long getSuccessCount() {
        return successCount;
    }

    public long getSuccessTime() {
        return successTime;
    }

    public String getTopic() {
        return topic;
    }

    public void setFailCount(long failCount) {
        this.failCount = failCount;
    }

    public void setFailTime(long failTime) {
        this.failTime = failTime;
    }

    public void setSnapshotTime(String snapshotTime) {
        this.snapshotTime = snapshotTime;
    }

    public void setSuccessCount(long successCount) {
        this.successCount = successCount;
    }

    public void setSuccessTime(long successTime) {
        this.successTime = successTime;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("snapshotTime", snapshotTime).add("topic", topic).add("successCount", successCount)
            .add("successTime", successTime).add("failCount", failCount).add("failTime", failTime).toString();
    }
}
