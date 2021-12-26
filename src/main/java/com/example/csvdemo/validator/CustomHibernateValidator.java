package com.example.csvdemo.validator;

import javax.validation.ValidatorFactory;
import javax.validation.spi.ConfigurationState;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.internal.engine.ValidatorFactoryImpl;

public class CustomHibernateValidator extends HibernateValidator {

  @Override
  public ValidatorFactory buildValidatorFactory(ConfigurationState configurationState) {
    return new CustomValidatorFactory( configurationState );
  }
}
