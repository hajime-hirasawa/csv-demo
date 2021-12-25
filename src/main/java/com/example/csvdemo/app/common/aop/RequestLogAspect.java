package com.example.csvdemo.app.common.aop;

import static com.example.csvdemo.util.Functions.cast;

import com.example.csvdemo.app.common.annotation.ApiName;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class RequestLogAspect {

  @Before("within(com.example.csvdemo.app.controller.*.*)")
  public void beforeHandler(JoinPoint jp) {
    String name = getApiName(jp);

    log.info("start request [{}]", name);
  }

  @After("within(com.example.csvdemo.app.controller.*.*)")
  public void afterHandler(JoinPoint jp) {
    String name = getApiName(jp);

    log.info("finish request [{}]", name);
  }

  private String getApiName(JoinPoint jp) {
    return Optional.of(jp)
        .map(JoinPoint::getSignature)
        .map(s -> cast(s, MethodSignature.class))
        .map(MethodSignature::getMethod)
        .map(c -> c.getAnnotation(ApiName.class))
        .map(ApiName::value)
        .orElse("no name");
  }
}
