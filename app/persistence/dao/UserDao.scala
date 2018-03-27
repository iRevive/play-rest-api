package persistence.dao

import java.sql.Timestamp

import javax.inject.{Inject, Singleton}
import persistence.mapping.User
import persistence.mapping.User.UserDto
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.SQLServerProfile

import scala.concurrent.Future

/**
  * @author Maksim Ochenashko
  */
@Singleton
class UserDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
  extends HasDatabaseConfigProvider[SQLServerProfile] {

  import profile.api._
  import persistence.mapping.Tables._

  def list(offset: Int, limit: Int): Future[Seq[User]] = {
    val query = for {
      u <- TUsr
    } yield User.projection(u)

    db.run(query.drop(offset).take(limit).result)
  }

  def get(id: Int): Future[Option[User]] = {
    val query = for {
      u <- TUsr if u.usrId === id
    } yield User.projection(u)

    db.run(query.result.headOption)
  }

  def create(user: UserDto): Future[Int] = {
    val query = TUsr
      .returning(TUsr.map(_.usrId))
      .into { case (_, id) => id }

    val record = TUsrRow(0, user.firstName, user.lastName, None)

    db.run(query += record)
  }

  def update(id: Int, user: UserDto): Future[Int] = {
    val query = for {
      u <- TUsr if u.usrId === id
    } yield (u.usrFname, u.usrLname)

    db.run(query.update((user.firstName, user.lastName)))
  }

  def delete(id: Int): Future[Int] = {
    val query = for {
      u <- TUsr if u.usrId === id
    } yield u

    db.run(query.delete)
  }

  def updateLastBlogDt(userId: Int, dt: Timestamp): Future[Int] = {
    val query = for {
      u <- TUsr if u.usrId === userId
    } yield u.lastBlogDt

    db.run(query.update(Some(dt)))
  }

}