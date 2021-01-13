package com.itechart.core.impl

import akka.http.scaladsl.model.HttpMethods
import com.itechart.SentimentConfigs
import com.itechart.core.controllers.{CheckTweetRequest, CheckTweetResponse, SentimentAnalysisRequest, SentimentAnalysisResponse}
import com.itechart.core.JsonSupport._
import com.itechart.core.EntityStorage
import org.slf4j.LoggerFactory
import spray.json._

import scala.concurrent.{ExecutionContextExecutor, Future}

class TweetApiImpl(
                    httpSender: HttpRequestSenderImpl = EntityStorage.httpSender
                  ) {

  implicit val ec: ExecutionContextExecutor = scala.concurrent.ExecutionContext.global

  private val logger = LoggerFactory.getLogger(classOf[TweetApiImpl])

  val defaultThreshold = 0.9

  def checkTweet(jsonContent: String): Future[CheckTweetResponse] = {
    val body = jsonContent.parseJson.convertTo[CheckTweetRequest]
    val requestBody = SentimentAnalysisRequest(Seq(body.content), defaultThreshold)

    logger.info(s"Checking the following tweet: ${body.content}")

    val request = httpSender.generateSentimentAnalysisRequest(HttpMethods.POST, SentimentConfigs.toxicityEndpoint, requestBody)

    logger.info(s"Sending request to SentimentAnalysis service")

    httpSender.requestToResponse[Seq[SentimentAnalysisResponse]](request).map(response => {
      logger.info(s"Fetched response from SentimentAnalysis service")
      val result = response.headOption
      CheckTweetResponse(
        result.flatMap(_.sentiment.toxicity.`match`).getOrElse(false),
        result.flatMap(_.sentiment.threat.`match`).getOrElse(false),
        result.flatMap(_.sentiment.sexualExplicit.`match`).getOrElse(false),
        result.flatMap(_.sentiment.severeToxicity.`match`).getOrElse(false),
        result.flatMap(_.sentiment.obscene.`match`).getOrElse(false),
        result.flatMap(_.sentiment.insult.`match`).getOrElse(false),
        result.flatMap(_.sentiment.identityAttack.`match`).getOrElse(false)
      )
    })

  }

}
