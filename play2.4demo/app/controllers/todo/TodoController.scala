package controllers.todo

import play.api._
import play.api.mvc._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class TodoController extends Controller {

//  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
//  import driver.api._

  def index = Action.async { implicit request =>
    Future(Ok(views.html.todo.index()))
  }

}
