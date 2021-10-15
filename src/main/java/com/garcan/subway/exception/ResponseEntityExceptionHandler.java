package com.garcan.subway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ResponseEntityExceptionHandler {

  @ExceptionHandler(BaseExceptionAbs.class)
  public final ResponseEntity<Response> handleNotFoundException(final BaseExceptionAbs ex) {
    log.error("Exception:", ex);
    return new ResponseEntity<>(new Response(ex), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Clase POJO usada por @see
   * com.garcan.exception.ResponseEntityExceptionHandler.handleNotFoundException(BaseExceptionAbs).
   * <br>
   * Anidada para no definirla en otro archivo ni hacerla pública. <br>
   * Privada para no ser usada fuera de esta clase. <br>
   * Los métodos getter y setter deben existir y ser públicos para que @ExceptionHandler funcione
   * correctamente.
   *
   */
  private class Response {

    private String message;
    private String stack;

    public Response(final BaseExceptionAbs cause) {
      this.message = cause.getMessage();
      this.stack = cause.toString();
    }

    @SuppressWarnings("unused")
    public final String getMessage() {
      return this.message;
    }

    @SuppressWarnings("unused")
    public final void setMessage(final String message) {
      this.message = message;
    }

    @SuppressWarnings("unused")
    public final String getStack() {
      return this.stack;
    }

    @SuppressWarnings("unused")
    public final void setStack(final String stack) {
      this.stack = stack;
    }

  }
}
