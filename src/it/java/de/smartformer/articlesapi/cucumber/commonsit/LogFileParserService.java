package de.smartformer.articlesapi.cucumber.commonsit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.Iterator;

public class LogFileParserService {

    private String logFilePath;

    private RandomAccessFile logFile;

    // https://www.javatpoint.com/java-arraylist
    private ArrayList<LogLine> logLines = new ArrayList<LogLine>(); // internal state

    public LogFileParserService(String logFilePath) throws IOException {

        this.logFilePath = logFilePath;

        // Reset inernal state
        this.logLines.clear();

        //File file = new File(this.logFilePath);
        //if (!file.exists()) {
        //    throw new InvalidPathException(this.logFilePath, "File does not exist");
        //}
        //https://docs.oracle.com/javase/7/docs/api/java/io/RandomAccessFile.html
        this.logFile = new RandomAccessFile(this.logFilePath, "r");
        // Reset Filepointer
        this.logFile.seek(0);
    }

    /**
     * 
     * @param logCode
     * 
     *                Check for a log line with the regarding logCode
     * @throws IOException
     */
    public Boolean checkCode(Integer logCode) throws IOException {
        this.fetchLatestLogs();
        // if LogCode in logLines => true
        // else false

        return true;
    }

    /**
     * 
     * @param logCode
     * @param match
     * 
     *                Check for a log line with the regarding logCode and match the
     *                String as part of the log message
     * @throws IOException
     */
    public Boolean checkMatch(Integer logCode, String match) throws IOException {
        this.fetchLatestLogs();
        // if LogCode in logLines => true
        // else false

        return true;
    }

    private void fetchLatestLogs() throws IOException {
        // Compare pointer to file size
        // if filesize bigger than last pointer
        //      => fetch latest lines from filepointer to current endOfFile
        //      => parse the lines for our Log Format ([1234] - Bla, whatever
        //      => add only all lines, following our log format to the internal state
        //      => set the pointer to the EoF
        // else keep pointer as is and do nothing

        String line = null;

        while((line = this.logFile.readLine()) != null){
            this.parseLine(line);
        }
    }

    private void parseLine(String line){
        // @Todo
        // Parse the line for our log format
        // if it matches, cut its contents and fill them into the internal state
        this.logLines.add(new LogLine(LogLineEnum.INFO, 4001, line));
    }

    /**
     * print all log lines to stdout
     */
    public void print(){
        Iterator<LogLine> itr = this.logLines.iterator();
        //traversing elements of ArrayList object
        while(itr.hasNext()){
            LogLine ll = (LogLine) itr.next();
            System.out.println(ll);
        }
    }
    
}
