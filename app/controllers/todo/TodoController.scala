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
import play.api.mvc.BodyParsers
import slick.driver.JdbcProfile
import models.Tables
import models.Tables._
import scala.concurrent.Future
import service.todo.{TodoService, TodoTypeService}
import javax.inject.Inject
import play.api.libs.json._
import play.api.libs.functional.syntax._
import java.sql.Timestamp
import service.todo.TodoSearchResult

class TodoController @Inject() (todoService: TodoService, todoTypeService: TodoTypeService) extends Controller {

  implicit val rds: Reads[Timestamp] = (__ \ "time").read[Long].map { long => new Timestamp(long) }
  implicit val wrs: Writes[Timestamp] = (__ \ "time").write[Long].contramap { (a: Timestamp) => a.getTime }
  implicit val fmt: Format[Timestamp] = Format(rds, wrs)

  implicit val todoRowWrites: Writes[TodoRow] = (
    (__ \ "id").write[Int] and
    (__ \ "todoTypeId").writeNullable[Int] and
    (__ \ "content").write[String] and
    (__ \ "insDatetime").write[Timestamp] and
    (__ \ "updDatetime").write[Timestamp]
  )(unlift(TodoRow.unapply))

  implicit val todoRowReads: Reads[TodoRow] = (
    (__ \ "id").read[Int] and
    (__ \ "todoTypeId").readNullable[Int] and
    (__ \ "content").read[String] and
    (__ \ "insDatetime").read[Timestamp] and
    (__ \ "updDatetime").read[Timestamp]
  )(TodoRow.apply _)

  implicit val todoSearchResultWrites: Writes[TodoSearchResult] = (
    (__ \ "id").write[Int] and
    (__ \ "content").write[String] and
    (__ \ "title").writeNullable[String]
  )(unlift(TodoSearchResult.unapply))

  implicit val todoSearchResultReads: Reads[TodoSearchResult] = (
    (__ \ "id").read[Int] and
    (__ \ "content").read[String] and
    (__ \ "title").readNullable[String]
  )(TodoSearchResult.apply _)

  def list = Action.async {
    todoService.list.map { x =>
      Ok(Json.toJson(x))
    }
  }

  case class TodoForm(todoTypeId: Option[Int] = None, content: String)
  implicit val todoFormlReads = Json.reads[TodoForm]

  def insert = Action.async(BodyParsers.parse.json) { request =>
    request.body.validate[TodoForm].fold(
      errors => {
        scala.concurrent.Future {
          BadRequest(Json.obj("message" -> JsError.toJson(errors)))
        }
      },
      form => {
        todoService.insert(TodoRow(0, form.todoTypeId, form.content, new java.sql.Timestamp(System.currentTimeMillis), new java.sql.Timestamp(System.currentTimeMillis))).map(_ => Ok(Json.toJson("ok")))
      }
    )
  }

  case class IDForm(id: Int)
  implicit val idFormlReads = Json.reads[IDForm]

  def delete = Action.async(BodyParsers.parse.json) { request =>
    request.body.validate[IDForm].fold(
      errors => {
        scala.concurrent.Future {
          BadRequest(Json.obj("message" -> JsError.toJson(errors)))
        }
      },
      form => {
        todoService.deleteById(form.id).map { x => 
          Ok(Json.toJson("ok"))
        }
      }
    )
  }
}
