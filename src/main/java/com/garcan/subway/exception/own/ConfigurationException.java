package com.garcan.subway.exception.own;

import com.garcan.subway.exception.BaseExceptionAbs;

public class ConfigurationException extends BaseExceptionAbs {

  private static final long serialVersionUID = -6575942327530558048L;

  public ConfigurationException(final String message, final Throwable cause, final int errorCode) {
    super(message, cause, errorCode);
  }

  public ConfigurationException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public ConfigurationException(final String message) {
    super(message);
  }

  public ConfigurationException(final Throwable cause) {
    super(cause);
  }

}
