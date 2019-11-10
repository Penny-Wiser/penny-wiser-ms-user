package com.penniless.springboot.validation.validator;

import com.penniless.springboot.validation.ValidExternalId;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ExternalIdValidator implements ConstraintValidator<ValidExternalId, String> {

  @Override
  public boolean isValid(String extId, ConstraintValidatorContext context) {
    Pattern pattern = Pattern.compile("(UR-)([\\w]+)");
    return pattern.matcher(extId).matches();
  }
}
