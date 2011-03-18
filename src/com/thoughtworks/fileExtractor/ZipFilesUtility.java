package com.thoughtworks.fileExtractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFilesUtility {
    Properties properties;

    public ZipFilesUtility() {
        this.properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File extractAppLogs() throws IOException {
        File zipFile = File.createTempFile("logs", ".zip", new File("/tmp"));
        String path = this.properties.getProperty("appLogDirectory");
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));
        File directory = new File(path);
        File[] files = directory.listFiles();
        for (File file : files) {
            FileInputStream fin = new FileInputStream(file);
            zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
            int length;
            byte[] buffer = new byte[new Long(file.length()).intValue()];
            while ((length = fin.read(buffer)) > 0) {
                zipOutputStream.write(buffer, 0, length);
            }
            zipOutputStream.closeEntry();
            fin.close();
        }
        zipOutputStream.close();
        return zipFile;
    }
}
