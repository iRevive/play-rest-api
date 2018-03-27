import com.google.inject.AbstractModule
import play.api.{Configuration, Environment}
import shared.{AuthConfig, RestConfig}

/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.

 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
class Module(environment: Environment, configuration: Configuration) extends AbstractModule {

  override def configure(): Unit = {
    val maxLimit: Int = configuration.get[Int]("application.rest.max-query-limit")

    val username = configuration.get[String]("application.rest.auth.username")
    val password = configuration.get[String]("application.rest.auth.password")

    val authConfig = AuthConfig(username, password)

    val restConfig = RestConfig(maxLimit, authConfig)

    bind(classOf[RestConfig]).toInstance(restConfig)
  }

}