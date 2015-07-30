package service.todo

import scala.concurrent.Future

import javax.inject.Inject
import models.Tables.Todo
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile
import models.Tables._

class TodoTypeService @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import slick.driver.MySQLDriver.api._

  def all(): Future[Seq[TodoTypeRow]] = db.run(TodoType.result)

  def insert(t: TodoTypeRow): Future[Unit] = db.run(TodoType += t).map { _ => () }

  def deleteById(id: Int): Future[Unit] = {

    val q = ( for {
      _ <- Todo.filter(_.todoTypeId === id.bind).delete
      _ <- TodoType.filter(_.id === id.bind).delete
    } yield ()).transactionally

    db.run(q)

  }
}
