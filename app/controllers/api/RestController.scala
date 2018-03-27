package controllers.api

import play.api.http.{ContentTypeOf, ContentTypes, Writeable}
import play.api.libs.json.Writes
import play.api.mvc._
import shared.RestConfig

/**
  * @author Maksim Ochenashko
  */
abstract class RestController(protected val restConfig: RestConfig, protected val cc: ControllerComponents)
  extends AbstractController(cc) {

  lazy val basicAuth = new BasicAuth(restConfig.authConfig.username, restConfig.authConfig.password)(cc.executionContext)

  /**
    * Authorization action. Authorizes user using credentials from the config.
    */
  lazy val Authorized: ActionBuilder[Request, AnyContent] = Action andThen basicAuth

  def success(): Result = {
    Ok(ApiResponse(Option.empty[String], None, success = true))
  }

  /**
    * Creates a successful response with a list of entities
    */
  def list[A: Writes](values: Seq[A], offset: Int, limit: Int): Result =
    Ok(ApiResponse.list(values, offset, limit))

  def created[A: Writes](value: A): Result = {
    Created(ApiResponse.success(value))
  }

  def badRequest(error: String): Result =
    BadRequest(ApiResponse(Option.empty[String], Some(error), success = false))

  def resultOrNotFound[A: Writes](entity: Option[A], id: Int): Result = {
    entity match {
      case Some(v) =>
        Ok(ApiResponse.success(v))

      case None =>
        NotFound(ApiResponse.error(s"Entity with id [$id] doesn't exist"))
    }
  }

  implicit def jsonWritable[A](implicit writes: Writes[A]): Writeable[A] = {
    implicit val contentType: ContentTypeOf[A] = ContentTypeOf[A](Some(ContentTypes.JSON))
    val transform = Writeable.writeableOf_JsValue.transform compose writes.writes
    Writeable(transform)
  }

}