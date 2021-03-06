package com.example.csvdemo.app.common.bean;

import java.util.List;
import lombok.Data;

@Data
public class DemoResponse<T> {

  private String resultCode = "00";

  private List<String> messages;

  private T result;

  public static<T> DemoResponse<T> asSuccess(T result, List<String> messages) {
    DemoResponse<T> response = new DemoResponse<>();
    response.setResult(result);
    response.setMessages(messages);
    return response;
  }

  public static<T> DemoResponse<T> asError(String resultCode, List<String> messages) {
    DemoResponse<T> response = new DemoResponse<>();
    response.setResultCode(resultCode);
    response.setMessages(messages);
    return response;
  }
}
