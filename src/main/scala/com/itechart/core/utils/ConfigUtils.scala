package com.itechart.core.utils

import com.typesafe.config.{Config, ConfigFactory}
import org.slf4j.LoggerFactory

object ConfigUtils {

  private val logger = LoggerFactory.getLogger(ConfigUtils.getClass)

  val CONFIG_ENV = System.getProperty("config")
  val pickConfig: PartialFunction[String, String] = {
    case null => {
      logger.info(s"found no configuration, defaulting to production")
      "production"
    }
    case config => {
      logger.info(s"found configuration: {}", config)
      "local"
    }
  }

  val config = pickConfig(CONFIG_ENV)
  val configFile = s"application.${config}.conf"

  logger.info(s"loading configuration: {}", configFile)

  val regularConfig: Config = ConfigFactory.load()
  val current: Config = ConfigFactory.load(configFile)
  val conf: Config = current.withFallback(regularConfig)
}
