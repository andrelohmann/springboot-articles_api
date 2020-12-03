package de.smartformer.articlesapi.cucumber.commonsit;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileWatcherBasic {

    /* filename have the path e.g: "./logs/filename.log" */
    private String fileName;
    private long previousFilePointer;
    private long currentFilePointer;
    private long previousLastModified;
    private long currentLastModified;

    public FileWatcherBasic(String fileName) {
        this.fileName = fileName;
    }

    private void setCurrentFilePointer() {
        try (RandomAccessFile file = new RandomAccessFile(this.fileName, "r")) {
            // create an array equal to the length of raf
            byte[] arr = new byte[(int) file.length()];
            // read the file
            file.readFully(arr);
            this.currentFilePointer = file.getFilePointer();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setPreviousFilePointer(long filePointer) {
        this.previousFilePointer = filePointer;
    }

    private void setCurrentLastModified() {
        this.currentLastModified = new File(this.fileName).lastModified();
    }

    public void setPreviousLastModified(long lastModified) {
        this.previousLastModified = lastModified;
    }

    public boolean isModifiedByFilePointer() {
        this.setCurrentFilePointer();
        return this.currentFilePointer > this.previousFilePointer;
    }

    public boolean isModifiedByDate() {
        this.setCurrentLastModified();
        return this.currentLastModified > this.previousLastModified;
    }
}
