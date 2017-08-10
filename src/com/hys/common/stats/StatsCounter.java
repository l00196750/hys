package com.hys.common.stats;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.AtomicLongMap;
import com.google.common.util.concurrent.Uninterruptibles;

public final class StatsCounter extends Thread {

    private static class AtomicStats {

        AtomicLongMap<String> successCount = AtomicLongMap.create();

        AtomicLongMap<String> successTime = AtomicLongMap.create();

        AtomicLongMap<String> failCount = AtomicLongMap.create();

        AtomicLongMap<String> failTime = AtomicLongMap.create();
    }

    private final int delayInMinute = 1;

    private List<? extends SnapshotExporter> snapshotExporterList;

    private AtomicReference<AtomicStats> atomicReference;

    private StatsCounter() {

    }

    public static StatsCounter createStarted(String statsCounterName, List<? extends SnapshotExporter> snapshotExporterList) {
        StatsCounter statsCounter = new StatsCounter();

        statsCounter.setName(statsCounterName + "Thread");
        statsCounter.snapshotExporterList = snapshotExporterList;

        statsCounter.atomicReference = new AtomicReference<AtomicStats>();
        statsCounter.atomicReference.set(new AtomicStats());

        statsCounter.start();
        return statsCounter;
    }

    public void recordSuccess(String topic, long executeTime) {
        AtomicStats atomicStats = atomicReference.get();
        atomicStats.successCount.incrementAndGet(topic);
        atomicStats.successTime.getAndAdd(topic, executeTime);
    }

    public void recordFail(String topic, long executeTime) {
        AtomicStats atomicStats = atomicReference.get();
        atomicStats.failCount.incrementAndGet(topic);
        atomicStats.failTime.getAndAdd(topic, executeTime);
    }

    @Override
    public void run() {
        while (true) {
            long nextTimeMillis = System.currentTimeMillis() + delayInMinute * 60 * 1000;
            snapshotOutput(createStatsSnapshot());
            Uninterruptibles.sleepUninterruptibly(nextTimeMillis - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }
    }

    private Collection<StatsSnapshot> createStatsSnapshot() {
        String snapshotTime = LocalDateTime.now().plusMinutes(delayInMinute).toString();

        AtomicStats atomicStats = atomicReference.getAndSet(new AtomicStats());
        // 其他线程可能还在写入数据
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);

        Set<String> topicSet = Sets.newTreeSet();
        topicSet.addAll(atomicStats.failCount.asMap().keySet());
        topicSet.addAll(atomicStats.successCount.asMap().keySet());

        List<StatsSnapshot> snapshotList = Lists.newArrayList();
        for (String topic : topicSet) {
            StatsSnapshot snapshot = new StatsSnapshot();
            snapshot.setTopic(topic);
            snapshot.setSnapshotTime(snapshotTime);
            snapshot.setFailCount(atomicStats.failCount.get(topic));
            snapshot.setFailTime(atomicStats.failTime.get(topic));
            snapshot.setSuccessCount(atomicStats.successCount.get(topic));
            snapshot.setSuccessTime(atomicStats.successTime.get(topic));
            snapshotList.add(snapshot);
        }

        return snapshotList;
    }

    private void snapshotOutput(Collection<StatsSnapshot> snapshots) {
        if (snapshots.isEmpty()) {
            return;
        }

        if (snapshotExporterList != null) {
            for (SnapshotExporter snapshotExporter : snapshotExporterList) {
                snapshotExporter.export(snapshots);
            }
        }
    }
}
