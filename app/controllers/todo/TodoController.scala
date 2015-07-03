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
import service.todo.{TodoService, TodoTypeService}
import javax.inject.Inject

class TodoController @Inject() (todoService: TodoService, todoTypeService: TodoTypeService) extends Controller {

  def index = Action.async {
    Future(Ok(views.html.todo.index()))
  }

  def list = Action.async {
    val r = for {
     res1 <- todoService.list
     res2 <- todoTypeService.all
    } yield {
      (res1, res2)
    }
    r.map(x => Ok(views.html.todo.list(x)))
  }

  case class TodoForm(todoTypeId: Option[Int] = None, content: String)

  val todoForm = Form(
    mapping(
      "todoTypeId" -> optional(number),
      "content" -> text()
    )(TodoForm.apply)(TodoForm.unapply)
  )

  def insert = Action.async { implicit rs =>
    val todo = todoForm.bindFromRequest.get
    todoService.insert(TodoRow(0, todo.todoTypeId, todo.content, new java.sql.Timestamp(System.currentTimeMillis), new java.sql.Timestamp(System.currentTimeMillis))).map(_ => Redirect(routes.TodoController.list))
  }
}
