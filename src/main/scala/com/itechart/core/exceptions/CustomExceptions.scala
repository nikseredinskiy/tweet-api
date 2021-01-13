package com.itechart.core.exceptions

object CustomExceptions {

  class ServerErrorException(message: String, cause: Throwable) extends RuntimeException(message) {
    if (cause != null) initCause(cause)

    def this(message: String) = this(message, null)
  }

}
