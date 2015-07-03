package service.todo

import scala.concurrent.Future

import javax.inject.Inject
import models.Tables.Todo
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile
import models.Todo
import models.Tables._

class TodoService @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  private val todos = TableQuery[models.Tables.Todo]
  private val todoTypes = TableQuery[models.Tables.TodoType]

  def all(): Future[Seq[TodoRow]] = db.run(todos.result)

  def list(): Future[Seq[(Int, Option[Int], String)]] = {
    val todoList = for {
      (t, tt) <- todos joinLeft todoTypes on (_.todoTypeId === _.id)
    } yield (t.id, tt.map(_.id), t.content)
    db.run(todoList.result)
  }


  def insert(todo: TodoRow): Future[Unit] = db.run(todos += todo).map { _ => () }

}