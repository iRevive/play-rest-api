package persistence.dao

import java.sql.Timestamp
import java.time.{Clock, Instant}

import javax.inject.{Inject, Singleton}
import persistence.mapping.{Blog, BlogDto}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.SQLServerProfile

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author Maksim Ochenashko
  */
@Singleton
class BlogDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends HasDatabaseConfigProvider[SQLServerProfile] {

  import profile.api._
  import persistence.mapping.Tables._

  def list(offset: Int, limit: Int): Future[Seq[Blog]] = {
    val query = for {
      master <- TBlogMaster.drop(offset).take(limit)
    } yield master

    for {
      blogs <- db.run(query.result)
      result <- Future.sequence(blogs.map(loadDetails))
    } yield result
  }

  def get(id: Int): Future[Option[Blog]] = {
    val query = for {
      master <- TBlogMaster if master.blogId === id
    } yield master

    for {
      master <- db.run(query.result.headOption)

      result <- master match {
        case Some(m) =>
          for {
            result <- loadDetails(m)
          } yield Some(result)

        case None =>
          Future.successful(Option.empty[Blog])
      }

    } yield result
  }


  def create(dto: BlogDto): Future[Int] = {
    val now = Timestamp.from(Instant.now(Clock.systemUTC()))

    val masterQuery = TBlogMaster
      .returning(TBlogMaster.map(_.blogId))
      .into { case (_, id) => id }

    val masterRecord = TBlogMasterRow(0, dto.userId, dto.header, Some(now))

    for {
      masterId <- db.run(masterQuery += masterRecord)

      detailQuery = TBlogDetail += TBlogDetailRow(0, dto.userId, masterId, dto.blogContent, Some(now))

      _ <- db.run(detailQuery)
    } yield masterId
  }

  def update(id: Int, dto: BlogDto): Future[Int] = {
    val query = for {
      master <- TBlogMaster if master.blogId === id
    } yield master.blogHdr

    db.run(query.update(dto.header))
  }

  def delete(id: Int): Future[Unit] = {
    val childrenQuery = for {
      detail <- TBlogDetail if detail.blogId === id
    } yield detail

    val masterQuery = for {
      master <- TBlogMaster if master.blogId === id
    } yield master

    db.run(DBIO.seq(childrenQuery.delete, masterQuery.delete))
  }

  def loadDetails(master: TBlogMasterRow): Future[Blog] = {
    val q = for {
      detail <- TBlogDetail if detail.blogId === master.blogId
    } yield detail

    for {
      details <- db.run(q.result)
    } yield Blog.create(master, details)
  }

}