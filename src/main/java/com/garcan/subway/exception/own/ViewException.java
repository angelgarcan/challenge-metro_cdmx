package com.garcan.subway.exception.own;

import com.garcan.subway.exception.BaseExceptionAbs;

public class ViewException extends BaseExceptionAbs {

  private static final long serialVersionUID = 1604461614259148255L;

  public ViewException(final String message, final Throwable cause, final int errorCode) {
    super(message, cause, errorCode);
  }

  public ViewException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public ViewException(final String message) {
    super(message);
  }

  public ViewException(final Throwable cause) {
    super(cause);
  }

}
