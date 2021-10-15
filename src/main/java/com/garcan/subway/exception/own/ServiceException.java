package com.garcan.subway.exception.own;

import com.garcan.subway.exception.BaseExceptionAbs;

public class ServiceException extends BaseExceptionAbs {

  private static final long serialVersionUID = 5867706271027285697L;

  public ServiceException(final String message, final Throwable cause, final int errorCode) {
    super(message, cause, errorCode);
  }

  public ServiceException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public ServiceException(final String message) {
    super(message);
  }

  public ServiceException(final Throwable cause) {
    super(cause);
  }

}
