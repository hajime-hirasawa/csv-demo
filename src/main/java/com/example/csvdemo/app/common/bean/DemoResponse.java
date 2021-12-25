package com.example.csvdemo.app.common.bean;

import java.util.List;
import lombok.Data;

@Data
public class DemoResponse<T> {

  private String resultCode;

  private List<String> messages;

  private T result;
}
