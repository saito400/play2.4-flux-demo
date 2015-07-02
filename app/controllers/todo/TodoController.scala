package controllers.todo

import play.api.Play
import play.api.data.Form
import play.api.data.Forms.mapping
import play.api.data.Forms.text
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfig
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.Action
import play.api.mvc.Controller
import slick.driver.JdbcProfile
import models.Tables
import models.Tables._
import scala.concurrent.Future

//class TodoController extends Controller with Tables with HasDatabaseConfig[JdbcProfile]{
class TodoController extends Controller with HasDatabaseConfig[JdbcProfile]{

  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  import driver.api._

//  case class Todo(id: Int, todoTypeId: Option[Int] = None, content: String)

  val Todos = TableQuery[models.Tables.Todo]

  def index = Action.async {
    Future(Ok(views.html.todo.index()))
  }

  def list = Action.async {
    db.run(Todos.result).map(res => Ok(views.html.todo.list(res.toList)))
  }

}
