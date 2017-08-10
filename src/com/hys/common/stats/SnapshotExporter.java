package com.hys.common.stats;

import java.util.Collection;

public interface SnapshotExporter {
    public void export(Collection<StatsSnapshot> snapshots);
}
