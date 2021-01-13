package com.itechart.core

import com.itechart.core.impl.{HttpRequestSenderImpl, TweetApiImpl}

object EntityStorage {

  val httpSender = new HttpRequestSenderImpl
  val tweetApiImpl = new TweetApiImpl

}
