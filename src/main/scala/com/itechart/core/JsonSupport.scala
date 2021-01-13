package com.itechart.core

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.itechart.core.controllers.{CheckTweetRequest, CheckTweetResponse, Sentiment, SentimentAnalysisRequest, SentimentAnalysisResponse, SentimentCheckResult}
import spray.json.{CollectionFormats, DefaultJsonProtocol, RootJsonFormat}

object JsonSupport extends SprayJsonSupport with DefaultJsonProtocol with CollectionFormats {
  implicit val bodyFormat: RootJsonFormat[CheckTweetRequest] = jsonFormat1(CheckTweetRequest)
  implicit val responseFormat: RootJsonFormat[CheckTweetResponse] = jsonFormat7(CheckTweetResponse)
  implicit val result: RootJsonFormat[SentimentCheckResult] = jsonFormat3(SentimentCheckResult)
  implicit val sentiment: RootJsonFormat[Sentiment] = jsonFormat7(Sentiment)
  implicit val responseFromHttp: RootJsonFormat[SentimentAnalysisResponse] = jsonFormat2(SentimentAnalysisResponse)
  implicit val requestToHttp: RootJsonFormat[SentimentAnalysisRequest] = jsonFormat2(SentimentAnalysisRequest)

  implicit val httpErrorResponse: RootJsonFormat[HttpErrorResponse] = jsonFormat1(HttpErrorResponse)
}
