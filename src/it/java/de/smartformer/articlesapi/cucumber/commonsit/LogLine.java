package de.smartformer.articlesapi.cucumber.commonsit;

import java.time.LocalDateTime;

public class LogLine {

  private Enum<LogLineEnum> type;
  private LocalDateTime timestamp;
  private Integer code;
  private String message;

  public LogLine(Enum<LogLineEnum> type, LocalDateTime timestamp, Integer code, String message) {

    

    this.type = type;
    this.timestamp = timestamp;
    this.code = code;
    this.message = message;
  }

  public Enum<LogLineEnum> getType() {
    return this.type;
  }

  public LocalDateTime getTimestamp() {
    return this.timestamp;
  }

  public Integer getCode() {
    return this.code;
  }

  public String getMessage() {
    return this.message;
  }

  public void setType(Enum<LogLineEnum> type) {
    this.type = type;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "LogLine{" + "type=" + this.type + ", timestamp=" + this.timestamp + ", code=" + this.code + ", message=" + this.message + '}';
  }
}