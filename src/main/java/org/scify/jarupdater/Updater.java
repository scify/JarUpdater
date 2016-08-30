/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scify.jarupdater;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author christina
 */
public class Updater {

    private Properties properties;

    public Updater() {
        properties = new Properties();
    }

    public void run() {
        overrideFile(properties.getJar());
        overrideFile(properties.getResourcesFolder());
        overrideFile(properties.getPropertiesFile());
    }

    private void overrideFile(String file) {
        System.out.println("Overriding: " + file);
        File source = new File(properties.getJarPath() + File.separator + file);
        File dest = new File(properties.getJarPath() + File.separator + ".." + File.separator);
        if (source.isFile() || source.isDirectory()) {
            try {
                if (source.isDirectory()) {
                    FileUtils.deleteDirectory(new File(properties.getJarPath() + File.separator + ".." + File.separator + file));
                    FileUtils.copyDirectory(source, new File(properties.getJarPath() + File.separator + ".." + File.separator + file));
                } else {
                    FileUtils.deleteQuietly(new File(properties.getJarPath() + File.separator + ".." + File.separator + file));
                    FileUtils.copyFileToDirectory(source, dest);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void startApp() {
        try {
            System.out.println("java -jar " + properties.getJarPath() + "/../" + properties.getJar());
            Process proc = Runtime.getRuntime().exec("java -jar " + properties.getJarPath() + "/../" + properties.getJar());

            InputStream in = proc.getInputStream();
            InputStream err = proc.getErrorStream();
        } catch (IOException ex) {
            Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeUpdater() {
        System.exit(0);
    }
}
