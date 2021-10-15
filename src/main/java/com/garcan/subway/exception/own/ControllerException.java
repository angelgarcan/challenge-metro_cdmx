package com.garcan.subway.exception.own;

import com.garcan.subway.exception.BaseExceptionAbs;

public class ControllerException extends BaseExceptionAbs {

  private static final long serialVersionUID = 4250399239423478234L;

  public ControllerException(final String message, final Throwable cause, final int errorCode) {
    super(message, cause, errorCode);
  }

  public ControllerException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public ControllerException(final String message) {
    super(message);
  }

  public ControllerException(final Throwable cause) {
    super(cause);
  }

}
