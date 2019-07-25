package com.jamesdpeters.utils;

import org.hibernate.Session;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipUtil {
    public static void main(String[] args) throws IOException {
        InputStream fileZip = SessionStore.class.getClassLoader().getResourceAsStream("db/default-filled.mv.zip");
        File destDir = new File(SessionStore.getDbPath(""));
        unzipDB(destDir,fileZip,"default-filled.mv.db", "Test.mv.db");
    }

    public static void unzipDB(File destination, InputStream zipFile, String defaultDbName, String saveFileName) throws IOException {
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(zipFile);
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            System.out.println("zipEntry: "+zipEntry.getName());
            System.out.println("defaultDbName: "+defaultDbName);
            if(zipEntry.getName().equals(defaultDbName)) {
                File newFile = renamedFile(destination, saveFileName);
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
    }

    private static File renamedFile(File destinationDir, String newFileName) throws IOException {
        File destFile = new File(destinationDir, newFileName);

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + newFileName);
        }

        return destFile;
    }
}