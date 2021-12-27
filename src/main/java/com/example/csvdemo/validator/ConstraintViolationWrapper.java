package com.example.csvdemo.validator;

import java.util.Objects;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.metadata.ConstraintDescriptor;
import lombok.Getter;

public class ConstraintViolationWrapper<T> implements ConstraintViolation<T> {

  @Getter
  private final ConstraintViolation<T> constraintViolation;

  private final String message;

  public ConstraintViolationWrapper(ConstraintViolation<T> constraintViolation) {
    this(constraintViolation, null);
  }
  public ConstraintViolationWrapper(ConstraintViolation<T> constraintViolation, String message) {
    this.constraintViolation = constraintViolation;
    this.message = message;
  }

  /**
   * @return the interpolated error message for this constraint violation
   */
  @Override
  public String getMessage() {
    if (Objects.isNull(message)) {
      return constraintViolation.getMessage();
    }
    return message;
  }

  public String getOriginalMessage() {
    return constraintViolation.getMessage();
  }

  /**
   * @return the non-interpolated error message for this constraint violation
   */
  @Override
  public String getMessageTemplate() {
    return constraintViolation.getMessageTemplate();
  }

  /**
   * Returns the root bean being validated. For method validation, returns the object the method is
   * executed on.
   * <p>
   * Returns {@code null} when:
   * <ul>
   *     <li>the {@code ConstraintViolation} is returned after calling
   *     {@link Validator#validateValue(Class, String, Object, Class[])}</li>
   *     <li>the {@code ConstraintViolation} is returned after validating a
   *     constructor.</li>
   * </ul>
   *
   * @return the validated object, the object hosting the validated element or {@code null}
   */
  @Override
  public T getRootBean() {
    return constraintViolation.getRootBean();
  }

  /**
   * Returns the class of the root bean being validated. For method validation, this is the object
   * class the method is executed on. For constructor validation, this is the class the constructor
   * is declared on.
   *
   * @return the class of the root bean or of the object hosting the validated element
   */
  @Override
  public Class<T> getRootBeanClass() {
    return constraintViolation.getRootBeanClass();
  }

  /**
   * Returns:
   * <ul>
   *     <li>the bean instance the constraint is applied on if it is
   *     a bean constraint</li>
   *     <li>the bean instance hosting the property the constraint
   *     is applied on if it is a property constraint or a container element constraint
   *     hosted on a property</li>
   *     <li>{@code null} when the {@code ConstraintViolation} is returned
   *     after calling {@link Validator#validateValue(Class, String, Object, Class[])}
   *     </li>
   *     <li>the object the method is executed on if it is
   *     a method parameter, cross-parameter or return value constraint or a
   *     container element constraint hosted on a method parameter or return value</li>
   *     <li>{@code null} if it is a constructor parameter or
   *     cross-parameter constraint or a container element constraint hosted on a
   *     constructor parameter</li>
   *     <li>the object the constructor has created if it is a
   *     constructor return value constraint</li>
   * </ul>
   *
   * @return the leaf bean
   */
  @Override
  public Object getLeafBean() {
    return constraintViolation.getLeafBean();
  }

  /**
   * Returns an {@code Object[]} representing the constructor or method invocation arguments if the
   * {@code ConstraintViolation} is returned after validating the method or constructor parameters.
   * Returns {@code null} otherwise.
   *
   * @return parameters of the method or constructor invocation or {@code null}
   * @since 1.1
   */
  @Override
  public Object[] getExecutableParameters() {
    return constraintViolation.getExecutableParameters();
  }

  /**
   * Returns the return value of the constructor or method invocation if the {@code
   * ConstraintViolation} is returned after validating the method or constructor return value.
   * <p>
   * Returns {@code null} if the method has no return value. Returns {@code null} otherwise.
   *
   * @return the method or constructor return value or {@code null}
   * @since 1.1
   */
  @Override
  public Object getExecutableReturnValue() {
    return constraintViolation.getExecutableReturnValue();
  }

  /**
   * @return the property path to the value from {@code rootBean}
   */
  @Override
  public Path getPropertyPath() {
    return constraintViolation.getPropertyPath();
  }

  /**
   * Returns the value failing to pass the constraint. For cross-parameter constraints, an {@code
   * Object[]} representing the method invocation arguments is returned.
   *
   * @return the value failing to pass the constraint
   */
  @Override
  public Object getInvalidValue() {
    return constraintViolation.getInvalidValue();
  }

  /**
   * Returns the constraint metadata reported to fail. The returned instance is immutable.
   *
   * @return constraint metadata
   */
  @Override
  public ConstraintDescriptor<?> getConstraintDescriptor() {
    return constraintViolation.getConstraintDescriptor();
  }

  /**
   * Returns an instance of the specified type allowing access to provider-specific APIs. If the
   * Jakarta Bean Validation provider implementation does not support the specified class, {@link
   * ValidationException} is thrown.
   *
   * @param type the class of the object to be returned
   * @return an instance of the specified class
   * @throws ValidationException if the provider does not support the call
   * @since 1.1
   */
  @Override
  public <U> U unwrap(Class<U> type) {
    return constraintViolation.unwrap(type);
  }
}
