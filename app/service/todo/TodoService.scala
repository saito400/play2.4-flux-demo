package service.todo

import scala.concurrent.Future

import javax.inject.Inject
import models.Tables.Todo
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile
import models.Tables._

case class TodoSearchResult(id: Int, content: String, title: Option[String])

class TodoService @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import slick.driver.MySQLDriver.api._

  def all(): Future[Seq[TodoRow]] = db.run(Todo.result)

  def list(): Future[Seq[TodoSearchResult]] = {
    val todoList = for {
      (t, tt) <- Todo joinLeft TodoType on(_.todoTypeId === _.id) sortBy {
        case (t1, t2) => t1.id
      }
    } yield (t.id, t.content, tt.map(_.title)) <> (TodoSearchResult.tupled, TodoSearchResult.unapply)
    db.run(todoList.result)
  }

  def insert(todo: TodoRow): Future[Unit] = db.run(Todo += todo).map { _ => () }

  def deleteById(id: Int): Future[Unit] = db.run(Todo.filter(_.id === id.bind).delete).map { _ => () }

}
