package com.hys.common.api.fileupload;

public class FileUploadResponse {

    private String rawFilename;

    private String saveFileName;

    public String getRawFilename() {
        return rawFilename;
    }

    public String getSaveFileName() {
        return saveFileName;
    }

    public void setRawFilename(String rawFilename) {
        this.rawFilename = rawFilename;
    }

    public void setSaveFileName(String saveFileName) {
        this.saveFileName = saveFileName;
    }
}
