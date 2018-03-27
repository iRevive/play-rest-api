package controllers.api

import play.api.libs.json.{Json, Writes}

/**
  * Representation of a list response.
  *
  * @author Maksim Ochenashko
  */
case class ListResponse[A](values: Seq[A], offset: Int, limit: Int)

object ListResponse {

  implicit def writes[A: Writes]: Writes[ListResponse[A]] = Json.writes[ListResponse[A]]

}