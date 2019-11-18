package com.penniless.springboot.exception;

public enum ExceptionType {
  ENTITY_NOT_FOUND("not.found"),
  ENTITY_NOT_FOUND_2("not.found.2"),
  DUPLICATE_ENTITY("duplicate"),
  ENTITY_EXCEPTION("exception"),
  UNAUTHORIZED("unauthorized");

  String value;

  ExceptionType(String value) {
    this.value = value;
  }

  String getValue() {
    return this.value;
  }
}
