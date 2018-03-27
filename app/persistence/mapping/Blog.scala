package persistence.mapping

import java.sql.Timestamp

import persistence.mapping.Tables.{TBlogDetail, TBlogDetailRow, TBlogMasterRow}
import play.api.libs.json.{Json, OWrites, Reads}
import slick.lifted.MappedProjection

/**
  * Model projection which is used by get/list API actions
  *
  * @author Maksim Ochenashko
  */
case class Blog(id: Int, userId: Int, header: String, createDt: Option[java.sql.Timestamp], details: Seq[BlogDetail])

object Blog {

  def create(master: TBlogMasterRow, details: Seq[TBlogDetailRow]): Blog = {
    Blog(master.blogId, master.usrId, master.blogHdr, master.createDt, details.map(BlogDetail.create))
  }

  implicit val writes: OWrites[Blog] = Json.writes[Blog]

}

case class BlogDetail(id: Int, userId: Int, blogId: Int, blogContent: Option[String], createDt: Option[Timestamp])

object BlogDetail {

  def projection(d: TBlogDetail): MappedProjection[BlogDetail, (Int, Int, Int, Option[String], Option[Timestamp])] = {
    import Tables.profile.api._

    (d.blogDetailId, d.usrId, d.blogId, d.blogContent, d.createDt) <> ((BlogDetail.apply _).tupled, BlogDetail.unapply)
  }

  def create(detail: TBlogDetailRow): BlogDetail = {
    BlogDetail(detail.blogDetailId, detail.usrId, detail.blogId, detail.blogContent, detail.createDt)
  }

  implicit val writes: OWrites[BlogDetail] = Json.writes[BlogDetail]

}

/**
  * Model projection which is used by post/put API actions
  */
case class BlogDto(userId: Int, header: String, blogContent: Option[String])

object BlogDto {

  implicit val reads: Reads[BlogDto] = Json.reads[BlogDto]

}

case class BlogDetailDto(userId: Int, blogContent: Option[String])

object BlogDetailDto {

  implicit val reads: Reads[BlogDetailDto] = Json.reads[BlogDetailDto]

}