package com.example.csvdemo.validator.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;


@Target(FIELD)
@Retention(RUNTIME)
@Documented
@AdditionalValidateParam(name = "fieldName")
public @interface FieldName {

  @AliasFor(annotation = AdditionalValidateParam.class, attribute = "value")
  String value();
}
