package shared

/**
  * @author Maksim Ochenashko
  */
case class AppConfig(restConfig: RestConfig)

case class RestConfig(maxLimitValue: Int, authConfig: AuthConfig)

case class AuthConfig(username: String, password: String)