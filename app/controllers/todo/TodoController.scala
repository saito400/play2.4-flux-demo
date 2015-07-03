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
import service.todo.TodoService
import javax.inject.Inject

class TodoController @Inject() (service: TodoService) extends Controller {

  def index = Action.async {
    Future(Ok(views.html.todo.index()))
  }

  def list = Action.async {
    service.list.map { x => 
      Ok(views.html.todo.list(x.toList))
    }
  }

  val todoForm = Form(
    mapping(
      "todoTypeId" -> optional(number),
      "content" -> text()
    )(Todo.apply)(Todo.unapply)
  )

  def insert = Action.async { implicit rs =>
    val todo = todoForm.bindFromRequest.get
    service.insert(TodoRow(0, todo.todoTypeId, todo.content, new java.sql.Timestamp(System.currentTimeMillis), new java.sql.Timestamp(System.currentTimeMillis))).map(_ => Redirect(routes.TodoController.list))
  }
}
