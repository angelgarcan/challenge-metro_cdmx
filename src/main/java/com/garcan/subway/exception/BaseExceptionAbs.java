package com.garcan.subway.exception;

/**
 * Clase usada para todas las excepciones generadas.<br>
 * Abstracta para no ser instanciada directamente y para definir implementaciones de métodos que
 * serán comunes a todas las excepciones. <br>
 *
 */
public abstract class BaseExceptionAbs extends RuntimeException {

  private static final long serialVersionUID = -1302397205638799956L;
  /*
   * No vuelva a definir las variables "message" o "cause", utilice las variables heredadas con
   * super()
   */

  /**
   * Evitar el uso del constructor predeterminado obliga a que se implemente uno de los otros
   * constructores.
   */
  @SuppressWarnings("unused")
  private BaseExceptionAbs() {}

  /**
   * Constructor base.
   *
   * @param message Mensaje con la descripción del error.
   * @param cause Objeto que contiene la excepción.
   * @param errorCode Código del error.
   */
  protected BaseExceptionAbs(final String message, final Throwable cause, final int errorCode) {
    super(message, cause);
  }

  /**
   * Constructor base.
   *
   * @param message Mensaje con la descripción del error.
   * @param cause Objeto que contiene la excepción.
   */
  protected BaseExceptionAbs(final String message, final Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructor base.
   *
   * @param message Mensaje con la descripción del error.
   */
  protected BaseExceptionAbs(final String message) {
    super(message);
  }

  /**
   * Constructor base.
   *
   * @param cause Objeto que contiene la excepción.
   */
  protected BaseExceptionAbs(final Throwable cause) {
    super(cause);
  }

  @Override
  public String toString() {
    return new StringBuilder()
        .append(this.getClass().getSimpleName() /* Nombre correcto de la clase de objeto */)
        .append(" [message=").append(getMessage())
        .append(", cause=").append(getCause()).append("]").toString();
  }
}
