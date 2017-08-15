package com.hys.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

public class HttpServletResponses {

    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * 向HttpServletResponse写入json文件.
     */
    public static void writeJson(HttpServletResponse response, String text) throws IOException {
        response.setHeader("content-type", " application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        OutputStream outputStream = response.getOutputStream();
        outputStream.write(Strings.nullToEmpty(text).getBytes(Charsets.UTF_8));
    }

    public static void writeJson(HttpServletResponse response, Object object) throws IOException {
        writeJson(response, mapper.writeValueAsString(object));
    }
}
