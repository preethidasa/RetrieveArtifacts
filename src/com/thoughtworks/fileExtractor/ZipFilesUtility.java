package com.thoughtworks.fileExtractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFilesUtility {

    public static File extractFile(String path, File zipFile) throws IOException {
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
