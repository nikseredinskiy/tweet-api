package com.itechart.core

import akka.http.scaladsl.server.Directives.{complete, handleExceptions}
import akka.http.scaladsl.server.{ExceptionHandler, Route}
import com.itechart.core.exceptions.CustomExceptions.ServerErrorException
import com.itechart.core.controllers.TweetApiController
import org.slf4j.LoggerFactory
import com.itechart.core.JsonSupport._

case class HttpErrorResponse(message: String)

object Routes {

  lazy val serviceRoutes: Route = handleExceptions(customHandler) {
    TweetApiController.routes
  }

  val customHandler: ExceptionHandler = {
    val log = LoggerFactory.getLogger("ExceptionHandler")

    ExceptionHandler {
      case e: ServerErrorException =>
        log.error("Request failed with:", e)
        complete(500, HttpErrorResponse(e.getMessage))
    }
  }

}
