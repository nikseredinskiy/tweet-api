package com.itechart.core.impl

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpMethod, HttpRequest, HttpResponse, StatusCodes}
import akka.http.scaladsl.unmarshalling.Unmarshal
import com.itechart.SentimentConfigs
import com.itechart.Server.system
import com.itechart.core.exceptions.CustomExceptions.ServerErrorException
import org.slf4j.LoggerFactory
import spray.json._

import scala.concurrent.{ExecutionContextExecutor, Future}

class HttpRequestSenderImpl {

  implicit val ec: ExecutionContextExecutor = scala.concurrent.ExecutionContext.global

  private val logger = LoggerFactory.getLogger(classOf[HttpRequestSenderImpl])

  def requestToResponse[T](request: HttpRequest)(
    implicit jsonFormat: RootJsonFormat[T]
  ): Future[T] = {
    for {
      response <- sendSingleRequest(request)
      responseStr <- Unmarshal(response).to[String]
    } yield responseStr.parseJson.convertTo[T]

    sendSingleRequest(request).flatMap(response => {
      response.status match {
        case status if status == StatusCodes.OK || status == StatusCodes.Created =>
          Unmarshal(response).to[String].map(_.parseJson.convertTo[T])
        case _ =>
          val errorMessage = s"Http request with url ${request.uri} failed to send with status code ${response.status}"
          logger.error(errorMessage)
          throw new ServerErrorException(errorMessage)
      }
    })
  }

  def sendSingleRequest(request: HttpRequest): Future[HttpResponse] = {
    Http().singleRequest(request)
  }

  def generateSentimentAnalysisRequest[T](method: HttpMethod, endpoint: String, body: T)
                                         (implicit jsonFormat: RootJsonFormat[T]): HttpRequest = {
    HttpRequest(
      method = method,
      uri = s"${SentimentConfigs.protocol}://${SentimentConfigs.host}:${SentimentConfigs.port}/$endpoint",
      entity = HttpEntity(ContentTypes.`application/json`, body.toJson.compactPrint)
    )
  }

}
