package controllers.todo

import play.api._
import play.api.mvc._

class TodoController extends Controller {

  def index = Action {
    Ok(views.html.todo.index())
  }

}
