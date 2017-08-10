package com.hys.common.stats;

import java.io.IOException;
import java.util.Collection;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.io.Files;

import com.hys.common.utils.Loggers;

public class FileSnapshotExporter implements SnapshotExporter {

    private String fileName;

    public FileSnapshotExporter(String fileName) {
        this.fileName = Preconditions.checkNotNull(fileName);
    }

    @Override
    public void export(Collection<StatsSnapshot> snapshots) {
        try {
            appendToFile(snapshots);
        }
        catch (Throwable e) {
            Loggers.getLogger(FileSnapshotExporter.class).error("{}", e);
        }
    }

    private java.io.File openFile() throws IOException {
        java.io.File outfile = new java.io.File(fileName);
        if (!outfile.exists()) {
            Files.createParentDirs(outfile);
            Files.touch(outfile);
        }

        return outfile;
    }

    private void appendToFile(Collection<StatsSnapshot> snapshots) throws IOException {
        java.io.File outfile = openFile();

        for (StatsSnapshot snapshot : snapshots) {
            StringBuilder text = new StringBuilder();
            text.append(snapshot.getSnapshotTime()).append(", ");
            text.append(snapshot.getTopic()).append(", ");
            text.append("suc").append(", ");
            text.append(snapshot.getSuccessCount()).append(", ");
            text.append(snapshot.getSuccessTime()).append(", ");
            text.append("fail").append(", ");
            text.append(snapshot.getFailCount()).append(", ");
            text.append(snapshot.getFailTime()).append("\n");

            Files.append(text.toString(), outfile, Charsets.UTF_8);
        }
    }
}
