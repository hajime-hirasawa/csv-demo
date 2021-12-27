package com.example.csvdemo.app.service.company;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CsvReadResult<T> {

  private T result;

  private List<String> warn;

}
