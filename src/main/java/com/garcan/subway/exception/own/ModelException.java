package com.garcan.subway.exception.own;

import com.garcan.subway.exception.BaseExceptionAbs;

public class ModelException extends BaseExceptionAbs {

  private static final long serialVersionUID = 1103009435797004716L;

  public ModelException(final String message, final Throwable cause, final int errorCode) {
    super(message, cause, errorCode);
  }

  public ModelException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public ModelException(final String message) {
    super(message);
  }

  public ModelException(final Throwable cause) {
    super(cause);
  }

}
