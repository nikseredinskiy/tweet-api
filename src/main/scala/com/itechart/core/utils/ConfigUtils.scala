package com.itechart.core.utils

import com.typesafe.config.{Config, ConfigFactory}
import org.slf4j.LoggerFactory

object ConfigUtils {

  private val logger = LoggerFactory.getLogger(ConfigUtils.getClass)

  val configFile = s"application.conf"

  logger.info(s"loading configuration: {}", configFile)

  val regularConfig: Config = ConfigFactory.load()
  val current: Config = ConfigFactory.load(configFile)
  val conf: Config = current.withFallback(regularConfig)
}
