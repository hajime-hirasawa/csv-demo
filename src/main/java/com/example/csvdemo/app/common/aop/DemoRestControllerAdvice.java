package com.example.csvdemo.app.common.aop;

import com.example.csvdemo.app.common.bean.DemoResponse;
import java.util.Collections;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class DemoRestControllerAdvice {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public DemoResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    log.warn("", e);
    return DemoResponse.asError("01", e.getAllErrors().stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.toList()));
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public DemoResponse<?> handleMethodArgumentNotValidException(Exception e) {
    log.error("", e);
    return DemoResponse.asError("99", Collections.singletonList("システムエラー"));
  }

}
