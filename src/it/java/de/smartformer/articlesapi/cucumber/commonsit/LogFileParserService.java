package de.smartformer.articlesapi.cucumber.commonsit;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;

public class LogFileParserService {

    private String logFilePath;

    private RandomAccessFile logFile;

    private ArrayList<LogLine> logLines;

    public LogFileParserService(String logFilePath) throws IOException {

        this.logFilePath = logFilePath;

        // Empty inernal state
        this.logLines = new ArrayList<LogLine>();

        //https://docs.oracle.com/javase/7/docs/api/java/io/RandomAccessFile.html
        this.logFile = new RandomAccessFile(this.logFilePath, "r");
        // Set the Filepointer to start
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

        String line = null;

        // As long as the file handle is kept open,
        // readLine will always read until it sees EoF
        // if there have been lines added
        // readLine will read until the new EoF
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
