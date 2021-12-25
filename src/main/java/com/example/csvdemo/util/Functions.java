package com.example.csvdemo.util;

public class Functions {

  @SuppressWarnings("unchecked")
  public static <T> T as (Object obj) {
    return (T) obj;
  }

  public static <T> T cast(Object obj, Class<T> clazz) {
    return as(obj);
  }

}
