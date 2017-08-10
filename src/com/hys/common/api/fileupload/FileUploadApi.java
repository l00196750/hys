package com.hys.common.api.fileupload;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import com.hys.common.api.base.BaseApi;
import com.hys.common.utils.IdWorker;
import com.hys.common.utils.LocalDates;
import com.hys.common.utils.Loggers;

@RestController
@RequestMapping("/auth/file")
public class FileUploadApi implements BaseApi {

    private Logger logger = Loggers.getLogger(FileUploadApi.class);

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public List<FileUploadResponse> upload(HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) request;

        String saveParentDir = new StringBuilder().append(request.getSession().getServletContext().getRealPath("/")).append("upload")
            .append(File.separator).append(LocalDates.now(null)).append(File.separator).toString();
        logger.debug("saveParentDir {}", saveParentDir);
        createParentDir(saveParentDir);

        List<FileUploadResponse> fileUploadResponselist = Lists.newArrayList();
        for (MultipartFile multipartFile : mreq.getFiles("files")) {
            FileUploadResponse fileUploadResponse = saveUploadFile(saveParentDir, multipartFile);
            if (fileUploadResponse != null) {
                fileUploadResponselist.add(fileUploadResponse);
            }
        }

        return fileUploadResponselist;
    }

    private FileUploadResponse saveUploadFile(String saveParentDir, MultipartFile multipartFile) throws IllegalStateException, IOException {
        if (Strings.isNullOrEmpty(multipartFile.getOriginalFilename())) {
            return null;
        }

        if (multipartFile.isEmpty()) {
            logger.debug("{} is empty.", multipartFile.getOriginalFilename());
        }

        String rawFilename = multipartFile.getOriginalFilename();
        String saveFileName = new StringBuilder().append(LocalDates.now(null)).append(".").append(IdWorker.nextId()).append(".")
            .append(StringUtils.substringAfterLast(rawFilename, ".")).toString();

        File targetFile = new File(saveParentDir + File.separator + saveFileName);
        multipartFile.transferTo(targetFile);

        logger.debug("rawFileName {}, saveFileName {}", rawFilename, targetFile.getAbsolutePath());
        FileUploadResponse fileUploadResponse = new FileUploadResponse();
        fileUploadResponse.setRawFilename(rawFilename);
        fileUploadResponse.setSaveFileName(saveFileName);
        return fileUploadResponse;
    }

    private void createParentDir(String saveParentDir) throws IOException {
        File file = new File(saveParentDir);
        if (!file.exists()) {
            file.mkdirs();
            if (!file.isDirectory()) {
                throw new IOException("Unable to create parent directories of " + file);
            }
        }
    }
}
