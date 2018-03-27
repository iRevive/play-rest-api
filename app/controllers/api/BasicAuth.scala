package controllers.api

import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author Maksim Ochenashko
  */
class BasicAuth(username: String, password: String)(implicit val executionContext: ExecutionContext)
  extends ActionFilter[Request] {

  /**
    * Extracts a value from the authorization header. Compares it with a config value.
    */
  def filter[A](request: Request[A]): Future[Option[Result]] = {
    val result = request.headers.get("Authorization") map { authHeader =>
      val (user, pass) = decodeBasicAuth(authHeader)
      if (user == username && pass == password) None else Some(Results.Unauthorized)
    } getOrElse Some(Results.Unauthorized)

    Future.successful(result)
  }

  private[this] def decodeBasicAuth(authHeader: String): (String, String) = {
    val baStr = authHeader.replaceFirst("Basic ", "")
    val decoded = new sun.misc.BASE64Decoder().decodeBuffer(baStr)
    val Array(user, password) = new String(decoded).split(":")
    (user, password)
  }
}
