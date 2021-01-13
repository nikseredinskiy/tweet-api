package com.itechart

import com.itechart.core.utils.ConfigUtils
import com.typesafe.config.Config

trait Resource {
  val config: Config = ConfigUtils.conf
}

object SentimentConfigs extends Resource {
  lazy val protocol: String = config.getString("sentiment.protocol")
  lazy val host: String = config.getString("sentiment.host")
  lazy val port: String = config.getString("sentiment.port")
  lazy val toxicityEndpoint: String = config.getString("sentiment.toxicity-endpoint")
}

object TweetApi extends Resource {
  lazy val host: String = config.getString("tweet-api.host")
  lazy val port: String = config.getString("tweet-api.port")
}
