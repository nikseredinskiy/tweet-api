package com.itechart.core.controllers

import akka.http.scaladsl.server.{Directives, Route}
import com.itechart.core.impl.TweetApiImpl
import com.itechart.core.EntityStorage
import com.itechart.core.JsonSupport._

final case class CheckTweetRequest(content: String)

final case class CheckTweetResponse(
                                     toxicity: Boolean,
                                     threat: Boolean,
                                     sexualExplicit: Boolean,
                                     severeToxicity: Boolean,
                                     obscene: Boolean,
                                     insult: Boolean,
                                     identityAttack: Boolean
                                   )

final case class SentimentAnalysisRequest(sentences: Seq[String], threshold: Double)

final case class SentimentCheckResult(`match`: Option[Boolean], probability0: Float, probability1: Float)

final case class Sentiment(
                            identityAttack: SentimentCheckResult,
                            insult: SentimentCheckResult,
                            obscene: SentimentCheckResult,
                            severeToxicity: SentimentCheckResult,
                            sexualExplicit: SentimentCheckResult,
                            threat: SentimentCheckResult,
                            toxicity: SentimentCheckResult
                          )

final case class SentimentAnalysisResponse(label: String, sentiment: Sentiment)

object TweetApiController extends Directives {

  val impl: TweetApiImpl = EntityStorage.tweetApiImpl

  def checkTweet: Route = {
    pathPrefix("analysis") {
      pathEnd {
        post {
          entity(as[String]) {
            body =>
              complete(impl.checkTweet(body))
          }
        }
      }
    }
  }


  val routes: Route = pathPrefix("api") {
    checkTweet
  }

}
