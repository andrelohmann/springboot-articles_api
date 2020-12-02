package de.smartformer.articlesapi.cucumber.commonsit;

public class LogLine {

  private Enum<LogLineEnum> type;
  private Integer code;
  private String message;

  public LogLine(Enum<LogLineEnum> type, Integer code, String message) {

    this.type = type;
    this.code = code;
    this.message = message;
  }

  public Enum<LogLineEnum> getType() {
    return this.type;
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

  public void setCode(Integer code) {
    this.code = code;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "LogLine{" + "type=" + this.type + ", code='" + this.code + '\'' + ", message='" + this.message + '\'' + '}';
  }
}