package com.hys.common.stats;

import java.util.Collection;

import org.slf4j.Logger;

import com.hys.common.utils.Loggers;

public class LogSnapshotExporter implements SnapshotExporter {

    @Override
    public void export(Collection<StatsSnapshot> snapshots) {
        Logger logger = Loggers.getLogger(LogSnapshotExporter.class);
        for (StatsSnapshot snapshot : snapshots) {
            logger.debug("{}", snapshot);
        }
    }
}
