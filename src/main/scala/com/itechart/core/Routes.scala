package com.itechart.core

import akka.http.scaladsl.server.Directives.{complete, handleExceptions, pathPrefix}
import akka.http.scaladsl.server.{ExceptionHandler, Route}
import com.itechart.core.exceptions.CustomExceptions.ServerErrorException
import org.slf4j.LoggerFactory
import com.itechart.core.JsonSupport._
import com.itechart.core.controllers.TweetApiController

case class HttpErrorResponse(message: String)

object Routes {

  lazy val serviceRoutes: Route =
    handleExceptions(customHandler) {
      pathPrefix("api") {
        TweetApiController.routes
      }
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
