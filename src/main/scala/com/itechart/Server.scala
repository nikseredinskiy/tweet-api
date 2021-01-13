package com.itechart

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.handleRejections
import akka.http.scaladsl.server.RejectionHandler
import com.itechart.core.Routes
import ch.megard.akka.http.cors.scaladsl.CorsDirectives.cors
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.ExecutionContext
import scala.io.StdIn

object Server extends App {
  private val logger: Logger = LoggerFactory.getLogger(Server.getClass)

  implicit val system: ActorSystem = ActorSystem("tweet-api")
  implicit val executor: ExecutionContext = system.dispatcher

  val routes = cors() {
    handleRejections(RejectionHandler.default) {
      Routes.serviceRoutes
    }
  }

  val bindingFuture = Http().newServerAt(TweetApi.host, TweetApi.port.toInt).bind(routes)

  logger.info(s"Server online at http://${TweetApi.host}:${TweetApi.port}/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done
}
