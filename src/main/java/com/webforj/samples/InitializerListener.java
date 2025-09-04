package com.webforj.samples;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;

public class InitializerListener implements ServletContextListener {
  private static final System.Logger logger = System.getLogger(InitializerListener.class.getName());

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    setupFilechooserFiles();
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    removeFilechooserFiles();
  }

  private void setupFilechooserFiles() {
    try {
      InputStream zipStream = getClass().getClassLoader().getResourceAsStream("filechooser-files.zip");
      if (zipStream == null) {
        throw new IOException("filechooser-files.zip not found in resources");
      }

      Path tempDir = Files.createTempDirectory("filechooser-files");

      try (ZipInputStream zis = new ZipInputStream(zipStream)) {
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
          Path entryPath = tempDir.resolve(entry.getName());
          if (entry.isDirectory()) {
            Files.createDirectories(entryPath);
          } else {
            Files.createDirectories(entryPath.getParent());
            Files.copy(zis, entryPath, StandardCopyOption.REPLACE_EXISTING);
          }
          zis.closeEntry();
        }
      }

      System.setProperty("filechooser-files.path", tempDir.toString());
      logger.log(System.Logger.Level.INFO, "filechooser-files are extracted to: " + tempDir);
    } catch (IOException e) {
      logger.log(System.Logger.Level.ERROR, "Error extracting filechooser-files.zip", e);
    }
  }

  private void removeFilechooserFiles() {
    String tempDir = System.getProperty("filechooser-files.path");
    try {
      FileUtils.forceDelete(new File(tempDir));
      logger.log(System.Logger.Level.INFO, "filechooser-files are deleted from: " + tempDir);
    } catch (IOException e) {
      logger.log(System.Logger.Level.ERROR, "Error deleting filechooser-files", e);
    }
  }
}
