package persistence.mapping
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.SQLServerProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = TBlogDetail.schema ++ TBlogMaster.schema ++ TUsr.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table TBlogDetail
   *  @param blogDetailId Database column blog_detail_id SqlType(int identity), PrimaryKey
   *  @param usrId Database column usr_id SqlType(int)
   *  @param blogId Database column blog_id SqlType(int)
   *  @param blogContent Database column blog_content SqlType(varchar), Length(500,true)
   *  @param createDt Database column create_dt SqlType(datetime) */
  case class TBlogDetailRow(blogDetailId: Int, usrId: Int, blogId: Int, blogContent: Option[String], createDt: Option[java.sql.Timestamp])
  /** GetResult implicit for fetching TBlogDetailRow objects using plain SQL queries */
  implicit def GetResultTBlogDetailRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[java.sql.Timestamp]]): GR[TBlogDetailRow] = GR{
    prs => import prs._
    TBlogDetailRow.tupled((<<[Int], <<[Int], <<[Int], <<?[String], <<?[java.sql.Timestamp]))
  }
  /** Table description of table t_blog_detail. Objects of this class serve as prototypes for rows in queries. */
  class TBlogDetail(_tableTag: Tag) extends profile.api.Table[TBlogDetailRow](_tableTag, Some("dbo"), "t_blog_detail") {
    def * = (blogDetailId, usrId, blogId, blogContent, createDt) <> (TBlogDetailRow.tupled, TBlogDetailRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(blogDetailId), Rep.Some(usrId), Rep.Some(blogId), blogContent, createDt).shaped.<>({r=>import r._; _1.map(_=> TBlogDetailRow.tupled((_1.get, _2.get, _3.get, _4, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column blog_detail_id SqlType(int identity), PrimaryKey */
    val blogDetailId: Rep[Int] = column[Int]("blog_detail_id", O.PrimaryKey, O.AutoInc)
    /** Database column usr_id SqlType(int) */
    val usrId: Rep[Int] = column[Int]("usr_id")
    /** Database column blog_id SqlType(int) */
    val blogId: Rep[Int] = column[Int]("blog_id")
    /** Database column blog_content SqlType(varchar), Length(500,true) */
    val blogContent: Rep[Option[String]] = column[Option[String]]("blog_content", O.Length(500,varying=true))
    /** Database column create_dt SqlType(datetime) */
    val createDt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("create_dt")
  }
  /** Collection-like TableQuery object for table TBlogDetail */
  lazy val TBlogDetail = new TableQuery(tag => new TBlogDetail(tag))

  /** Entity class storing rows of table TBlogMaster
   *  @param blogId Database column blog_id SqlType(int identity), PrimaryKey
   *  @param usrId Database column usr_id SqlType(int)
   *  @param blogHdr Database column blog_hdr SqlType(varchar), Length(125,true)
   *  @param createDt Database column create_dt SqlType(datetime) */
  case class TBlogMasterRow(blogId: Int, usrId: Int, blogHdr: String, createDt: Option[java.sql.Timestamp])
  /** GetResult implicit for fetching TBlogMasterRow objects using plain SQL queries */
  implicit def GetResultTBlogMasterRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[java.sql.Timestamp]]): GR[TBlogMasterRow] = GR{
    prs => import prs._
    TBlogMasterRow.tupled((<<[Int], <<[Int], <<[String], <<?[java.sql.Timestamp]))
  }
  /** Table description of table t_blog_master. Objects of this class serve as prototypes for rows in queries. */
  class TBlogMaster(_tableTag: Tag) extends profile.api.Table[TBlogMasterRow](_tableTag, Some("dbo"), "t_blog_master") {
    def * = (blogId, usrId, blogHdr, createDt) <> (TBlogMasterRow.tupled, TBlogMasterRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(blogId), Rep.Some(usrId), Rep.Some(blogHdr), createDt).shaped.<>({r=>import r._; _1.map(_=> TBlogMasterRow.tupled((_1.get, _2.get, _3.get, _4)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column blog_id SqlType(int identity), PrimaryKey */
    val blogId: Rep[Int] = column[Int]("blog_id", O.PrimaryKey, O.AutoInc)
    /** Database column usr_id SqlType(int) */
    val usrId: Rep[Int] = column[Int]("usr_id")
    /** Database column blog_hdr SqlType(varchar), Length(125,true) */
    val blogHdr: Rep[String] = column[String]("blog_hdr", O.Length(125,varying=true))
    /** Database column create_dt SqlType(datetime) */
    val createDt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("create_dt")
  }
  /** Collection-like TableQuery object for table TBlogMaster */
  lazy val TBlogMaster = new TableQuery(tag => new TBlogMaster(tag))

  /** Entity class storing rows of table TUsr
   *  @param usrId Database column usr_id SqlType(int identity), PrimaryKey
   *  @param usrFname Database column usr_fname SqlType(varchar), Length(25,true)
   *  @param usrLname Database column usr_lname SqlType(varchar), Length(25,true)
   *  @param lastBlogDt Database column last_blog_dt SqlType(datetime) */
  case class TUsrRow(usrId: Int, usrFname: String, usrLname: Option[String], lastBlogDt: Option[java.sql.Timestamp])
  /** GetResult implicit for fetching TUsrRow objects using plain SQL queries */
  implicit def GetResultTUsrRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]], e3: GR[Option[java.sql.Timestamp]]): GR[TUsrRow] = GR{
    prs => import prs._
    TUsrRow.tupled((<<[Int], <<[String], <<?[String], <<?[java.sql.Timestamp]))
  }
  /** Table description of table t_usr. Objects of this class serve as prototypes for rows in queries. */
  class TUsr(_tableTag: Tag) extends profile.api.Table[TUsrRow](_tableTag, Some("dbo"), "t_usr") {
    def * = (usrId, usrFname, usrLname, lastBlogDt) <> (TUsrRow.tupled, TUsrRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(usrId), Rep.Some(usrFname), usrLname, lastBlogDt).shaped.<>({r=>import r._; _1.map(_=> TUsrRow.tupled((_1.get, _2.get, _3, _4)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column usr_id SqlType(int identity), PrimaryKey */
    val usrId: Rep[Int] = column[Int]("usr_id", O.PrimaryKey, O.AutoInc)
    /** Database column usr_fname SqlType(varchar), Length(25,true) */
    val usrFname: Rep[String] = column[String]("usr_fname", O.Length(25,varying=true))
    /** Database column usr_lname SqlType(varchar), Length(25,true) */
    val usrLname: Rep[Option[String]] = column[Option[String]]("usr_lname", O.Length(25,varying=true))
    /** Database column last_blog_dt SqlType(datetime) */
    val lastBlogDt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("last_blog_dt")
  }
  /** Collection-like TableQuery object for table TUsr */
  lazy val TUsr = new TableQuery(tag => new TUsr(tag))
}
