package persistence.mapping

import java.sql.Timestamp

import persistence.mapping.Tables.{TUsr, TUsrRow}
import play.api.libs.json.{Json, OWrites, Reads}
import slick.lifted.MappedProjection

/**
  * Model projection which is used by get/list API actions
  *
  * @author Maksim Ochenashko
  */
case class User(id: Int, firstName: String, lastName: Option[String], lastBlogDt: Option[java.sql.Timestamp])

object User {

  def projection(tUsr: TUsr): MappedProjection[User, (Int, String, Option[String], Option[Timestamp])] = {
    import Tables.profile.api._

    (tUsr.usrId, tUsr.usrFname, tUsr.usrLname, tUsr.lastBlogDt) <> ((User.apply _).tupled, User.unapply)
  }

  def create(tUsr: TUsrRow): User = {
    User(tUsr.usrId, tUsr.usrFname, tUsr.usrLname, tUsr.lastBlogDt)
  }

  implicit val writes: OWrites[User] = Json.writes[User]

  /**
    * Model projection which is used by post/put API actions
    */
  case class UserDto(firstName: String, lastName: Option[String])

  implicit val reads: Reads[UserDto] = Json.reads[UserDto]

}