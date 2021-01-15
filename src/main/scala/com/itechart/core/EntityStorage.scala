package com.itechart.core

import com.itechart.core.services.{HttpClient, TweetApiService}
import com.softwaremill.macwire._

object EntityStorage {

  val httpSender: HttpClient = wire[HttpClient]
  val tweetApiService: TweetApiService = wire[TweetApiService]

}
