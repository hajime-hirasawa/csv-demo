package com.example.csvdemo.validator;

import static com.example.csvdemo.util.Functions.as;

import com.example.csvdemo.validator.annotation.AdditionalValidateParam;
import com.example.csvdemo.validator.annotation.FieldName;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import javax.validation.groups.Default;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;

public class ValidatorWrapper implements Validator {

  private final Validator validator;

  public ValidatorWrapper(Validator validator) {
    this.validator =validator;
  }

  /**
   * Validates all constraints on {@code object}.
   *
   * @param object object to validate
   * @param groups the group or list of groups targeted for validation (defaults to {@link
   * Default})
   * @return constraint violations or an empty set if none
   * @throws IllegalArgumentException if object is {@code null} or if {@code null} is passed to the
   * varargs groups
   * @throws ValidationException if a non recoverable error happens during the validation process
   */
  @Override
  public <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {
    Set<ConstraintViolation<T>> error = validator.validate(object, groups);
    return error.stream()
        .map(v -> (ConstraintViolationImpl<T>) v)
        .map(v -> ConstraintViolationImpl.forBeanValidation(
            v.getMessageTemplate(),
            v.getMessageParameters(),
            v.getExpressionVariables(),
            resolveTokens(v),
            v.getRootBeanClass(),
            v.getRootBean(),
            v.getLeafBean(),
            v.getInvalidValue(),
            v.getPropertyPath(),
            v.getConstraintDescriptor(),
            v.getDynamicPayload(object.getClass())
        )).collect(Collectors.toSet());
  }

  /**
   * Validates all constraints placed on the property of {@code object} named {@code propertyName}.
   *
   * @param object object to validate
   * @param propertyName property to validate (i.e. field and getter constraints)
   * @param groups the group or list of groups targeted for validation (defaults to {@link
   * Default})
   * @return constraint violations or an empty set if none
   * @throws IllegalArgumentException if {@code object} is {@code null}, if {@code propertyName} is
   * {@code null}, empty or not a valid object property or if {@code null} is passed to the varargs
   * groups
   * @throws ValidationException if a non recoverable error happens during the validation process
   */
  @Override
  public <T> Set<ConstraintViolation<T>> validateProperty(T object, String propertyName,
      Class<?>... groups) {
    return validator.validateProperty(object, propertyName);
  }

  /**
   * Validates all constraints placed on the property named {@code propertyName} of the class {@code
   * beanType} would the property value be {@code value}.
   * <p>
   * {@link ConstraintViolation} objects return {@code null} for {@link
   * ConstraintViolation#getRootBean()} and {@link ConstraintViolation#getLeafBean()}.
   *
   * @param beanType the bean type
   * @param propertyName property to validate
   * @param value property value to validate
   * @param groups the group or list of groups targeted for validation (defaults to {@link
   * Default}).
   * @return constraint violations or an empty set if none
   * @throws IllegalArgumentException if {@code beanType} is {@code null}, if {@code propertyName}
   * is {@code null}, empty or not a valid object property or if {@code null} is passed to the
   * varargs groups
   * @throws ValidationException if a non recoverable error happens during the validation process
   */
  @Override
  public <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType, String propertyName,
      Object value, Class<?>... groups) {
    return validator.validateValue(beanType, propertyName, value, groups);
  }

  /**
   * Returns the descriptor object describing bean constraints.
   * <p>
   * The returned object (and associated objects including {@link ConstraintDescriptor}s) are
   * immutable.
   *
   * @param clazz class or interface type evaluated
   * @return the bean descriptor for the specified class
   * @throws IllegalArgumentException if clazz is {@code null}
   * @throws ValidationException if a non recoverable error happens during the metadata discovery or
   * if some constraints are invalid.
   */
  @Override
  public BeanDescriptor getConstraintsForClass(Class<?> clazz) {
    return validator.getConstraintsForClass(clazz);
  }

  /**
   * Returns an instance of the specified type allowing access to provider-specific APIs.
   * <p>
   * If the Jakarta Bean Validation provider implementation does not support the specified class,
   * {@link ValidationException} is thrown.
   *
   * @param type the class of the object to be returned
   * @return an instance of the specified class
   * @throws ValidationException if the provider does not support the call
   */
  @Override
  public <T> T unwrap(Class<T> type) {
    return validator.unwrap(type);
  }

  /**
   * Returns the contract for validating parameters and return values of methods and constructors.
   *
   * @return contract for method and constructor validation
   * @since 1.1
   */
  @Override
  public ExecutableValidator forExecutables() {
    return validator.forExecutables();
  }

  private final Map<String, Map<String, String>> cachedParams = new HashMap<>();


  private String resolveTokens(ConstraintViolation<?> violation) {
    String message = violation.getMessage();

    Map<String, String> params = getFieldParams(violation);

    for (Entry<String, String> e : params.entrySet()) {
      message = message.replace("{" + e.getKey() + "}" , e.getValue());
    }
    return message;
  }

  private Map<String, String> getFieldParams(ConstraintViolation<?> violation) {
    String field = String.join(".", violation.getLeafBean().getClass().getName(), getLeafNodeName(violation));
    if (!cachedParams.containsKey(field)) {
      cachedParams.put(field, getFieldAnnotations(violation));
    }
    return cachedParams.get(field);
  }

  private String getLeafNodeName(ConstraintViolation<?> violation) {
    return ((PathImpl)violation.getPropertyPath()).getLeafNode().getName();
  }

  private Map<String, String> getFieldAnnotations(ConstraintViolation<?> violation) {
    Field f = getDeclaredField(violation.getLeafBean().getClass(), getLeafNodeName(violation));

    return Arrays.stream(f.getDeclaredAnnotations())
        .filter(a -> Objects.nonNull(a.annotationType().getAnnotation(AdditionalValidateParam.class)))
        .collect(Collectors.toMap(
            a -> a.annotationType().getAnnotation(AdditionalValidateParam.class).name(),
            a -> as(invokeDeclaredGetter(a, "value"))));
  }

  private Field getDeclaredField(Class<?> clazz, String fieldName) {
    try {
      return clazz.getDeclaredField(fieldName);
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
  }

  private Object invokeDeclaredGetter(Object obj, String getterName) {
    try {
      return obj.getClass().getMethod(getterName).invoke(obj);
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }
}
