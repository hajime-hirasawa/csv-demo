package com.example.csvdemo.validator;

import javax.validation.Validator;
import javax.validation.spi.ConfigurationState;
import org.hibernate.validator.internal.engine.ValidatorFactoryImpl;

public class CustomValidatorFactory extends ValidatorFactoryImpl {

  public CustomValidatorFactory(ConfigurationState configurationState) {
    super(configurationState);
  }

  @Override
  public Validator getValidator() {
    return new ValidatorWrapper(super.getValidator());
  }
}
