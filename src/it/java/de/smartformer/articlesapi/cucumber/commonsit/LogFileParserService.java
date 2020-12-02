package de.smartformer.articlesapi.cucumber.commonsit;

public class LogFileParserService {

    private String logFile;

    private ArrayList<LogLine> logLines = []; //internal state

    private FilePointer pointer; // File Pointer 

    public LogFileParserService(String logFile) {
        this.logFile = logFile;

        // Create a FilePointer, 
    }

    public reset(){
        this.logLines = [];
        // also reset File Pointer
    }

    public checkCode(Integer logCode){
        this.fetchLatesLogs();
        // if LogCode in logLines => true
        // else false
    }

    private fetchLatestLogs(){
        // Compare pointer to file size
        // if filesize bigger than last pointer
        //      => fetch latest lines from filepointer to current endOfFile
        //      => parse the lines for our Log Format ([1234] - Bla, whatever
        //      => add only all lines, following our log format to the internal state
        //      => set the pointer to the EoF
        // else keep pointer as is and do nothing
    }
    
}
