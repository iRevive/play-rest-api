package controllers

import java.sql.Timestamp
import java.time.{Clock, Instant}

import controllers.api.RestController
import javax.inject.{Inject, Singleton}
import persistence.dao.{BlogDetailDao, UserDao}
import persistence.mapping.BlogDetailDto
import play.api.mvc._
import shared.RestConfig

import scala.concurrent.ExecutionContext

/**
  * @author Maksim Ochenashko
  */
@Singleton
class BlogDetailController @Inject()(cc: ControllerComponents,
                                     restConfig: RestConfig,
                                     blogDetailDao: BlogDetailDao,
                                     userDao: UserDao)
                                    (implicit ec: ExecutionContext) extends RestController(restConfig, cc) {

  /**
    * Returns a list of entities by blogId.
    *
    * @param offsetOpt offset for pagination
    * @param limitOpt  limit for pagination
    * @return
    */
  def list(blogId: Int, offsetOpt: Option[Int], limitOpt: Option[Int]): Action[AnyContent] = Authorized.async {
    val offset = offsetOpt.getOrElse(0)
    val limit = limitOpt.getOrElse(restConfig.maxLimitValue)

    for {
      users <- blogDetailDao.list(blogId, offset, limit)
    } yield list(users, offset, limit)
  }

  /**
    * Returns blog detail by id and blog id if entity exists in a database, otherwise returns not found response.
    */
  def get(blogId: Int, id: Int): Action[AnyContent] = Authorized.async {
    for {
      user <- blogDetailDao.get(blogId, id)
    } yield resultOrNotFound(user, id)
  }

  /**
    * Creates a blog detail, also updates user's `last blog dt`.
    */
  def create(blogId: Int): Action[BlogDetailDto] = Authorized(parse.json[BlogDetailDto]).async { request =>
    for {
      id <- blogDetailDao.create(blogId, request.body)
      _ <- userDao.updateLastBlogDt(request.body.userId, Timestamp.from(Instant.now(Clock.systemUTC())))
    } yield created(id)
  }

  /**
    * Updates a blog detail, also updates user's `last blog dt`.
    */
  def update(blogId: Int, id: Int): Action[BlogDetailDto] = Authorized(parse.json[BlogDetailDto]).async { request =>
    for {
      _ <- blogDetailDao.update(blogId, id, request.body)
      _ <- userDao.updateLastBlogDt(request.body.userId, Timestamp.from(Instant.now(Clock.systemUTC())))
    } yield success()
  }

  /**
    * Deletes record by id.
    */
  def delete(blogId: Int, id: Int): Action[AnyContent] = Authorized.async {
    for {
      _ <- blogDetailDao.delete(blogId, id)
    } yield success()
  }

}