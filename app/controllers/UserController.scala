package controllers

import controllers.api.RestController
import javax.inject.{Inject, Singleton}
import persistence.dao.UserDao
import persistence.mapping.User.UserDto
import play.api.mvc._
import shared.RestConfig

import scala.concurrent.ExecutionContext

/**
  * @author Maksim Ochenashko
  */
@Singleton
class UserController @Inject()(cc: ControllerComponents,
                               restConfig: RestConfig,
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
      users <- userDao.list(offset, limit)
    } yield list(users, offset, limit)
  }

  /**
    * Returns user by id if entity exists in a database, otherwise returns not found response.
    */
  def get(id: Int): Action[AnyContent] = Authorized.async {
    for {
      user <- userDao.get(id)
    } yield resultOrNotFound(user, id)
  }

  /**
    * Creates a user.
    */
  def create(): Action[UserDto] = Authorized(parse.json[UserDto]).async { request =>
    for {
      id <- userDao.create(request.body)
    } yield created(id)
  }

  /**
    * Updates a user.
    */
  def update(id: Int): Action[UserDto] = Authorized(parse.json[UserDto]).async { request =>
    for {
      _ <- userDao.update(id, request.body)
    } yield success()
  }

  /**
    * Deletes record by id.
    */
  def delete(id: Int): Action[AnyContent] = Authorized.async {
    for {
      _ <- userDao.delete(id)
    } yield success()
  }

}