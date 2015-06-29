package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.MySQLDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema = SchemaVersion.schema ++ Todo.schema ++ TodoType.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table SchemaVersion
   *  @param versionRank Database column version_rank SqlType(INT)
   *  @param installedRank Database column installed_rank SqlType(INT)
   *  @param version Database column version SqlType(VARCHAR), PrimaryKey, Length(50,true)
   *  @param description Database column description SqlType(VARCHAR), Length(200,true)
   *  @param `type` Database column type SqlType(VARCHAR), Length(20,true)
   *  @param script Database column script SqlType(VARCHAR), Length(1000,true)
   *  @param checksum Database column checksum SqlType(INT), Default(None)
   *  @param installedBy Database column installed_by SqlType(VARCHAR), Length(100,true)
   *  @param installedOn Database column installed_on SqlType(TIMESTAMP)
   *  @param executionTime Database column execution_time SqlType(INT)
   *  @param success Database column success SqlType(BIT) */
  case class SchemaVersionRow(versionRank: Int, installedRank: Int, version: String, description: String, `type`: String, script: String, checksum: Option[Int] = None, installedBy: String, installedOn: java.sql.Timestamp, executionTime: Int, success: Boolean)
  /** GetResult implicit for fetching SchemaVersionRow objects using plain SQL queries */
  implicit def GetResultSchemaVersionRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[Int]], e3: GR[java.sql.Timestamp], e4: GR[Boolean]): GR[SchemaVersionRow] = GR{
    prs => import prs._
    SchemaVersionRow.tupled((<<[Int], <<[Int], <<[String], <<[String], <<[String], <<[String], <<?[Int], <<[String], <<[java.sql.Timestamp], <<[Int], <<[Boolean]))
  }
  /** Table description of table schema_version. Objects of this class serve as prototypes for rows in queries.
   *  NOTE: The following names collided with Scala keywords and were escaped: type */
  class SchemaVersion(_tableTag: Tag) extends Table[SchemaVersionRow](_tableTag, "schema_version") {
    def * = (versionRank, installedRank, version, description, `type`, script, checksum, installedBy, installedOn, executionTime, success) <> (SchemaVersionRow.tupled, SchemaVersionRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(versionRank), Rep.Some(installedRank), Rep.Some(version), Rep.Some(description), Rep.Some(`type`), Rep.Some(script), checksum, Rep.Some(installedBy), Rep.Some(installedOn), Rep.Some(executionTime), Rep.Some(success)).shaped.<>({r=>import r._; _1.map(_=> SchemaVersionRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7, _8.get, _9.get, _10.get, _11.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column version_rank SqlType(INT) */
    val versionRank: Rep[Int] = column[Int]("version_rank")
    /** Database column installed_rank SqlType(INT) */
    val installedRank: Rep[Int] = column[Int]("installed_rank")
    /** Database column version SqlType(VARCHAR), PrimaryKey, Length(50,true) */
    val version: Rep[String] = column[String]("version", O.PrimaryKey, O.Length(50,varying=true))
    /** Database column description SqlType(VARCHAR), Length(200,true) */
    val description: Rep[String] = column[String]("description", O.Length(200,varying=true))
    /** Database column type SqlType(VARCHAR), Length(20,true)
     *  NOTE: The name was escaped because it collided with a Scala keyword. */
    val `type`: Rep[String] = column[String]("type", O.Length(20,varying=true))
    /** Database column script SqlType(VARCHAR), Length(1000,true) */
    val script: Rep[String] = column[String]("script", O.Length(1000,varying=true))
    /** Database column checksum SqlType(INT), Default(None) */
    val checksum: Rep[Option[Int]] = column[Option[Int]]("checksum", O.Default(None))
    /** Database column installed_by SqlType(VARCHAR), Length(100,true) */
    val installedBy: Rep[String] = column[String]("installed_by", O.Length(100,varying=true))
    /** Database column installed_on SqlType(TIMESTAMP) */
    val installedOn: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("installed_on")
    /** Database column execution_time SqlType(INT) */
    val executionTime: Rep[Int] = column[Int]("execution_time")
    /** Database column success SqlType(BIT) */
    val success: Rep[Boolean] = column[Boolean]("success")

    /** Index over (installedRank) (database name schema_version_ir_idx) */
    val index1 = index("schema_version_ir_idx", installedRank)
    /** Index over (success) (database name schema_version_s_idx) */
    val index2 = index("schema_version_s_idx", success)
    /** Index over (versionRank) (database name schema_version_vr_idx) */
    val index3 = index("schema_version_vr_idx", versionRank)
  }
  /** Collection-like TableQuery object for table SchemaVersion */
  lazy val SchemaVersion = new TableQuery(tag => new SchemaVersion(tag))

  /** Entity class storing rows of table Todo
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param todoTypeId Database column todo_type_id SqlType(INT), Default(None)
   *  @param content Database column content SqlType(VARCHAR), Length(255,true)
   *  @param insDatetime Database column ins_datetime SqlType(DATETIME)
   *  @param updDatetime Database column upd_datetime SqlType(DATETIME) */
  case class TodoRow(id: Int, todoTypeId: Option[Int] = None, content: String, insDatetime: java.sql.Timestamp, updDatetime: java.sql.Timestamp)
  /** GetResult implicit for fetching TodoRow objects using plain SQL queries */
  implicit def GetResultTodoRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[String], e3: GR[java.sql.Timestamp]): GR[TodoRow] = GR{
    prs => import prs._
    TodoRow.tupled((<<[Int], <<?[Int], <<[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table todo. Objects of this class serve as prototypes for rows in queries. */
  class Todo(_tableTag: Tag) extends Table[TodoRow](_tableTag, "todo") {
    def * = (id, todoTypeId, content, insDatetime, updDatetime) <> (TodoRow.tupled, TodoRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), todoTypeId, Rep.Some(content), Rep.Some(insDatetime), Rep.Some(updDatetime)).shaped.<>({r=>import r._; _1.map(_=> TodoRow.tupled((_1.get, _2, _3.get, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column todo_type_id SqlType(INT), Default(None) */
    val todoTypeId: Rep[Option[Int]] = column[Option[Int]]("todo_type_id", O.Default(None))
    /** Database column content SqlType(VARCHAR), Length(255,true) */
    val content: Rep[String] = column[String]("content", O.Length(255,varying=true))
    /** Database column ins_datetime SqlType(DATETIME) */
    val insDatetime: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("ins_datetime")
    /** Database column upd_datetime SqlType(DATETIME) */
    val updDatetime: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("upd_datetime")
  }
  /** Collection-like TableQuery object for table Todo */
  lazy val Todo = new TableQuery(tag => new Todo(tag))

  /** Entity class storing rows of table TodoType
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param title Database column title SqlType(VARCHAR), Length(255,true)
   *  @param insDatetime Database column ins_datetime SqlType(DATETIME)
   *  @param updDatetime Database column upd_datetime SqlType(DATETIME) */
  case class TodoTypeRow(id: Int, title: String, insDatetime: java.sql.Timestamp, updDatetime: java.sql.Timestamp)
  /** GetResult implicit for fetching TodoTypeRow objects using plain SQL queries */
  implicit def GetResultTodoTypeRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp]): GR[TodoTypeRow] = GR{
    prs => import prs._
    TodoTypeRow.tupled((<<[Int], <<[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table todo_type. Objects of this class serve as prototypes for rows in queries. */
  class TodoType(_tableTag: Tag) extends Table[TodoTypeRow](_tableTag, "todo_type") {
    def * = (id, title, insDatetime, updDatetime) <> (TodoTypeRow.tupled, TodoTypeRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(title), Rep.Some(insDatetime), Rep.Some(updDatetime)).shaped.<>({r=>import r._; _1.map(_=> TodoTypeRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column title SqlType(VARCHAR), Length(255,true) */
    val title: Rep[String] = column[String]("title", O.Length(255,varying=true))
    /** Database column ins_datetime SqlType(DATETIME) */
    val insDatetime: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("ins_datetime")
    /** Database column upd_datetime SqlType(DATETIME) */
    val updDatetime: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("upd_datetime")
  }
  /** Collection-like TableQuery object for table TodoType */
  lazy val TodoType = new TableQuery(tag => new TodoType(tag))
}
