package controllers.todo

import play.api.Play
import play.api.data.Form
import play.api.data.Forms.mapping
import play.api.data.Forms.text
import play.api.data.Forms.number
import play.api.data.Forms.optional
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfig
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.Action
import play.api.mvc.Controller
import slick.driver.JdbcProfile
import models.Tables
import models.Tables._
import scala.concurrent.Future

class TodoTypeController extends Controller with HasDatabaseConfig[JdbcProfile]{

  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  import driver.api._

  val TodoTypes = TableQuery[models.Tables.TodoType]

  def index = Action.async {
    Future(Ok(views.html.todotype.index()))
  }

  def list = Action.async {
    db.run(TodoTypes.result).map(res => Ok(views.html.todotype.list(res.toList)))
  }

  case class TodoType(title: String)

  val todoTypeForm = Form(
    mapping(
      "title" -> text()
    )(TodoType.apply)(TodoType.unapply)
  )

  def insert = Action.async { implicit rs =>
    val todoType = todoTypeForm.bindFromRequest.get
      db.run(TodoTypes += TodoTypeRow(0, todoType.title, new java.sql.Timestamp(System.currentTimeMillis), new java.sql.Timestamp(System.currentTimeMillis))).map(_ => Redirect(routes.TodoTypeController.list))
  }

}