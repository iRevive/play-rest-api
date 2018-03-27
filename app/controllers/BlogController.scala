package controllers

import java.sql.Timestamp
import java.time.{Clock, Instant}

import controllers.api.RestController
import javax.inject.{Inject, Singleton}
import persistence.dao.{BlogDao, UserDao}
import persistence.mapping.BlogDto
import play.api.mvc._
import shared.RestConfig

import scala.concurrent.ExecutionContext

/**
  * @author Maksim Ochenashko
  */
@Singleton
class BlogController @Inject()(cc: ControllerComponents,
                               restConfig: RestConfig,
                               blogDao: BlogDao,
                               userDao: UserDao)
                              (implicit ec: ExecutionContext) extends RestController(restConfig, cc) {

  /**
    * Returns a list of entities.
    *
    * @param offsetOpt offset for pagination
    * @param limitOpt  limit for pagination
    * @return
    */
  def list(offsetOpt: Option[Int], limitOpt: Option[Int]): Action[AnyContent] = Authorized.async {
    val offset = offsetOpt.getOrElse(0)
    val limit = limitOpt.getOrElse(restConfig.maxLimitValue)

    for {
      users <- blogDao.list(offset, limit)
    } yield list(users, offset, limit)
  }

  /**
    * Returns blog by id if entity exists in a database, otherwise returns not found response.
    */
  def get(id: Int): Action[AnyContent] = Authorized.async {
    for {
      user <- blogDao.get(id)
    } yield resultOrNotFound(user, id)
  }

  /**
    * Creates a blog detail, also updates user's `last blog dt`.
    */
  def create(): Action[BlogDto] = Authorized(parse.json[BlogDto]).async { request =>
    for {
      id <- blogDao.create(request.body)
      _ <- userDao.updateLastBlogDt(request.body.userId, Timestamp.from(Instant.now(Clock.systemUTC())))
    } yield created(id)
  }

  /**
    * Updates a blog.
    */
  def update(id: Int): Action[BlogDto] = Authorized(parse.json[BlogDto]).async { request =>
    for {
      _ <- blogDao.update(id, request.body)
    } yield success()
  }

  /**
    * Deletes record by id.
    */
  def delete(id: Int): Action[AnyContent] = Authorized.async {
    for {
      _ <- blogDao.delete(id)
    } yield success()
  }

}