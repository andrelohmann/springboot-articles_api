package de.smartformer.articlesapi.cucumber.commonsit;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogFileParserService {

    private String logFilePath;

    private RandomAccessFile logFile;

    private ArrayList<LogLine> logLines;

    // ----- Parser Logic
    private String logLineRegex = "^(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}\\.\\d{3})(\\s+)(INFO|ERROR)(.*)(:\\s)\\[(\\d{4})\\](\\s+)(.*)";
    private Pattern logLinePattern = Pattern.compile(this.logLineRegex);
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    // -----

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
        // Update the internal state
        this.fetchLatestLogs();

        // create the iterator
        Iterator<LogLine> itr = this.logLines.iterator();
        //traversing elements of ArrayList object
        while(itr.hasNext()){
            LogLine ll = (LogLine) itr.next();
            if(ll.getCode().equals(logCode)) return true;
        }

        return false;
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
        // Update the internal state
        this.fetchLatestLogs();

        // create the iterator
        Iterator<LogLine> itr = this.logLines.iterator();
        //traversing elements of ArrayList object
        while(itr.hasNext()){
            LogLine ll = (LogLine) itr.next();
            if(ll.getCode().equals(logCode) && ll.getMessage().contains(match)) return true;
        }

        return false;
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

        Matcher matcher = this.logLinePattern.matcher(line);

        if(matcher.matches()){
            LocalDateTime timestamp = LocalDateTime.parse(matcher.group(1), dateTimeFormatter);

            this.logLines.add(new LogLine(LogLineEnum.valueOf(matcher.group(3)), timestamp, Integer.parseInt(matcher.group(6)), matcher.group(8)));
        }
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
