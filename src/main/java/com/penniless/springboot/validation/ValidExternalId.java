package com.penniless.springboot.validation;

import com.penniless.springboot.validation.validator.ExternalIdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = ExternalIdValidator.class)
@Documented
public @interface ValidExternalId {
  String message() default "The external ID provided is invalid. Please check again.";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
