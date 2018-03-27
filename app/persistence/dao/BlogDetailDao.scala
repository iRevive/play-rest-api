package persistence.dao

import java.sql.Timestamp
import java.time.{Clock, Instant}

import javax.inject.{Inject, Singleton}
import persistence.mapping.{BlogDetail, BlogDetailDto}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.SQLServerProfile

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author Maksim Ochenashko
  */
@Singleton
class BlogDetailDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends HasDatabaseConfigProvider[SQLServerProfile] {

  import profile.api._
  import persistence.mapping.Tables._

  def list(blogId: Int, offset: Int, limit: Int): Future[Seq[BlogDetail]] = {
    val query = for {
      detail <- TBlogDetail if detail.blogId === blogId
    } yield BlogDetail.projection(detail)

    for {
      records <- db.run(query.drop(offset).take(limit).result)
    } yield records
  }

  def get(blogId: Int, id: Int): Future[Option[BlogDetail]] = {
    val query = for {
      detail <- TBlogDetail if detail.blogId === blogId && detail.blogDetailId === id
    } yield BlogDetail.projection(detail)

    for {
      result <- db.run(query.result.headOption)
    } yield result
  }

  def create(blogId: Int, dto: BlogDetailDto): Future[Int] = {
    val now = Timestamp.from(Instant.now(Clock.systemUTC()))

    val detailQuery = TBlogDetail
      .returning(TBlogDetail.map(_.blogDetailId))
      .into { case (_, id) => id }

    val record = TBlogDetailRow(0, dto.userId, blogId, dto.blogContent, Some(now))

    for {
      detailId <- db.run(detailQuery += record)
    } yield detailId
  }

  def update(blogId: Int, id: Int, dto: BlogDetailDto): Future[Int] = {
    val query = for {
      detail <- TBlogDetail if detail.blogId === blogId && detail.blogDetailId === id
    } yield detail.blogContent

    db.run(query.update(dto.blogContent))
  }

  def delete(blogId: Int, id: Int): Future[Int] = {
    val detailQuery = for {
      detail <- TBlogDetail if detail.blogId === blogId && detail.blogDetailId === id
    } yield detail

    db.run(detailQuery.delete)
  }

}