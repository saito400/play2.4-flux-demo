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
import models.Todo

class TodoController extends Controller with HasDatabaseConfig[JdbcProfile]{

  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  import driver.api._

  val todos = TableQuery[models.Tables.Todo]
  val todoTypes = TableQuery[models.Tables.TodoType]

  def index = Action.async {
    Future(Ok(views.html.todo.index()))
  }

  def list = Action.async {
    val todoList = for {
      (t, tt) <- todos joinLeft todoTypes on (_.todoTypeId === _.id)
    } yield (t.id, tt.map(_.id), t.content)
    db.run(todoList.result).map(res => Ok(views.html.todo.list(res.toList)))
  }

  val todoForm = Form(
    mapping(
      "todoTypeId" -> optional(number),
      "content" -> text()
    )(Todo.apply)(Todo.unapply)
  )

  def insert = Action.async { implicit rs =>
    val todo = todoForm.bindFromRequest.get
    db.run(todos += TodoRow(0, todo.todoTypeId, todo.content, new java.sql.Timestamp(System.currentTimeMillis), new java.sql.Timestamp(System.currentTimeMillis))).map(_ => Redirect(routes.TodoController.list))
  }

}
