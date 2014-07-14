package configuration

import com.typesafe.config.ConfigFactory

object Config {
  val config = ConfigFactory.load()

  val corsAllowOrigin = config.getString("cors.allow.origin")
  val composer = config.getString("composer")

}
