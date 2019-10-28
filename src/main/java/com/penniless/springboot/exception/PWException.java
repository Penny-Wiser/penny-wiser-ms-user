package com.penniless.springboot.exception;

import java.text.MessageFormat;

public class PWException {

  public PWException() {}

  public static RuntimeException throwException(
      EntityType entityType, ExceptionType exceptionType, String... args) {
    String messageTemplate = getMessageTemplate(entityType, exceptionType);
    return throwException(exceptionType, messageTemplate, args);
  }

  private static RuntimeException throwException(
      ExceptionType exceptionType, String messageTemplate, String... args) {
    switch (exceptionType) {
      case ENTITY_NOT_FOUND:
        return new EntityNotFoundException(MessageFormat.format(messageTemplate, args));
      case ENTITY_NOT_FOUND_2:
        return new EntityNotFoundException(MessageFormat.format(messageTemplate, args));
      case DUPLICATE_ENTITY:
        return new DuplicateEntityException(MessageFormat.format(messageTemplate, args));
      default:
        return new RuntimeException(MessageFormat.format(messageTemplate, args));
    }
  }

  private static String getMessageTemplate(EntityType entityType, ExceptionType exceptionType) {
    String identifier =
        String.format("%s.%s", entityType.name().toLowerCase(), exceptionType.getValue());
    switch (identifier) {
      case "user.not.found":
        return "Requested user with id - {0} does not exist.";
      case "user.not.found.2":
        return "Requested user with email - {0} does not exist.";
      case "user.duplicate":
        return "User with email - {0} already exists.";
      default:
        return "Other exceptions";
    }
  }

  public static class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
      super(message);
    }
  }

  public static class DuplicateEntityException extends RuntimeException {
    public DuplicateEntityException(String message) {
      super(message);
    }
  }
}
