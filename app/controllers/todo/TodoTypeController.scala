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
import models.TodoType
import service.todo.TodoTypeService
import javax.inject.Inject

class TodoTypeController @Inject() (service: TodoTypeService) extends Controller {

  def index = Action.async {
    Future(Ok(views.html.todotype.index()))
  }

  def list = Action.async {
    service.all.map { x => 
      Ok(views.html.todotype.list(x.toList))
    }
  }

  case class TodoTypeForm(title: String)

  val todoTypeForm = Form(
    mapping(
      "title" -> text()
    )(TodoTypeForm.apply)(TodoTypeForm.unapply)
  )

  def insert = Action.async { implicit rs =>
    val todoType = todoTypeForm.bindFromRequest.get
    service.insert(TodoTypeRow(0, todoType.title, new java.sql.Timestamp(System.currentTimeMillis), new java.sql.Timestamp(System.currentTimeMillis))).map(_ => Redirect(routes.TodoTypeController.list))
  }
}
