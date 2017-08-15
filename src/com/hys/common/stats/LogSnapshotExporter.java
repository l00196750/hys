package com.hys.common.stats;

import com.hys.common.utils.Loggers;

import java.util.Collection;

import org.slf4j.Logger;

public class LogSnapshotExporter implements SnapshotExporter {

    @Override
    public void export(Collection<StatsSnapshot> snapshots) {
        Logger logger = Loggers.getLogger(LogSnapshotExporter.class);
        for (StatsSnapshot snapshot : snapshots) {
            logger.debug("{}", snapshot);
        }
    }
}
