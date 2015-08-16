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
import service.todo.TodoTypeService
import javax.inject.Inject
import play.api.libs.json._
import play.api.libs.functional.syntax._
import java.sql.Timestamp

class TodoTypeController @Inject() (service: TodoTypeService) extends Controller {

  case class TodoTypeForm(title: String)
  implicit val todoTypeFormat = Json.format[TodoTypeForm]

  implicit val rds: Reads[Timestamp] = (__ \ "time").read[Long].map { long => new Timestamp(long) }
  implicit val wrs: Writes[Timestamp] = (__ \ "time").write[Long].contramap { (a: Timestamp) => a.getTime }
  implicit val fmt: Format[Timestamp] = Format(rds, wrs)

  implicit val TodoTypeRowWrites: Writes[TodoTypeRow] = (
    (__ \ "id").write[Int] and
    (__ \ "title").write[String] and
    (__ \ "insDatetime").write[Timestamp] and
    (__ \ "updDatetime").write[Timestamp]
  )(unlift(TodoTypeRow.unapply))

  implicit val TodoTypeRowReads: Reads[TodoTypeRow] = (
    (__ \ "id").read[Int] and
    (__ \ "title").read[String] and
    (__ \ "insDatetime").read[Timestamp] and
    (__ \ "updDatetime").read[Timestamp]
  )(TodoTypeRow.apply _)

  def list = Action.async {
    service.all.map { x =>
      Ok(Json.toJson(x))
    }
  }

  def insert = Action.async(BodyParsers.parse.json) { request =>
    request.body.validate[TodoTypeForm].fold(
      errors => {
        scala.concurrent.Future {
          BadRequest(Json.obj("message" -> JsError.toJson(errors)))
        }
      },
      form => {
        service.insert(TodoTypeRow(0, form.title, new java.sql.Timestamp(System.currentTimeMillis), new java.sql.Timestamp(System.currentTimeMillis))).map(_ => Ok(Json.toJson("ok")))
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
        service.deleteById(form.id).map { x => 
          Ok(Json.toJson("ok"))
        }
      }
    )
  }
}
