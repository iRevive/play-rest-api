package controllers.api

import play.api.libs.json.{Json, Writes}

/**
  * Meta model for an API response.
  *
  * @author Maksim Ochenashko
  */
case class ApiResponse[A](result: Option[A], error: Option[String], success: Boolean)

object ApiResponse {

  def success[A](result: A): ApiResponse[A] =
    ApiResponse(Some(result), None, success = true)

  def list[A](values: Seq[A], offset: Int, limit: Int): ApiResponse[ListResponse[A]] =
    ApiResponse(Some(ListResponse(values, offset, limit)), None, success = true)

  def error(error: String): ApiResponse[String] =
    ApiResponse(None, Some(error), success = false)

  def error[A](result: A, error: String): ApiResponse[A] =
    ApiResponse(Some(result), Some(error), success = false)

  implicit def writes[A: Writes]: Writes[ApiResponse[A]] = Json.writes[ApiResponse[A]]

}