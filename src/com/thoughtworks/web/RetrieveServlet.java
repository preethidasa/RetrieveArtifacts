package com.thoughtworks.web;

import com.thoughtworks.fileExtractor.ZipFilesUtility;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class RetrieveServlet extends HttpServlet {
    public static final String DEFAULT_CONTENT_TYPE = "application/zip";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        File zipFile = getFile(request);
        sendFile(response, zipFile);
    }

    public File getFile(HttpServletRequest request) throws IOException {
        String servletPath = request.getServletPath();
        if (servletPath.contains("/appLogs")) {
            return getApplicationLogsZipped();
        }
        return null;
    }

    private File getApplicationLogsZipped() throws IOException {
        File tempFile = File.createTempFile("logs", ".zip", new File("/tmp"));
        return ZipFilesUtility.extractFile(
                "/home/praveeg/third-party-lib/apache-tomcat-7.0.8/logs",
                tempFile);
    }

    public void sendFile(HttpServletResponse response, File file) {
        response.setContentType(DEFAULT_CONTENT_TYPE);
        response.setHeader("Content-Disposition", "attachment;filename=\"" + file.getAbsolutePath() + "\"");
        try {
            response.setContentLength(new Long(file.length()).intValue());
            byte[] dataInBytes = new byte[(int) file.length()];
            FileInputStream is = new FileInputStream(file);
            is.read(dataInBytes);
            ServletOutputStream op = response.getOutputStream();
            op.write(dataInBytes);
            op.flush();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
